package com.example.proyecto_didactik

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Actividad4 : AppCompatActivity() {
    private var nombreActividad: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad4)

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")
    }

    fun accionCompletado(v: View) {
        val intent = Intent(this, Mapa::class.java)
        intent.putExtra("MensajeActividadCompletada" , "Oso Ondo! S letra lortu duzue!");
        intent.putExtra("letraActividadCompletada" , "S");
        startActivity(intent)
    }


    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre" , nombreActividad);
        startActivity(intent)
    }
}