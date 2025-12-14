package com.example.parkingbnb_proyecto.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.models.DBHelper
import com.example.parkingbnb_proyecto.ui.adapter.AutoLocalAdapter

class ListaAutosLocalesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_autos_locales)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAutosLocales)
        val btnVolverHome = findViewById<Button>(R.id.btnVolverHome)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DBHelper(this)
        val autos = dbHelper.obtenerAutos()

        recyclerView.adapter = AutoLocalAdapter(autos)

        btnVolverHome.setOnClickListener {
            finish()
        }
    }
}
