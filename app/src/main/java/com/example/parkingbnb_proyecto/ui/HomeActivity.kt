package com.example.parkingbnb_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.MainActivity
import com.example.parkingbnb_proyecto.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Botones
        val btnAgregarAuto = findViewById<Button>(R.id.btnAgregarAuto)
        val btnListarAutos = findViewById<Button>(R.id.btnListarAutos)
        val btnAutosLocales = findViewById<Button>(R.id.btnAutosLocales)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnAgregarUsuario = findViewById<Button>(R.id.btnAgregarUsuario)

        // Obtener rol del usuario desde SharedPreferences
        val sharedPref = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
        val rolUsuario = sharedPref.getString("rol", "usuario") // "usuario" por defecto

        // Control de visibilidad según rol
        if (rolUsuario == "admin") {
            btnAgregarAuto.visibility = View.VISIBLE
            btnAutosLocales.visibility = View.VISIBLE
            btnAgregarUsuario.visibility = View.VISIBLE
        } else {
            btnAgregarAuto.visibility = View.GONE
            btnAutosLocales.visibility = View.VISIBLE
            btnAgregarUsuario.visibility = View.GONE
        }

        // Click listeners
        btnAgregarAuto.setOnClickListener {
            startActivity(Intent(this, AddAutoActivity::class.java))
        }

        btnListarAutos.setOnClickListener {
            startActivity(Intent(this, ListaAutosActivity::class.java))
        }

        btnAutosLocales.setOnClickListener {
            startActivity(Intent(this, ListaAutosLocalesActivity::class.java))
        }

        btnAgregarUsuario.setOnClickListener {
            startActivity(Intent(this, AgregarUsuarioActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            // Borrar SharedPreferences al cerrar sesión
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
