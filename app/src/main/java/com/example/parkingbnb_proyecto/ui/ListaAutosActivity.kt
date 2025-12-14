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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaAutosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnVolverHome: Button
    private val repository = AutoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_autos)

        recyclerView = findViewById(R.id.recyclerAutos)
        btnVolverHome = findViewById(R.id.btnVolverHome)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar datos desde la API REST
        cargarAutos()

        // 🔙 Volver al Home
        btnVolverHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun cargarAutos() {
        repository.obtenerAutos().enqueue(object : Callback<List<Auto>> {

            override fun onResponse(
                call: Call<List<Auto>>,
                response: Response<List<Auto>>
            ) {
                if (response.isSuccessful) {
                    val autos = response.body() ?: emptyList()
                    recyclerView.adapter = AutoAdapter(autos)
                } else {
                    Toast.makeText(
                        this@ListaAutosActivity,
                        "Error al obtener datos: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Auto>>, t: Throwable) {
                Toast.makeText(
                    this@ListaAutosActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
