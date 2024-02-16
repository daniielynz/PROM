package com.example.proyecto_didactik

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(private val context: Context, private val rankings: List<Ranking>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numero: TextView = itemView.findViewById(R.id.numero)
        val imagen: ImageView = itemView.findViewById(R.id.imagenRanking)
        val texto1: TextView = itemView.findViewById(R.id.texto1)
        val texto2: TextView = itemView.findViewById(R.id.texto2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val ranking = rankings[position]

        // Establecer el número según la posición en la lista (1-indexed)
        holder.numero.text = (position + 1).toString()


        // Escribimos el nombre de usuario
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
        val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", MainActivity.DEFAULT_USER_ID)

        // Obtener la lista de usuarios desde la base de datos
        val dbOperations = DbOperations(context)
        val usuarios = dbOperations.readAllUsers()

        // Id del usuario
        val usuario = usuarios.find { it.id == idUsuarioActual }
        val nombreUsuario = usuario?.nombre

        //Cambiar color del nombre para el usuario
        if (nombreUsuario != null && ranking.texto1 == nombreUsuario) {
            holder.texto1.setTextColor(ContextCompat.getColor(context, R.color.oro))
        }


        // Cambiar el color del número según la posición
        when (position) {
            0 -> {
                holder.numero.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.oro))
                holder.numero.setBackgroundResource(R.drawable.bordes_redondos_ranking) // Cambia a tu recurso de fondo negro
            }
            1 -> {
                holder.numero.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.plata))
                holder.numero.setBackgroundResource(R.drawable.bordes_redondos_ranking) // Cambia a tu recurso de fondo negro
            }
            2 -> {
                holder.numero.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.bronce))
                holder.numero.setBackgroundResource(R.drawable.bordes_redondos_ranking) // Cambia a tu recurso de fondo negro
            }
            else -> {
                // Restaurar el color y fondo predeterminados
                holder.numero.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.oscuro))
                holder.numero.setBackgroundResource(R.drawable.bordes_redondos_ranking)
            }
        }

        holder.imagen.setImageResource(ranking.imagenResource)
        holder.texto1.text = ranking.texto1
        holder.texto2.text = ranking.texto2

    }

    override fun getItemCount(): Int {
        return rankings.size
    }
}