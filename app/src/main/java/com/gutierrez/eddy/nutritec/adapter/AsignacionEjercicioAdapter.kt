package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.AsignacionEjercicio

class AsignacionEjercicioAdapter(
    private val asignaciones: List<AsignacionEjercicio>,
    private val onEliminarClick: (AsignacionEjercicio) -> Unit
) : RecyclerView.Adapter<AsignacionEjercicioAdapter.AsignacionEjercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignacionEjercicioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_asignacion_ejercicio, parent, false)
        return AsignacionEjercicioViewHolder(itemView, onEliminarClick)
    }

    override fun onBindViewHolder(holder: AsignacionEjercicioViewHolder, position: Int) {
        val asignacion = asignaciones[position]
        holder.bind(asignacion)
    }

    override fun getItemCount() = asignaciones.size

    class AsignacionEjercicioViewHolder(itemView: View, private val onEliminarClick: (AsignacionEjercicio) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val descripcionEjercicio: TextView = itemView.findViewById(R.id.descripcionEjercicio)
        private val nivelEjercicio: TextView = itemView.findViewById(R.id.nivelEjercicio)
        private val tipoImcEjercicio: TextView = itemView.findViewById(R.id.tipoImcEjercicio)
        private val fechaHoraAsignacion: TextView = itemView.findViewById(R.id.fechaHoraAsignacion)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewEjercicio)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarAsignacionEjercicio)

        fun bind(asignacion: AsignacionEjercicio) {
            descripcionEjercicio.text = asignacion.ejercicio.descripcion ?: "Descripción no disponible"
            nivelEjercicio.text = asignacion.ejercicio.nivel ?: "Nivel no disponible"
            tipoImcEjercicio.text = asignacion.ejercicio.tipoImc.tipoImc ?: "Tipo IMC no disponible"
            fechaHoraAsignacion.text = asignacion.fechaHoraAsignacion?.toString() ?: "Fecha no disponible"

            Glide.with(itemView.context)
                .load(asignacion.ejercicio.images)
                .into(imageView)

            btnEliminar.setOnClickListener {
                onEliminarClick(asignacion)
            }
        }
    }
}
