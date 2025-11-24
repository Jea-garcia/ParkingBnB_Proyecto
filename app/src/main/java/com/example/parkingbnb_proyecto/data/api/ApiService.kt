package com.example.parkingbnb_proyecto.data.api

import com.example.parkingbnb_proyecto.data.model.Auto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getAutos(): Call<List<Auto>>

}

