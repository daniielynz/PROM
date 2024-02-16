package com.example.proyecto_didactik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MisionAdapter(private val misiones: List<Mision>) :
    RecyclerView.Adapter<MisionAdapter.MisionViewHolder>() {

    class MisionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imagenMision)
        val texto1: TextView = itemView.findViewById(R.id.texto1)
        val texto2: TextView = itemView.findViewById(R.id.texto2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mision, parent, false)
        return MisionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MisionViewHolder, position: Int) {
        val mision = misiones[position]

        holder.imagen.setImageResource(mision.imagenResource)
        holder.texto1.text = mision.texto1
        holder.texto2.text = mision.texto2
    }

    override fun getItemCount(): Int {
        return misiones.size
    }
}
