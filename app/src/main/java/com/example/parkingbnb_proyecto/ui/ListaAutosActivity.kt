package com.example.parkingbnb_proyecto.ui

import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.models.DBHelper

class ListaAutosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_autos)

        val listView = findViewById<ListView>(R.id.listAutos)
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM autos", null)
            val lista = mutableListOf<String>()

            while (cursor.moveToNext()) {
                val patente = cursor.getString(cursor.getColumnIndexOrThrow("patente"))
                val modelo = cursor.getString(cursor.getColumnIndexOrThrow("modelo"))
                val color = cursor.getString(cursor.getColumnIndexOrThrow("color"))
                lista.add("🚗 $patente - $modelo ($color)")
            }

            cursor.close()
            db.close()

            if (lista.isEmpty()) {
                Toast.makeText(this, "No hay autos registrados aún", Toast.LENGTH_SHORT).show()
            }

            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar los autos", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
