package com.example.proyecto_didactik

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Actividad1 : AppCompatActivity() {
    // Propiedades y variables de la clase
    private var nombreActividad: String? = null
    private var contadorAciertos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad1)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")
    }

    // Acción al completar la actividad
    fun accionCompletado(v: View) {
        if (contadorAciertos == 3) {
            // Mostrar diálogo de acierto
            val letra = resources.getStringArray(R.array.arrayLetrasActividades).getOrNull(0)?.trim()?.toString() ?: ""
            val puntos = resources.getStringArray(R.array.arrayPuntosActividades).getOrNull(0)?.trim()?.toString() ?: ""

            val mensaje = getString(R.string.felicitacion_actividad)+"\n-"+getString(R.string.letra)+" "+letra+"\n-"+puntos+" "+getString(R.string.puntos)

            generarDialogo(v, getString(R.string.tituloGenerarDialogo), mensaje) {
                // Otorgar puntos si la actividad no se ha completado
                val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
                val valorVariable = sharedPreferences.getInt("ACTIVIDAD_1", 0)
                if (valorVariable == 0) {
                    // Poner ACTIVIDAD_1 con valor 1
                    val editor = sharedPreferences.edit()
                    editor.putInt("ACTIVIDAD_1", 1)
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
                            dbOperations.updateActividad(idUsuarioActual,1)

                            // Actualizar la variable PUNTOS_USUARIO_ACTUAL en SharedPreferences
                            editor.putInt("PUNTOS_USUARIO_ACTUAL", nuevosPuntos)
                            editor.apply()
                        }
                    }
                }

                // Este bloque de código se ejecutará después de cerrar el diálogo
                val intent = Intent(this, Mapa::class.java)
                intent.putExtra("letraActividadCompletada", "A")
                startActivity(intent)
            }
        } else {
            // Mostrar diálogo indicando que quedan respuestas por seleccionar
            generarDialogo(v, getString(R.string.tituloErrores), getString(R.string.errorActividad1)) {}
        }
    }

    // Función para volver a la pantalla anterior
    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }

    // Función para manejar la selección de opciones correctas
    fun opcionCorrecta(view: View) {
        val textoSeleccionado = view as TextView

        // Cambiar el color de fondo a verde solo si no se ha seleccionado antes
        if (textoSeleccionado.tag == null) {
            textoSeleccionado.setBackgroundColor(getColor(R.color.acierto))
            textoSeleccionado.tag = "seleccionado"
            contadorAciertos++
            mostrarImagenAsociada(view)
        }
    }

    // Función para manejar la selección de opciones incorrectas
    fun opcionIncorrecta(view: View) {
        val textoSeleccionado = view as TextView

        // Cambiar el color de fondo a verde solo si no se ha seleccionado antes
        if (textoSeleccionado.tag == null) {
            textoSeleccionado.setBackgroundColor(getColor(R.color.fallo))
            mostrarImagenAsociada(view)
        }
    }

    // Mostrar la imagen asociada a la opción seleccionada
    private fun mostrarImagenAsociada(view: View) {
        val textViewId = view.id
        val numeroAsociado = obtenerNumeroAsociado(textViewId)
        val imageViewId = resources.getIdentifier("imageView$numeroAsociado", "id", packageName)
        val imageView = findViewById<ImageView>(imageViewId)
        imageView?.visibility = View.VISIBLE
    }

    // Obtener el número asociado a la opción seleccionada
    private fun obtenerNumeroAsociado(textViewId: Int): Int {
        val idString = resources.getResourceEntryName(textViewId)
        return idString.substring(2).toInt()
    }

    // Generar diálogo modal
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