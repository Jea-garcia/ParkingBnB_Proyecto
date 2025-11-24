// ui/adapter/AutoAdapter.kt
package com.example.parkingbnb_proyecto.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingbnb_proyecto.R
import com.example.parkingbnb_proyecto.data.model.Auto

class AutoAdapter(private val autos: List<Auto>) : RecyclerView.Adapter<AutoAdapter.AutoViewHolder>() {

    class AutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = itemView.findViewById(R.id.tvSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_auto, parent, false)
        return AutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutoViewHolder, position: Int) {
        val auto = autos[position]
        holder.tvTitle.text = auto.title
        holder.tvSubtitle.text = auto.body
    }

    override fun getItemCount() = autos.size
}