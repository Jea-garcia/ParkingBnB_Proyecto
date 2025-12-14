package com.example.parkingbnb_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.models.DBHelper
import com.google.android.material.textfield.TextInputEditText

class AgregarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuario)

        val txtNuevoUsuario = findViewById<TextInputEditText>(R.id.txtNuevoUsuario)
        val txtNuevaContrasena = findViewById<TextInputEditText>(R.id.txtNuevaContrasena)
        val radioGroupRol = findViewById<RadioGroup>(R.id.radioGroupRol)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnVolverHome = findViewById<Button>(R.id.btnVolverHome)

        val db = DBHelper(this)

        btnAgregar.setOnClickListener {
            val usuario = txtNuevoUsuario.text.toString().trim()
            val contrasena = txtNuevaContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rolSeleccionadoId = radioGroupRol.checkedRadioButtonId
            val rol = findViewById<RadioButton>(rolSeleccionadoId).text.toString()

            val exito = db.agregarUsuario(usuario, contrasena, rol)
            if (exito) {
                Toast.makeText(this, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show()
                txtNuevoUsuario.text?.clear()
                txtNuevaContrasena.text?.clear()
                radioGroupRol.check(R.id.rbUsuario) // vuelve a seleccionar "Usuario" por defecto
            } else {
                Toast.makeText(this, "Error al agregar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolverHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
