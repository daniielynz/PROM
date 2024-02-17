package com.example.proyecto_didactik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador para el RecyclerView que muestra la lista de misiones
class MisionAdapter(private val misiones: List<Mision>) :
    RecyclerView.Adapter<MisionAdapter.MisionViewHolder>() {

    // Clase interna que representa la vista de cada elemento en el RecyclerView
    class MisionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imagenMision)
        val texto1: TextView = itemView.findViewById(R.id.texto1)
        val texto2: TextView = itemView.findViewById(R.id.texto2)
    }

    // Crea una nueva instancia de ViewHolder al inflar el diseño del elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mision, parent, false)
        return MisionViewHolder(view)
    }

    // Vincula los datos de la posición dada en la lista a la vista
    override fun onBindViewHolder(holder: MisionViewHolder, position: Int) {
        val mision = misiones[position]

        // Establece las imágenes y textos en la vista
        holder.imagen.setImageResource(mision.imagenResource)
        holder.texto1.text = mision.texto1
        holder.texto2.text = mision.texto2
    }

    // Devuelve la cantidad total de elementos en la lista
    override fun getItemCount(): Int {
        return misiones.size
    }
}