package com.example.proyecto_didactik

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Actividad4 : AppCompatActivity() {
    private var nombreActividad: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad4)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Obtener datos adicionales del Intent
        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")
    }

    // Método llamado al completar la actividad
    fun accionCompletado(v: View) {
        // Mostrar diálogo de acierto
        val letra = resources.getStringArray(R.array.arrayLetrasActividades).getOrNull(3)?.trim()?.toString() ?: ""
        val puntos = resources.getStringArray(R.array.arrayPuntosActividades).getOrNull(3)?.trim()?.toString() ?: ""

        val mensaje = getString(R.string.felicitacion_actividad) + "\n-" + getString(R.string.letra) + " " + letra + "\n-" + puntos + " " + getString(R.string.puntos)

        // Generar diálogo modal
        generarDialogo(v, getString(R.string.tituloGenerarDialogo), mensaje) {
            // Otorgar puntos si la actividad no se ha completado
            val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
            val valorVariable = sharedPreferences.getInt("ACTIVIDAD_4", 0)
            if (valorVariable == 0) {
                // Poner ACTIVIDAD_4 con valor 1
                val editor = sharedPreferences.edit()
                editor.putInt("ACTIVIDAD_4", 1)
                editor.apply()

                // Encontrar el ID del usuario y sumarle X puntos
                val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", -1)

                if (idUsuarioActual != -1L) {
                    val dbOperations = DbOperations(this)
                    val usuarioActual = dbOperations.getUserById(idUsuarioActual)

                    if (usuarioActual != null) {
                        val puntosActuales = usuarioActual.puntos
                        val nuevosPuntos = puntosActuales + puntos.toInt()

                        // Actualizar puntos del usuario en la base de datos
                        dbOperations.updateUserPoints(idUsuarioActual, nuevosPuntos)

                        // Actualizar valor de la actividad
                        dbOperations.updateActividad(idUsuarioActual, 4)

                        // Actualizar la variable PUNTOS_USUARIO_ACTUAL en SharedPreferences
                        editor.putInt("PUNTOS_USUARIO_ACTUAL", nuevosPuntos)
                        editor.apply()
                    }
                }
            }

            // Este bloque de código se ejecutará después de cerrar el diálogo
            val intent = Intent(this, Mapa::class.java)
            intent.putExtra("letraActividadCompletada", "S")
            startActivity(intent)
        }
    }

    // Método para volver a la interfaz común
    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }

    // Método para generar un diálogo modal
    fun generarDialogo(
        view: View,
        titulo: String,
        contenido: String,
        callback: () -> Unit
    ) {
        val builder = AlertDialog.Builder(view.context, R.style.AlertDialogTheme)
        val inflater = LayoutInflater.from(view.context)
        val dialogView: View = inflater.inflate(R.layout.modal_layout, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = dialogView.findViewById<TextView>(R.id.contentTextView)
        val acceptButton = dialogView.findViewById<Button>(R.id.acceptButton)

        titleTextView.text = titulo
        contentTextView.text = contenido

        val alertDialog = builder.setView(dialogView).create()
        alertDialog.setCanceledOnTouchOutside(false)

        acceptButton.setOnClickListener {
            // Cierra la ventana modal al hacer clic en el botón Aceptar
            alertDialog.dismiss()
            // Ejecutar la devolución de llamada
            callback.invoke()
        }

        alertDialog.show()
    }
}