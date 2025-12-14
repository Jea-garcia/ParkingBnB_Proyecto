package com.example.parkingbnb_proyecto.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ParkingBnB.db"
        private const val DATABASE_VERSION = 2

        // Usuarios
        private const val TABLE_USERS = "usuarios"
        private const val COL_USER_ID = "id"
        private const val COL_USER_NAME = "usuario"
        private const val COL_USER_PASS = "contrasena"
        private const val COL_USER_ROLE = "rol"

        // Autos
        private const val TABLE_AUTOS = "autos"
        private const val COL_AUTO_ID = "id"
        private const val COL_AUTO_PATENTE = "patente"
        private const val COL_AUTO_MODELO = "modelo"
        private const val COL_AUTO_COLOR = "color"
        private const val COL_AUTO_ENTRADA = "hora_entrada"
        private const val COL_AUTO_SALIDA = "hora_salida"
        private const val COL_AUTO_TOTAL = "total"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_USERS (
                $COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USER_NAME TEXT,
                $COL_USER_PASS TEXT,
                $COL_USER_ROLE TEXT DEFAULT 'usuario'
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_AUTOS (
                $COL_AUTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_AUTO_PATENTE TEXT,
                $COL_AUTO_MODELO TEXT,
                $COL_AUTO_COLOR TEXT,
                $COL_AUTO_ENTRADA INTEGER,
                $COL_AUTO_SALIDA INTEGER,
                $COL_AUTO_TOTAL INTEGER
            )
        """)

        // Usuario admin por defecto
        val values = ContentValues().apply {
            put(COL_USER_NAME, "admin")
            put(COL_USER_PASS, "1234")
            put(COL_USER_ROLE, "admin")
        }
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $COL_USER_ROLE TEXT DEFAULT 'usuario'")
        }
    }

    // Agregar usuario
    fun agregarUsuario(nombre: String, contrasena: String, rol: String = "usuario"): Boolean {
        val db = writableDatabase

        val usuarioExistente = obtenerUsuario(nombre)
        if (usuarioExistente != null) return false

        val values = ContentValues().apply {
            put(COL_USER_NAME, nombre)
            put(COL_USER_PASS, contrasena)
            put(COL_USER_ROLE, rol)
        }
        return db.insert(TABLE_USERS, null, values) != -1L
    }

    // Obtener usuario por nombre
    fun obtenerUsuario(nombre: String): Usuario? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_USER_NAME = ?", arrayOf(nombre))
        if (cursor.moveToFirst()) {
            val usuario = Usuario(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME)),
                contrasena = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASS)),
                rol = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ROLE))
            )
            cursor.close()
            return usuario
        }
        cursor.close()
        return null
    }

    // Registrar auto (entrada)
    fun insertarAuto(patente: String, modelo: String, color: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_AUTO_PATENTE, patente)
            put(COL_AUTO_MODELO, modelo)
            put(COL_AUTO_COLOR, color)
            put(COL_AUTO_ENTRADA, System.currentTimeMillis())
        }
        db.insert(TABLE_AUTOS, null, values)
    }

    // Registrar salida y calcular cobro
    fun registrarSalida(idAuto: Int): Int {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT $COL_AUTO_ENTRADA FROM $TABLE_AUTOS WHERE $COL_AUTO_ID = ?", arrayOf(idAuto.toString()))
        if (!cursor.moveToFirst()) { cursor.close(); return 0 }
        val horaEntrada = cursor.getLong(0)
        cursor.close()
        val horaSalida = System.currentTimeMillis()
        val horas = ((horaSalida - horaEntrada) / (1000 * 60 * 60)).toInt().coerceAtLeast(1)
        val total = horas * 1000
        val values = ContentValues().apply {
            put(COL_AUTO_SALIDA, horaSalida)
            put(COL_AUTO_TOTAL, total)
        }
        db.update(TABLE_AUTOS, values, "$COL_AUTO_ID = ?", arrayOf(idAuto.toString()))
        return total
    }

    // Obtener lista de autos
    fun obtenerAutos(): List<AutoLocal> {
        val autos = mutableListOf<AutoLocal>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_AUTOS", null)
        if (cursor.moveToFirst()) {
            do {
                val patente = cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTO_PATENTE))
                val modelo = cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTO_MODELO))
                val color = cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTO_COLOR))
                val horaEntradaMs = cursor.getLong(cursor.getColumnIndexOrThrow(COL_AUTO_ENTRADA))
                val horaSalidaMs = cursor.getLong(cursor.getColumnIndexOrThrow(COL_AUTO_SALIDA))
                val total = cursor.getInt(cursor.getColumnIndexOrThrow(COL_AUTO_TOTAL))
                val horaEntradaStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(horaEntradaMs))
                val minutos = if (horaSalidaMs != 0L) (horaSalidaMs - horaEntradaMs) / (1000 * 60) else (System.currentTimeMillis() - horaEntradaMs) / (1000 * 60)
                autos.add(AutoLocal(patente, modelo, color, horaEntradaStr, minutos, total))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return autos
    }
}
