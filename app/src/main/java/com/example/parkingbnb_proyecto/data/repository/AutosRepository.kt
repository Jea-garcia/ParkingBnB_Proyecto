package com.example.parkingbnb_proyecto.data.repository

import com.example.parkingbnb_proyecto.data.api.RetrofitClient
import com.example.parkingbnb_proyecto.data.model.Auto
import retrofit2.Call

class AutoRepository {

    fun obtenerAutos(): Call<List<Auto>> {
        return RetrofitClient.instance.getAutos()
    }

}
