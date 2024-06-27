package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        private val ejerimagen: ImageView = itemView.findViewById(R.id.ejerimagen)
        private val nombreejer: TextView = itemView.findViewById(R.id.tvNombre)
        private val ejerdescripcion: TextView = itemView.findViewById(R.id.ejerdescripcion)
        private val ejernivel: TextView = itemView.findViewById(R.id.ejernivel)
        private val ejertipoimc: TextView = itemView.findViewById(R.id.ejertipoimc)

        fun bind(ejercicio: Ejercicios) {
            nombreejer.text = ejercicio.nombre
            ejerdescripcion.text = ejercicio.descripcion
            ejernivel.text = ejercicio.nivel
            ejertipoimc.text = ejercicio.tipoImc.tipoImc

            // Cargar la imagen usando Glide
            Glide.with(itemView)
                .load(ejercicio.images)
                .into(ejerimagen)
        }
    }
}
