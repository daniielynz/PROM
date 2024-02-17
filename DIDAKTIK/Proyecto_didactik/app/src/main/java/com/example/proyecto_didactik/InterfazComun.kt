package com.example.proyecto_didactik

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class InterfazComun : AppCompatActivity() {

    private lateinit var textoCentralSuperior: TextView
    private lateinit var textoCentralAbajo: TextView
    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var botonInferior: Button
    private lateinit var botonIzquierdaSuperior: ImageView
    lateinit var mapaDeListas: Map<String, List<String>>
    var nombreActividad: String = ""

    private var isThumbnailShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.interfaz_comun)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Nombre Actividad, titulo, texto, video, intent inferior
        mapaDeListas = mapOf(
            "Andra Mariko eliza" to listOf(getString(R.string.mision1), getString(R.string.descripcion1), "andra_mari", "Actividad1"),
            "Aixerrotako Paellak" to listOf(getString(R.string.mision2), getString(R.string.descripcion2), "paellak", "Actividad2"),
            //... (otros elementos del mapa)
        )

        // Acceder al valor del Intent
        nombreActividad = intent.getStringExtra("nombre").toString()

        // Usar el valor del Intent
        val listaDeValores = mapaDeListas[nombreActividad]

        textoCentralSuperior = findViewById(R.id.textoCentralSuperior)
        textoCentralAbajo = findViewById(R.id.textoCentralAbajo)
        videoView = findViewById(R.id.videoView)
        botonInferior = findViewById(R.id.botonInferior)
        botonIzquierdaSuperior = findViewById(R.id.botonIzquierdaSuperior)

        // Verificar si hay un valor asociado con la clave y si la lista tiene al menos 4 elementos
        if (nombreActividad != null && listaDeValores != null && listaDeValores.size >= 4) {
            // Establecer el texto en las vistas
            textoCentralSuperior.text = listaDeValores[0]
            textoCentralAbajo.text = listaDeValores[1]

            // Obtener el ID del recurso raw a partir del nombre en la lista
            val resourceId = resources.getIdentifier(listaDeValores[2], "raw", packageName)

            // Configurar el video solo si se encuentra el recurso
            if (resourceId != 0) {
                val uri = Uri.parse("android.resource://" + packageName + "/" + resourceId)
                videoView.setVideoURI(uri)

                mediaController = MediaController(this)
                videoView.setMediaController(mediaController)

                mediaController = object : MediaController(this) {
                    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                        if (ev.action == MotionEvent.ACTION_UP) {
                            // Se ha tocado el MediaController, ocultar la miniatura
                            if (isThumbnailShown) {
                                videoView.setBackground(null)
                                isThumbnailShown = false
                            }
                        }
                        return super.dispatchTouchEvent(ev)
                    }
                }

                videoView.setMediaController(mediaController)

                videoView.setOnPreparedListener {
                    if (!isThumbnailShown) {
                        // Mostrar la miniatura solo si aún no se ha mostrado
                        val thumbnail = getVideoThumbnail(uri)
                        val bitmapDrawable = BitmapDrawable(resources, thumbnail)
                        videoView.background = bitmapDrawable
                        isThumbnailShown = true
                    }
                }
            }
            if (nombreActividad == "Abestia") {
                botonInferior.text = getString(R.string.botonAcabar)
                botonIzquierdaSuperior.visibility = View.INVISIBLE
            }
        }

        val botonInferior: Button = findViewById(R.id.botonInferior)
        botonInferior.setOnClickListener {
            // Verificar si hay un valor asociado con la clave y si la lista tiene al menos 4 elementos
            if (nombreActividad != null && listaDeValores != null && listaDeValores.size >= 4) {
                // Obtener el cuarto valor de la lista
                val claseDestino = listaDeValores[3]

                // Determinar a qué clase debe apuntar el Intent
                val intent = when (claseDestino) {
                    "MainActivity" -> Intent(this, MainActivity::class.java)
                    "Actividad1" -> Intent(this, Actividad1::class.java)
                    "Actividad2" -> Intent(this, Actividad2::class.java)
                    "Actividad3" -> Intent(this, Actividad3::class.java)
                    "Actividad4" -> Intent(this, Actividad4::class.java)
                    "Actividad5" -> Intent(this, Actividad5::class.java)
                    "Actividad6" -> Intent(this, Actividad6::class.java)
                    "Actividad7" -> Intent(this, Actividad7::class.java)
                    "Abestia" -> Intent(this, MainActivity::class.java)
                    //Poner aqui las 7
                    else -> {
                        //Volver a MainActivity si no existe
                        Intent(this, MainActivity::class.java)
                    }
                }
                intent.putExtra("nombre", nombreActividad);
                // Iniciar la nueva actividad
                startActivity(intent)

                // Finalizar la actividad actual si no necesitas mantenerla en el stack
                finish()
            }
        }

        val botonIzquierdaSuperior: ImageView = findViewById(R.id.botonIzquierdaSuperior)
        botonIzquierdaSuperior.setOnClickListener {
            val intent = Intent(this, Mapa::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)

            // Finalizar la actividad actual si no necesitas mantenerla en el stack
            finish()
        }
    }

    //Obtener miniatura para el vídeo
    private fun getVideoThumbnail(uri: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(this, uri)
            return retriever.frameAtTime
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        return null
    }
}