package com.example.parkingbnb_proyecto.data.model

// JSON de /posts: { userId, id, title, body }
// Tú lo tratarás como Auto en UI. Puedes mapear title->modelo y body->patente, por ejemplo.
data class Auto(
    val userId: Int = 1,
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
)