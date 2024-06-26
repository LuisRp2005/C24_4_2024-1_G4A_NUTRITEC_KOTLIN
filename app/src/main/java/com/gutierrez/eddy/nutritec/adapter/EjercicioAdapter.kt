package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.Ejercicios

class EjercicioAdapter : RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder>() {

    private var ejercicio: List<Ejercicios> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ejercicio, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val currentEjercicio = ejercicio[position]
        holder.bind(currentEjercicio)
    }

    override fun getItemCount() = ejercicio.size

    // Método para actualizar la lista de ejercicios
    fun submitList(list: List<Ejercicios>) {
        ejercicio = list
        notifyDataSetChanged()
    }

    inner class EjercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreejer: TextView = itemView.findViewById(R.id.tvNombre)

        fun bind(ejercicio: Ejercicios) {
            nombreejer.text = ejercicio.nombre
        }
    }
}
