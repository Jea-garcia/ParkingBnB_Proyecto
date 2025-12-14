package com.example.parkingbnb_proyecto.models

data class AutoLocal(
    val patente: String,
    val modelo: String,
    val color: String,
    val horaEntrada: String,
    val minutos: Long,
    val monto: Int
)
