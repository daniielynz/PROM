package com.example.proyecto_didactik

import android.app.Activity
import android.content.Context
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
        setContentView(R.layout.dialog_nombre)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Evita que el diálogo se cierre al tocar fuera de él
        setFinishOnTouchOutside(false)
    }

    fun validarNombre(view: View) {
        val editTextContent = findViewById<EditText>(R.id.editTextContent)
        val nombreIngresado = editTextContent.text.toString().trim()

        if (nombreIngresado.isNotBlank()) {
            // Establece el resultado con el nombre ingresado
            val resultIntent = Intent()
            resultIntent.putExtra("nombreUsuario", nombreIngresado)
            setResult(RESULT_OK, resultIntent)
            finish()
        } else {
            // Muestra un mensaje de error o realiza alguna acción
        }
    }

    override fun onBackPressed() {

    }
}