package com.example.parkingbnb_proyecto.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ParkingBnB.db"
        private const val DATABASE_VERSION = 1

        // Tabla de usuarios (para login)
        private const val TABLE_USERS = "usuarios"
        private const val COL_USER_ID = "id"
        private const val COL_USER_NAME = "usuario"
        private const val COL_USER_PASS = "contrasena"

        // Tabla de autos (para registros)
        private const val TABLE_AUTOS = "autos"
        private const val COL_AUTO_ID = "id"
        private const val COL_AUTO_PATENTE = "patente"
        private const val COL_AUTO_MODELO = "modelo"
        private const val COL_AUTO_COLOR = "color"
        private const val COL_AUTO_ENTRADA = "hora_entrada"
        private const val COL_AUTO_SALIDA = "hora_salida"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USER_NAME TEXT,
                $COL_USER_PASS TEXT
            )
        """.trimIndent()

        val createAutosTable = """
            CREATE TABLE $TABLE_AUTOS (
                $COL_AUTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_AUTO_PATENTE TEXT,
                $COL_AUTO_MODELO TEXT,
                $COL_AUTO_COLOR TEXT,
                $COL_AUTO_ENTRADA TEXT,
                $COL_AUTO_SALIDA TEXT
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createAutosTable)

        // Usuario inicial (admin / 1234)
        val values = ContentValues().apply {
            put(COL_USER_NAME, "admin")
            put(COL_USER_PASS, "1234")
        }
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AUTOS")
        onCreate(db)
    }
}