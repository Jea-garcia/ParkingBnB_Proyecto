package com.example.parkingbnb_proyecto.ui

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

        val txtPatente = findViewById<EditText>(R.id.txtPatente)
        val txtModelo = findViewById<EditText>(R.id.txtModelo)
        val txtColor = findViewById<EditText>(R.id.txtColor)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnVerLista = findViewById<Button>(R.id.btnVerLista)
        val btnVolverHome = findViewById<Button>(R.id.btnVolverHome)

        // ✅ GUARDAR AUTO CON HORA DE ENTRADA AUTOMÁTICA
        btnGuardar.setOnClickListener {

            val patente = txtPatente.text.toString().trim()
            val modelo = txtModelo.text.toString().trim()
            val color = txtColor.text.toString().trim()

            if (patente.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 👉 Usa DBHelper (hora en millis)
            dbHelper.insertarAuto(patente, modelo, color)

            Toast.makeText(
                this,
                "Auto ingresado correctamente\nHora de entrada registrada",
                Toast.LENGTH_SHORT
            ).show()

            txtPatente.text.clear()
            txtModelo.text.clear()
            txtColor.text.clear()
        }

        btnVerLista.setOnClickListener {
            startActivity(Intent(this, ListaAutosLocalesActivity::class.java))
        }

        btnVolverHome.setOnClickListener {
            finish()
        }
    }
}
