package com.example.parkingbnb_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.models.DBHelper
import com.example.parkingbnb_proyecto.models.Usuario
import com.example.parkingbnb_proyecto.ui.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a vistas
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtContrasena = findViewById<EditText>(R.id.edtContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val dbHelper = DBHelper(this)

        btnLogin.setOnClickListener {
            val usuarioInput = edtUsuario.text.toString().trim()
            val contrasenaInput = edtContrasena.text.toString().trim()

            if (usuarioInput.isEmpty()) {
                edtUsuario.error = "Ingrese usuario"
                return@setOnClickListener
            }

            if (contrasenaInput.isEmpty()) {
                edtContrasena.error = "Ingrese contraseña"
                return@setOnClickListener
            }

            val usuario: Usuario? = dbHelper.obtenerUsuario(usuarioInput)

            if (usuario == null || usuario.contrasena != contrasenaInput) {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar rol en SharedPreferences
            val sharedPref = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("rol", usuario.rol)
                apply()
            }

            // Redirigir a HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
