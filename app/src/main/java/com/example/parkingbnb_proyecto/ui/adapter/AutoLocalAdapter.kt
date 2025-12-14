package com.example.parkingbnb_proyecto.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.models.AutoLocal

class AutoLocalAdapter(private val autos: List<AutoLocal>) :
    RecyclerView.Adapter<AutoLocalAdapter.AutoViewHolder>() {

    class AutoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPatente: TextView = view.findViewById(R.id.tvPatente)
        val tvModelo: TextView = view.findViewById(R.id.tvModelo)
        val tvColor: TextView = view.findViewById(R.id.tvColor)
        val tvHoraEntrada: TextView = view.findViewById(R.id.tvHoraEntrada)
        val tvMinutos: TextView = view.findViewById(R.id.tvMinutos)
        val tvMonto: TextView = view.findViewById(R.id.tvMonto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_auto_local, parent, false)
        return AutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutoViewHolder, position: Int) {
        val auto = autos[position]
        holder.tvPatente.text = auto.patente
        holder.tvModelo.text = auto.modelo
        holder.tvColor.text = auto.color
        holder.tvHoraEntrada.text = auto.horaEntrada
        holder.tvMinutos.text = "${auto.minutos} min"
        holder.tvMonto.text = "$${auto.monto}"
    }

    override fun getItemCount(): Int = autos.size
}
