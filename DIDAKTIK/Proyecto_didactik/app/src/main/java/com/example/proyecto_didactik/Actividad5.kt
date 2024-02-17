package com.example.proyecto_didactik

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Random
import android.view.inputmethod.EditorInfo

class Actividad5 : AppCompatActivity() {
    private var nombreActividad: String? = null
    private lateinit var diferencias: List<String>
    private lateinit var listaPistas: List<String>
    private val respuestasUtilizadas = mutableListOf<String>()
    private var contadorAciertos = 0

    // Se mueve la inicialización a `onCreate` para que sea después de `setContentView`
    lateinit var buscador: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad5)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Cambiado: Mueve la inicialización aquí
        buscador = findViewById(R.id.buscador)

        // Lista de diferencias
        diferencias = listOf(
            resources.getStringArray(R.array.arrayRespuestas).getOrNull(0)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayRespuestas).getOrNull(1)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayRespuestas).getOrNull(2)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayRespuestas).getOrNull(3)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayRespuestas).getOrNull(4)?.trim()?.toString() ?: ""
        )

        // Lista de pistas
        listaPistas = listOf(
            resources.getStringArray(R.array.arrayPistas).getOrNull(0)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayPistas).getOrNull(1)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayPistas).getOrNull(2)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayPistas).getOrNull(3)?.trim()?.toString() ?: "",
            resources.getStringArray(R.array.arrayPistas).getOrNull(4)?.trim()?.toString() ?: ""
        )

        // Cambiado: Agrega el listener para el botón de acción en el teclado
        buscador.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Lógica que se ejecutará al pulsar Enter
                accionCompletado(buscador)
                true
            } else {
                false
            }
        }

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")
    }

    fun accionCompletado(v: View) {
        val buscadorEditText: EditText = findViewById(R.id.buscador)
        val textoAurkituta: TextView = findViewById(R.id.aurkituta)

        val respuesta = buscadorEditText.text.toString().toLowerCase().trim()

        if (respuesta.isNotEmpty() && respuesta in diferencias && respuesta !in respuestasUtilizadas) {
            // Respuesta correcta y no utilizada previamente
            contadorAciertos++
            respuestasUtilizadas.add(respuesta)
        }

        // Actualizar el texto aurkituta
        textoAurkituta.text = "$contadorAciertos/${diferencias.size} "+getString(R.string.encontrado)

        // Reiniciar el EditText
        buscadorEditText.text.clear()

        // Verificar si se encontraron todas las palabras
        if (contadorAciertos == diferencias.size) {
            //Mostrar diálogo de acierto
            val letra = resources.getStringArray(R.array.arrayLetrasActividades).getOrNull(4)?.trim()?.toString() ?: ""
            val puntos = resources.getStringArray(R.array.arrayPuntosActividades).getOrNull(4)?.trim()?.toString() ?: ""

            val mensaje = getString(R.string.felicitacion_actividad)+"\n-"+getString(R.string.letra)+" "+letra+"\n-"+puntos+" "+getString(R.string.puntos)

            generarDialogo(v, getString(R.string.tituloGenerarDialogo), mensaje) {
                //Otorgar puntos si la actividad no se ha completado
                val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
                val valorVariable = sharedPreferences.getInt("ACTIVIDAD_5", 0)
                if (valorVariable == 0) {
                    //Poner ACTIVIDAD_5 con valor 1
                    val editor = sharedPreferences.edit()
                    editor.putInt("ACTIVIDAD_5", 1)
                    editor.apply()

                    //Encontrar el ID del usuario y sumarle X puntos
                    val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", -1)

                    if (idUsuarioActual != -1L) {
                        val dbOperations = DbOperations(this)
                        val usuarioActual = dbOperations.getUserById(idUsuarioActual)

                        if (usuarioActual != null) {
                            val puntosActuales = usuarioActual.puntos
                            val nuevosPuntos = puntosActuales + puntos.toInt()

                            // Actualizar puntos del usuario en la base de datos
                            dbOperations.updateUserPoints(idUsuarioActual, nuevosPuntos)

                            //Actualizar valor de la actividad
                            dbOperations.updateActividad(idUsuarioActual,5)

                            //Actualizar la variable PUNTOS_USUARIO_ACTUAL en SharedPreferences
                            editor.putInt("PUNTOS_USUARIO_ACTUAL", nuevosPuntos)
                            editor.apply()
                        }
                    }
                }

                // Este bloque de código se ejecutará después de cerrar el diálogo
                val intent = Intent(this, Mapa::class.java)
                intent.putExtra("letraActividadCompletada", "T")
                startActivity(intent)
            }
        }
    }

    //MOSTRAR PISTA AL PULSAR EN EL CONTADOR
    fun mostrarPista(view: View) {
        // Obtiene una pista aleatoria de la lista
        val pistaAleatoria = obtenerPistaAleatoria()

        // Muestra la pista
        generarDialogo(view, getString(R.string.tituloPista), pistaAleatoria) {}
    }

    private fun obtenerPistaAleatoria(): String {
        // Genera un índice aleatorio dentro del rango de la lista
        val indiceAleatorio = Random().nextInt(listaPistas.size)

        // Devuelve la pista correspondiente al índice aleatorio
        return listaPistas[indiceAleatorio]
    }

    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }

    //GENERAR DIÁLOGO MODAL
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