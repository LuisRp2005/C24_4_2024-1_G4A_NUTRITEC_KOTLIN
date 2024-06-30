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
import com.gutierrez.eddy.nutritec.models.Comidas

class ComidaAdapter(
    private val onComidaClick: (Comidas) -> Unit,
    private val onAsignarClick: (Comidas) -> Unit
) : RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    private var comidas: List<Comidas> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(view, onComidaClick, onAsignarClick)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidas[position]
        holder.bind(comida)
    }

    override fun getItemCount(): Int {
        return comidas.size
    }

    fun submitList(list: List<Comidas>) {
        comidas = list
        notifyDataSetChanged()
    }

    inner class ComidaViewHolder(
        itemView: View,
        private val onComidaClick: (Comidas) -> Unit,
        private val onAsignarClick: (Comidas) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imagenComida: ImageView = itemView.findViewById(R.id.imagenComida)
        private val nombreComida: TextView = itemView.findViewById(R.id.tvNombreComida)
        private val caloriasComida: TextView = itemView.findViewById(R.id.tvCaloriasComida)
        private val categoriaComida: TextView = itemView.findViewById(R.id.tvCategoriaComida)
        private val btnAsignarComida: Button = itemView.findViewById(R.id.btnAsignarComida)

        fun bind(comida: Comidas) {
            nombreComida.text = comida.nombreComida
            caloriasComida.text = "Calorías: ${comida.calorias}"
            categoriaComida.text = "Categoría: ${comida.categoriaComida.nombreCategoria}"

            Glide.with(itemView)
                .load(comida.images)
                .into(imagenComida)

            itemView.setOnClickListener {
                onComidaClick(comida)
            }

            btnAsignarComida.setOnClickListener {
                onAsignarClick(comida)
            }
        }
    }
}
