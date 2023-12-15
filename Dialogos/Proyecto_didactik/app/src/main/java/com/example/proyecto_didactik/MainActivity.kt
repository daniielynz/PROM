package com.example.proyecto_didactik

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var belowImageViewText: TextView
    private lateinit var imageView: ImageView
    private lateinit var infoButton: Button

    private var isAnimationRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        belowImageViewText = findViewById(R.id.belowImageViewText)
        imageView = findViewById(R.id.imageView)
        infoButton = findViewById(R.id.infoButton)
    }

    fun showTextBox(view: View) {
        //Cambiar la imagen al pulsar
        Glide.with(this).asGif().load(R.drawable.vasorojomovimiento).placeholder(imageView.getDrawable()).into(imageView)


        // Verificar si la animación ya está en curso
        if (isAnimationRunning) {
            return
        }

        val textToShow = "Kaixo! Nire izena Kali da eta Getxoko kultura ondarea aurkeztuko dizuet.\n" +
                "Horretarako Getxoko zazpi leku ezberdin bisitatuko ditugu!\n" +
                "Baina adi egon! Jarduera batzuk egin beharko dituzue! Etorri nirekin!\nHEMEN SAKATU JOLASTEKO"
//        val textToShow = "Hola"

        belowImageViewText.text = ""
        isAnimationRunning = true

        val handler = Handler()

        val delayMillis = 0
        var currentIndex = 0

        val runnable = object : Runnable {
            override fun run() {
                if (currentIndex <= textToShow.length) {
                    belowImageViewText.text = textToShow.substring(0, currentIndex)
                    currentIndex++
                    handler.postDelayed(this, delayMillis.toLong())
                } else {
                    isAnimationRunning = false
                    Glide.with(this@MainActivity).asGif().load(R.drawable.vasorojo).placeholder(imageView.getDrawable()).into(imageView)

                    belowImageViewText.setOnClickListener {
                        // Aquí debes iniciar la segunda actividad o realizar la acción deseada
                        belowImageViewText.text = ""
                        // crear el intent
                        val intent = Intent(this@MainActivity, Mapa::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        handler.postDelayed(runnable, delayMillis.toLong())
    }

    // DIALOGO MODAL PARA MOSTRAR VERSIÓN Y AUTORES
    fun showInfoDialog(view: View) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        builder.setTitle("GetxoMua-ri buruz")
            .setMessage("EGILEAK:\n \tAdrián Llarena\n \tDaniel Yañez\n \tIker Pérez-Cárcamo\n\nLAGUNTZAILEAK:\n \tLara Casanueva\n \tMikel De la Torre\n \tIrune García\n \tMaider Hombreiro\n\nBERTSIOA: 1.0\n\n \u00a9 2023-2024")
            .setPositiveButton("ONDO") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        val infoDialog = builder.create()
        infoDialog.show()
    }
}