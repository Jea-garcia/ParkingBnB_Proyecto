package com.example.parkingbnb_proyecto

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.models.DBHelper
import com.example.parkingbnb_proyecto.ui.AddAutoActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        btnLogin.setOnClickListener {

            val usuario = txtUsuario.text.toString().trim()
            val contrasena = txtContrasena.text.toString().trim()

            // Validaciones básicas
            if (usuario.isEmpty()) {
                txtUsuario.error = "Ingrese usuario"
                return@setOnClickListener
            }
            if (contrasena.isEmpty()) {
                txtContrasena.error = "Ingrese contraseña"
                return@setOnClickListener
            }

            // Consulta SQL
            val cursor: Cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?",
                arrayOf(usuario, contrasena)
            )

            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Inicio exitoso", Toast.LENGTH_SHORT).show()

                // Ir a registrar autos
                val intent = Intent(this, AddAutoActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
        }
    }
}
