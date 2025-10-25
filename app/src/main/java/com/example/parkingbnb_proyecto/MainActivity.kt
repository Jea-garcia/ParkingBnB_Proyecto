package com.example.parkingbnb_proyecto

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingbnb_proyecto.models.DBHelper
import com.example.parkingbnb_proyecto.ui.AddAutoActivity

class MainActivity() : AppCompatActivity(), Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        btnLogin.setOnClickListener {
            val usuario = txtUsuario.text.toString()
            val contrasena = txtContrasena.text.toString()

            val cursor: Cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?",
                arrayOf(usuario, contrasena)
            )

            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Inicio exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AddAutoActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}
