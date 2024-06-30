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
import com.gutierrez.eddy.nutritec.models.Ejercicios

class EjercicioAdapter(
    private val onEjercicioClick: (Ejercicios) -> Unit,
    private val onAsignarClick: (Ejercicios) -> Unit
) : RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder>() {

    private var ejercicios: List<Ejercicios> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ejercicio, parent, false)
        return EjercicioViewHolder(view, onEjercicioClick, onAsignarClick)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    override fun getItemCount(): Int {
        return ejercicios.size
    }

    fun submitList(list: List<Ejercicios>) {
        ejercicios = list
        notifyDataSetChanged()
    }

    inner class EjercicioViewHolder(
        itemView: View,
        private val onEjercicioClick: (Ejercicios) -> Unit,
        private val onAsignarClick: (Ejercicios) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val ejerimagen: ImageView = itemView.findViewById(R.id.ejerimagen)
        private val nombreejer: TextView = itemView.findViewById(R.id.tvNombre)
        private val ejerdescripcion: TextView = itemView.findViewById(R.id.ejerdescripcion)
        private val ejernivel: TextView = itemView.findViewById(R.id.ejernivel)
        private val ejertipoimc: TextView = itemView.findViewById(R.id.ejertipoimc)
        private val btnAsignarEjercicio: Button = itemView.findViewById(R.id.btnAsignarEjercicio)

        fun bind(ejercicio: Ejercicios) {
            nombreejer.text = ejercicio.nombre
            ejerdescripcion.text = ejercicio.descripcion
            ejernivel.text = ejercicio.nivel
            ejertipoimc.text = ejercicio.tipoImc.tipoImc

            Glide.with(itemView)
                .load(ejercicio.images)
                .into(ejerimagen)

            itemView.setOnClickListener {
                onEjercicioClick(ejercicio)
            }

            btnAsignarEjercicio.setOnClickListener {
                onAsignarClick(ejercicio)
            }
        }
    }
}
