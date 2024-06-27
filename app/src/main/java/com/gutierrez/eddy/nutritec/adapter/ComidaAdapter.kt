package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.Comidas

class ComidaAdapter : RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    private var comidas: List<Comidas> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidas[position]
        holder.bind(comida)
    }

    override fun getItemCount(): Int {
        return comidas.size
    }

    // Método para actualizar la lista de comidas
    fun submitList(list: List<Comidas>) {
        comidas = list
        notifyDataSetChanged()
    }

    inner class ComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagenComida: ImageView = itemView.findViewById(R.id.imagenComida)
        private val nombreComida: TextView = itemView.findViewById(R.id.tvNombreComida)
        private val caloriasComida: TextView = itemView.findViewById(R.id.tvCaloriasComida)
        private val categoriaComida: TextView = itemView.findViewById(R.id.tvCategoriaComida)

        fun bind(comida: Comidas) {
            nombreComida.text = comida.nombreComida
            caloriasComida.text = "Calorías: ${comida.calorias}"
            categoriaComida.text = "Categoría: ${comida.categoriaComida.nombreCategoria}"

            // Cargar la imagen de la comida usando Glide
            Glide.with(itemView)
                .load(comida.images)
                .into(imagenComida)
        }
    }
}
