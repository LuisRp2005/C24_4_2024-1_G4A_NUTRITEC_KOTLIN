package com.gutierrez.eddy.nutritec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.TipoIMC

class TipoimcAdapter() : RecyclerView.Adapter<TipoimcAdapter.TipoimcViewHolder>() {

    private var tipoImc: List<TipoIMC> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoimcViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tipoimc, parent, false)
        return TipoimcViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipoimcAdapter.TipoimcViewHolder, position: Int) {
        val tipoIMC = tipoImc[position]
        holder.bind(tipoIMC)
    }

    override fun getItemCount(): Int {
        return tipoImc.size
    }

    fun submitList(list: List<TipoIMC>) {
        tipoImc = list
        notifyDataSetChanged()
    }

    inner class TipoimcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idtipoimc: TextView = itemView.findViewById(R.id.tvIdItem)
        val nombretipoimc: TextView = itemView.findViewById(R.id.tvNombre)
        val descripciontipoimc: TextView = itemView.findViewById(R.id.tvDeseipcion)

        fun bind(tipo: TipoIMC ) {
            idtipoimc.text = tipo.idTipoImc.toString()
            nombretipoimc.text = tipo.tipoImc
            descripciontipoimc.text = tipo.descripcionImc

            }
        }

}
