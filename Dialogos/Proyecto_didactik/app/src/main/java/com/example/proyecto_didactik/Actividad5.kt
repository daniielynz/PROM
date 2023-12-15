package com.example.proyecto_didactik

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class Actividad5 : AppCompatActivity() {
    private var nombreActividad: String? = null
    private val diferencias = listOf("biribilgunea", "harea", "portua", "etxeak", "zubia") //"rotonda", "arena", "puerto", "casa", "puente"
    private val respuestasUtilizadas = mutableListOf<String>()
    private var contadorAciertos = 0

    // Lista de pistas
    private val listaPistas = listOf(
        "Gune biribila da",
        "Hondartzak daukate ura eta...",
        "Itsasontziak ainguratuta dauden tokia",
        "Baita zu ... batean bizi zara",
        "Ibai bat zeharkatzeko erabiltzen da",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad5)

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")
    }

    fun accionCompletado(v: View) {
        val buscadorEditText: EditText = findViewById(R.id.buscador)
        val textoAurkituta: TextView = findViewById(R.id.aurkituta)

        val respuesta = buscadorEditText.text.toString().toLowerCase()

        if (respuesta.isNotEmpty() && respuesta in diferencias && respuesta !in respuestasUtilizadas) {
            // Respuesta correcta y no utilizada previamente
            contadorAciertos++
            respuestasUtilizadas.add(respuesta)
        }

        // Actualizar el texto aurkituta
        textoAurkituta.text = "$contadorAciertos/${diferencias.size} aurkituta"

        // Reiniciar el EditText
        buscadorEditText.text.clear()

        // Verificar si se encontraron todas las palabras
        if (contadorAciertos == diferencias.size) {
            // Todas las palabras encontradas, realizar la acción Completado
            val intent = Intent(this, Mapa::class.java)
            intent.putExtra("MensajeActividadCompletada", "Oso Ondo! T letra lortu duzue!")
            intent.putExtra("letraActividadCompletada", "T")
            startActivity(intent)
        }
    }

    //MOSTRAR PISTA AL PULSAR EN EL CONTADOR
    fun mostrarPista(view: View) {
        // Obtiene una pista aleatoria de la lista
        val pistaAleatoria = obtenerPistaAleatoria()

        // Muestra la pista en un Toast
        showInfoDialog(pistaAleatoria)
    }

    private fun obtenerPistaAleatoria(): String {
        // Genera un índice aleatorio dentro del rango de la lista
        val indiceAleatorio = Random().nextInt(listaPistas.size)

        // Devuelve la pista correspondiente al índice aleatorio
        return listaPistas[indiceAleatorio]
    }

    //DIALOGO MODAL PARA MOSTRAR MENSAJES DE ERROR
    fun showInfoDialog(mensaje: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        builder.setTitle("PISTA")
            .setMessage(mensaje)
            .setPositiveButton("ONDO") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        val infoDialog = builder.create()
        infoDialog.show()
    }

    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }
}