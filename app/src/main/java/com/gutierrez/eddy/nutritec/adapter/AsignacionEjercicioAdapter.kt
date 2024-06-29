package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio

class AsignacionEjercicioAdapter(private val asignaciones: List<AsignacionEjercicio>) : RecyclerView.Adapter<AsignacionEjercicioAdapter.AsignacionEjercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignacionEjercicioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_asignacion_ejercicio, parent, false)
        return AsignacionEjercicioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AsignacionEjercicioViewHolder, position: Int) {
        val asignacion = asignaciones[position]
        holder.bind(asignacion)
    }

    override fun getItemCount() = asignaciones.size

    class AsignacionEjercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descripcionEjercicio: TextView = itemView.findViewById(R.id.descripcionEjercicio)
        private val fechaHoraAsignacion: TextView = itemView.findViewById(R.id.fechaHoraAsignacion)

        fun bind(asignacion: AsignacionEjercicio) {
            descripcionEjercicio.text = asignacion.ejercicio.descripcion
            fechaHoraAsignacion.text = asignacion.fechaHoraAsignacion.toString()
        }
    }
}