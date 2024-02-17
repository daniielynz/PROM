package com.example.proyecto_didactik

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ModalActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Oculta la barra de navegación y la barra de estado en el diálogo modal
        window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )

        // Muestra la ventana modal
        showCustomDialog()
    }
    // Función para mostrar un diálogo modal personalizado
    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.modal_layout, null)

        // Obtén referencias a los elementos de la interfaz de usuario en el diálogo modal
        val titleTextView = dialogView.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = dialogView.findViewById<TextView>(R.id.contentTextView)
        val acceptButton = dialogView.findViewById<Button>(R.id.acceptButton)

        // Puedes personalizar el título y el contenido aquí
        titleTextView.text = "Título Personalizado"
        contentTextView.text = "Contenido personalizado en la ventana modal."

        // Crea una instancia del diálogo modal con la vista personalizada
        val alertDialog = builder.setView(dialogView).create()

        // Esto evita que el diálogo se cierre al tocar fuera de él
        alertDialog.setCanceledOnTouchOutside(false)

        // Configura el evento de clic para el botón Aceptar
        acceptButton.setOnClickListener {
            // Cierra la ventana modal al hacer clic en el botón Aceptar
            alertDialog.dismiss()
        }

        // Muestra el diálogo modal
        alertDialog.show()
    }
}