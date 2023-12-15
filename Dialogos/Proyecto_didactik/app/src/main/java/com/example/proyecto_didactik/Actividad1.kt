package com.example.proyecto_didactik

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Actividad1 : AppCompatActivity() {
    private var nombreActividad: String? = null
    private var contadorAciertos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad1)

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")
    }

    fun accionCompletado(v: View) {
        if (contadorAciertos == 3) {
            // Todos los textos correctos han sido seleccionados
            val intent = Intent(this, Mapa::class.java)
            intent.putExtra("MensajeActividadCompletada", "Oso Ondo! A letra lortu duzue!")
            intent.putExtra("letraActividadCompletada", "A")
            startActivity(intent)
        } else {
            // Mostrar dialogo modal indicando que quedan respuestas por seleccionar
            showInfoDialog("Ez dituzu erantzun guztiak aukeratu")
        }
    }

    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }

    fun opcionCorrecta(view: View) {
        val textoSeleccionado = view as TextView

        // Cambiar el color de fondo a verde solo si no se ha seleccionado antes
        if (textoSeleccionado.tag == null) {
            textoSeleccionado.setBackgroundColor(Color.GREEN)
            textoSeleccionado.tag = "seleccionado"
            contadorAciertos++
        }
    }

    fun opcionIncorrecta(view: View) {
        val textoSeleccionado = view as TextView

        // Cambiar el color de fondo a rojo
        textoSeleccionado.setBackgroundColor(Color.RED)
    }

    //DIALOGO MODAL PARA MOSTRAR MENSAJES DE ERROR
    fun showInfoDialog(mensaje: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        builder.setTitle("EZ DUZU BUKATU")
            .setMessage(mensaje)
            .setPositiveButton("ONDO") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        val infoDialog = builder.create()
        infoDialog.show()
    }
}