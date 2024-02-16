package com.example.proyecto_didactik

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DialogMisionesActivity(context: Context) : Dialog(context, R.style.DialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_misiones)

        // Ocultar la barra de navegación
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMisiones)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val misiones = listOf(
            Mision(R.drawable.iglesia, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision1), "50 " + context.getString(R.string.puntos)),
            Mision(R.drawable.paella, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision2), "60 " + context.getString(R.string.puntos)),
            Mision(R.drawable.sopa, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision3), "80 " + context.getString(R.string.puntos)),
            Mision(R.drawable.jaiak, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision4), "10 " + context.getString(R.string.puntos)),
            Mision(R.drawable.barco, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision5), "100 " + context.getString(R.string.puntos)),
            Mision(R.drawable.puntabegonia, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision6), "10 " + context.getString(R.string.puntos)),
            Mision(R.drawable.puente, context.getString(R.string.tituloAcabarActividad) + "\n" + context.getString(R.string.mision7), "30 " + context.getString(R.string.puntos))
        )


        val adapter = MisionAdapter(misiones)
        recyclerView.adapter = adapter
    }
}
