package com.example.proyecto_didactik

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")

        val buttonCentro = findViewById<Button>(R.id.botonCentro)

        buttonCentro.setOnClickListener {
            val radioGroup1 = findViewById<RadioGroup>(R.id.radioGroup1)
            val radioGroup2 = findViewById<RadioGroup>(R.id.radioGroup2)
            val radioGroup3 = findViewById<RadioGroup>(R.id.radioGroup3)

            comprobarRadioButtons(radioGroup1, radioGroup2, radioGroup3)
        }
    }

    private fun comprobarRadioButtons(radioGroup1: RadioGroup, radioGroup2: RadioGroup, radioGroup3: RadioGroup) {
        val radioButtonId1 = radioGroup1.checkedRadioButtonId
        val radioButtonId2 = radioGroup2.checkedRadioButtonId
        val radioButtonId3 = radioGroup3.checkedRadioButtonId

        if (radioButtonId1 != -1 && radioButtonId2 != -1 && radioButtonId3 != -1) {
            val radioButton1 = findViewById<RadioButton>(radioButtonId1)
            val radioButton2 = findViewById<RadioButton>(radioButtonId2)
            val radioButton3 = findViewById<RadioButton>(radioButtonId3)

            // Comprobar si los radio buttons seleccionados son los correctos
            if (radioButton1.text == "Itsasontziak arazorik gabe azpitik pasatzeko"
                && radioButton2.text == "50 metro"
                && radioButton3.text == "Getxo eta Portugalete"
            ) {
                val intent = Intent(this, Mapa::class.java)
                intent.putExtra("MensajeActividadCompletada", "Oso Ondo! A letra lortu duzue!")
                intent.putExtra("letraActividadCompletada", "X")
                startActivity(intent)

            } else {
                // Acción a realizar si alguna respuesta es incorrecta
                showInfoDialog("Akatsak daude, zuzendu mesedez!")
            }
        } else {
            // Acción a realizar si no se han seleccionado todas las respuestas
            showInfoDialog("Galdera guztietan erantzun bat hautatu behar da!")
        }
    }

    //DIALOGO MODAL PARA MOSTRAR MENSAJES DE ERROR
    fun showInfoDialog(mensaje: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        builder.setTitle("BIRPASATU")
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
