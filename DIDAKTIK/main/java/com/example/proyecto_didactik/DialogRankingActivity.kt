package com.example.proyecto_didactik

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DialogRankingActivity(context: Context, private val rankings: List<Ranking>) : Dialog(context, R.style.DialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_ranking)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewRanking)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Ocultar la barra de navegación
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        // Ordenar la lista de rankings descendentemente por puntos
        val rankingsOrdenados = rankings.sortedByDescending { it.texto2.extractPoints().toInt() }

        val adapter = RankingAdapter(context, rankingsOrdenados)
        recyclerView.adapter = adapter
    }

    private fun String.extractPoints(): String {
        // Extraer los dígitos de la cadena (eliminando cualquier otro carácter)
        return replace(Regex("[^\\d]"), "")
    }
}