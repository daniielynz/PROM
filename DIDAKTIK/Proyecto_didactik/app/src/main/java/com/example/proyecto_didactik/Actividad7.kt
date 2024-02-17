package com.example.proyecto_didactik

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Actividad7 : AppCompatActivity() {
    private var nombreActividad: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad7)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")

        val buttonCentro = findViewById<Button>(R.id.botonCentro)

        buttonCentro.setOnClickListener {
            val radioGroup1 = findViewById<RadioGroup>(R.id.radioGroup1)
            val radioGroup2 = findViewById<RadioGroup>(R.id.radioGroup2)
            val radioGroup3 = findViewById<RadioGroup>(R.id.radioGroup3)

            comprobarRadioButtons(it, radioGroup1, radioGroup2, radioGroup3)
        }
    }

    private fun comprobarRadioButtons(view: View, radioGroup1: RadioGroup, radioGroup2: RadioGroup, radioGroup3: RadioGroup) {
        val radioButtonId1 = radioGroup1.checkedRadioButtonId
        val radioButtonId2 = radioGroup2.checkedRadioButtonId
        val radioButtonId3 = radioGroup3.checkedRadioButtonId

        if (radioButtonId1 != -1 && radioButtonId2 != -1 && radioButtonId3 != -1) {
            val radioButton1 = findViewById<RadioButton>(radioButtonId1)
            val radioButton2 = findViewById<RadioButton>(radioButtonId2)
            val radioButton3 = findViewById<RadioButton>(radioButtonId3)

            // Comprobar si los radio buttons seleccionados son los correctos
            if (radioButton1.text == getString(R.string.testP1O1)
                && radioButton2.text == getString(R.string.testP2O3)
                && radioButton3.text == getString(R.string.testP3O1)
            ) {
                // Mostrar diálogo de acierto
                val letra = resources.getStringArray(R.array.arrayLetrasActividades).getOrNull(6)?.trim()?.toString() ?: ""
                val puntos = resources.getStringArray(R.array.arrayPuntosActividades).getOrNull(6)?.trim()?.toString() ?: ""

                val mensaje = getString(R.string.felicitacion_actividad)+"\n-"+getString(R.string.letra)+" "+letra+"\n-"+puntos+" "+getString(R.string.puntos)

                generarDialogo(view, getString(R.string.tituloGenerarDialogo), mensaje) {
                    // Otorgar puntos si la actividad no se ha completado
                    val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
                    val valorVariable = sharedPreferences.getInt("ACTIVIDAD_7", 0)
                    if (valorVariable == 0) {
                        // Poner ACTIVIDAD_7 con valor 1
                        val editor = sharedPreferences.edit()
                        editor.putInt("ACTIVIDAD_7", 1)
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
                                dbOperations.updateActividad(idUsuarioActual, 7)

                                // Actualizar la variable PUNTOS_USUARIO_ACTUAL en SharedPreferences
                                editor.putInt("PUNTOS_USUARIO_ACTUAL", nuevosPuntos)
                                editor.apply()
                            }
                        }
                    }

                    // Este bloque de código se ejecutará después de cerrar el diálogo
                    val intent = Intent(view.context, Mapa::class.java)
                    intent.putExtra("letraActividadCompletada", "X")
                    view.context.startActivity(intent)
                }
            } else {
                // Acción a realizar si alguna respuesta es incorrecta
                generarDialogo(view, getString(R.string.tituloErrores), getString(R.string.errorActividad2)) {}
            }
        } else {
            // Acción a realizar si no se han seleccionado todas las respuestas
            generarDialogo(view, getString(R.string.tituloErrores), getString(R.string.errorActividad3)) {}
        }
    }


    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }

    // GENERAR DIÁLOGO MODAL
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