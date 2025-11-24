package com.example.parkingbnb_proyecto.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.models.DBHelper

class AddAutoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_auto)

        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        val txtPatente = findViewById<EditText>(R.id.txtPatente)
        val txtModelo = findViewById<EditText>(R.id.txtModelo)
        val txtColor = findViewById<EditText>(R.id.txtColor)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnVerLista = findViewById<Button>(R.id.btnVerLista)

        btnGuardar.setOnClickListener {
            val patente = txtPatente.text.toString().trim()
            val modelo = txtModelo.text.toString().trim()
            val color = txtColor.text.toString().trim()

            if (patente.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val valores = ContentValues().apply {
                put("patente", patente)
                put("modelo", modelo)
                put("color", color)
            }

            val resultado = db.insert("autos", null, valores)

            if (resultado != -1L) {
                Toast.makeText(this, "Vehículo registrado", Toast.LENGTH_SHORT).show()
                txtPatente.text.clear()
                txtModelo.text.clear()
                txtColor.text.clear()
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        }

        btnVerLista.setOnClickListener {
            startActivity(Intent(this, ListaAutosActivity::class.java))
        }
    }
}