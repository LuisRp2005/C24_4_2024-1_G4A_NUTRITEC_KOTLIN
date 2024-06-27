package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.CategoriaComida

class CategoriaAdapter : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    private var categorias: List<CategoriaComida> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria_comida, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.bind(categoria)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    fun submitList(list: List<CategoriaComida>) {
        categorias = list
        notifyDataSetChanged()
    }

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idCategoria: TextView = itemView.findViewById(R.id.tvIdCategoria)
        private val nombreCategoria: TextView = itemView.findViewById(R.id.tvNombreCategoria)

        fun bind(categoria: CategoriaComida) {
            idCategoria.text = categoria.idCategoriaComida.toString()
            nombreCategoria.text = categoria.nombreCategoria
        }
    }
}
