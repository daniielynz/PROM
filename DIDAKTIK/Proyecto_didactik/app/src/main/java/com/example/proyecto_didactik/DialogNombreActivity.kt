package com.example.proyecto_didactik

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class DialogNombreActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecer el diseño de la actividad como el diseño del diálogo
        setContentView(R.layout.dialog_nombre)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Evita que el diálogo se cierre al tocar fuera de él
        setFinishOnTouchOutside(false)
    }

    // Método llamado al hacer clic en el botón para validar el nombre
    fun validarNombre(view: View) {
        // Obtener la referencia al EditText que contiene el nombre ingresado
        val editTextContent = findViewById<EditText>(R.id.editTextContent)
        val nombreIngresado = editTextContent.text.toString().trim()

        if (nombreIngresado.isNotBlank()) {
            // Si el nombre no está en blanco, establecer el resultado con el nombre ingresado
            val resultIntent = Intent()
            resultIntent.putExtra("nombreUsuario", nombreIngresado)
            setResult(RESULT_OK, resultIntent)
            finish()
        } else {
            //No se utiliza :)
        }
    }

    // Método llamado al presionar el botón de retroceso
    override fun onBackPressed() {
        //No se utiliza :)
    }
}