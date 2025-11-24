package com.example.parkingbnb_proyecto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.data.model.Auto
import com.example.parkingbnb_proyecto.data.repository.AutoRepository
import com.example.parkingbnb_proyecto.ui.adapter.AutoAdapter
import com.example.parkingbnb_proyecto.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaAutosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val repository = AutoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_autos)

        recyclerView = findViewById(R.id.recyclerAutos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar datos desde la API REST
        cargarAutos()

        // 🔹 Volver directamente al login (no a AddAutoActivity)
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun cargarAutos() {
        val call = repository.obtenerAutos()

        call.enqueue(object : Callback<List<Auto>> {
            override fun onResponse(call: Call<List<Auto>>, response: Response<List<Auto>>) {
                if (response.isSuccessful) {
                    val autos = response.body() ?: emptyList()
                    recyclerView.adapter = AutoAdapter(autos)
                    Toast.makeText(this@ListaAutosActivity, "Datos cargados desde API", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ListaAutosActivity,
                        "Error al obtener los datos: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Auto>>, t: Throwable) {
                Toast.makeText(this@ListaAutosActivity,
                    "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}