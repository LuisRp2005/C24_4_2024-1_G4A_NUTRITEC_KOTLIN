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
import com.gutierrez.eddy.nutritec.models.AsignacionComida

class AsignacionComidaAdapter(
    private val asignaciones: List<AsignacionComida>,
    private val onEliminarClick: (AsignacionComida) -> Unit
) : RecyclerView.Adapter<AsignacionComidaAdapter.AsignacionComidaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignacionComidaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_asignacion_comida, parent, false)
        return AsignacionComidaViewHolder(itemView, onEliminarClick)
    }

    override fun onBindViewHolder(holder: AsignacionComidaViewHolder, position: Int) {
        val asignacion = asignaciones[position]
        holder.bind(asignacion)
    }

    override fun getItemCount() = asignaciones.size

    class AsignacionComidaViewHolder(itemView: View, private val onEliminarClick: (AsignacionComida) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val descripcionComida: TextView = itemView.findViewById(R.id.descripcionComida)
        private val fechaHoraRegistro: TextView = itemView.findViewById(R.id.fechaHoraRegistro)
        private val caloriasComida: TextView = itemView.findViewById(R.id.caloriasComida)
        private val categoriaComida: TextView = itemView.findViewById(R.id.categoriaComida)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewComida)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarComida)

        fun bind(asignacion: AsignacionComida) {
            descripcionComida.text = asignacion.comida.nombreComida
            fechaHoraRegistro.text = asignacion.fechaHoraRegistro
            caloriasComida.text = asignacion.comida.calorias.toString()
            categoriaComida.text = asignacion.comida.categoriaComida.nombreCategoria

            Glide.with(itemView.context)
                .load(asignacion.comida.images)
                .into(imageView)

            btnEliminar.setOnClickListener {
                onEliminarClick(asignacion)
            }
        }
    }
}
