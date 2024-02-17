package com.example.proyecto_didactik

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible


class Actividad2 : AppCompatActivity() {
    // Variables y propiedades
    private var nombreActividad: String? = null

    private lateinit var puzzleZone: ViewGroup
    private lateinit var marcoZone: ViewGroup

    private lateinit var puzzle3x2p1: ImageView
    private lateinit var puzzle3x2p2: ImageView
    private lateinit var puzzle3x2p3: ImageView
    private lateinit var puzzle3x2p4: ImageView
    private lateinit var puzzle3x2p5: ImageView
    private lateinit var puzzle3x2p6: ImageView

    private lateinit var marco1: ImageView
    private lateinit var marco2: ImageView
    private lateinit var marco3: ImageView
    private lateinit var marco4: ImageView
    private lateinit var marco5: ImageView
    private lateinit var marco6: ImageView

    private var draggedImage: ImageView? = null

    private var bien1: Boolean = false
    private var bien2: Boolean = false
    private var bien3: Boolean = false
    private var bien4: Boolean = false
    private var bien5: Boolean = false
    private var bien6: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad2)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val extras: Bundle? = intent.extras
        nombreActividad = extras?.getString("nombre")

        puzzleZone = findViewById(R.id.puzzleZone)
        marcoZone = findViewById(R.id.marcoZone)

        puzzle3x2p1 = findViewById(R.id.puzzle3x2p1)
        puzzle3x2p2 = findViewById(R.id.puzzle3x2p2)
        puzzle3x2p3 = findViewById(R.id.puzzle3x2p3)
        puzzle3x2p4 = findViewById(R.id.puzzle3x2p4)
        puzzle3x2p5 = findViewById(R.id.puzzle3x2p5)
        puzzle3x2p6 = findViewById(R.id.puzzle3x2p6)

        marco1 = findViewById(R.id.marco1)
        marco2 = findViewById(R.id.marco2)
        marco3 = findViewById(R.id.marco3)
        marco4 = findViewById(R.id.marco4)
        marco5 = findViewById(R.id.marco5)
        marco6 = findViewById(R.id.marco6)

        // Configurar listeners de arrastrar y soltar para las imágenes del puzzle
        puzzle3x2p1.setOnTouchListener(TouchListener())
        puzzle3x2p2.setOnTouchListener(TouchListener())
        puzzle3x2p3.setOnTouchListener(TouchListener())
        puzzle3x2p4.setOnTouchListener(TouchListener())
        puzzle3x2p5.setOnTouchListener(TouchListener())
        puzzle3x2p6.setOnTouchListener(TouchListener())

        marco1.setOnDragListener(DragListener())
        marco2.setOnDragListener(DragListener())
        marco3.setOnDragListener(DragListener())
        marco4.setOnDragListener(DragListener())
        marco5.setOnDragListener(DragListener())
        marco6.setOnDragListener(DragListener())
    }

    fun volver(view: View) {
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre", nombreActividad)
        startActivity(intent)
    }

    // Lógica del evento táctil para las imágenes del puzzle
    private inner class TouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Guardar la información de la puzzle3x2p arrastrada y su posición inicial
                draggedImage = view as ImageView
                val clipData = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                view.startDrag(clipData, shadowBuilder, view, 0)
                return true
            }
            return false
        }
    }

    // Lógica del evento de arrastrar y soltar para los marcos
    private inner class DragListener : View.OnDragListener {
        override fun onDrag(view: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    // Verificar si la puzzle3x2p se soltó en un marco correcto
                    val droppedImage = event.localState as ImageView
                    if (view == marco1 && droppedImage == puzzle3x2p1) {
                        marco1.setImageDrawable(droppedImage.drawable)
                        marco1.visibility = View.INVISIBLE
                        puzzle3x2p1.visibility = View.INVISIBLE
                        bien1 = true

                    } else if (view == marco2 && droppedImage == puzzle3x2p2) {
                        marco2.setImageDrawable(droppedImage.drawable)
                        marco2.visibility = View.INVISIBLE
                        puzzle3x2p2.visibility = View.INVISIBLE
                        bien2 = true
                    } else if (view == marco3 && droppedImage == puzzle3x2p3) {
                        marco3.setImageDrawable(droppedImage.drawable)
                        marco3.visibility = View.INVISIBLE
                        puzzle3x2p3.visibility = View.INVISIBLE
                        bien3 = true
                    } else if (view == marco4 && droppedImage == puzzle3x2p4) {
                        marco4.setImageDrawable(droppedImage.drawable)
                        marco4.visibility = View.INVISIBLE
                        puzzle3x2p4.visibility = View.INVISIBLE
                        bien4 = true
                    } else if (view == marco5 && droppedImage == puzzle3x2p5) {
                        marco5.setImageDrawable(droppedImage.drawable)
                        marco5.visibility = View.INVISIBLE
                        puzzle3x2p5.visibility = View.INVISIBLE
                        bien5 = true
                    } else if (view == marco6 && droppedImage == puzzle3x2p6) {
                        marco6.setImageDrawable(droppedImage.drawable)
                        marco6.visibility = View.INVISIBLE
                        puzzle3x2p6.visibility = View.INVISIBLE
                        bien6 = true
                    } else {
                        // Volver a colocar la puzzle3x2p en su posición inicial
                        val layoutParams = droppedImage.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.leftMargin = 0
                        layoutParams.topMargin = 0
                        droppedImage.layoutParams = layoutParams
                    }
                    if (bien1 && bien2 && bien3 && bien4 && bien5 && bien6){
                        //Mostrar diálogo de acierto
                        val letra = resources.getStringArray(R.array.arrayLetrasActividades).getOrNull(1)?.trim()?.toString() ?: ""
                        val puntos = resources.getStringArray(R.array.arrayPuntosActividades).getOrNull(1)?.trim()?.toString() ?: ""

                        val mensaje = getString(R.string.felicitacion_actividad)+"\n-"+getString(R.string.letra)+" "+letra+"\n-"+puntos+" "+getString(R.string.puntos)

                        generarDialogo(view, getString(R.string.tituloGenerarDialogo), mensaje) {
                            //Otorgar puntos si la actividad no se ha completado
                            val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
                            val valorVariable = sharedPreferences.getInt("ACTIVIDAD_2", 0)
                            if (valorVariable == 0) {
                                //Poner ACTIVIDAD_2 con valor 1
                                val editor = sharedPreferences.edit()
                                editor.putInt("ACTIVIDAD_2", 1)
                                editor.apply()

                                //Encontrar el ID del usuario y sumarle X puntos
                                val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", -1)

                                if (idUsuarioActual != -1L) {
                                    val dbOperations = DbOperations(this@Actividad2)
                                    val usuarioActual = dbOperations.getUserById(idUsuarioActual)

                                    if (usuarioActual != null) {
                                        val puntosActuales = usuarioActual.puntos
                                        val nuevosPuntos = puntosActuales + puntos.toInt()

                                        // Actualizar puntos del usuario en la base de datos
                                        dbOperations.updateUserPoints(idUsuarioActual, nuevosPuntos)

                                        //Actualizar valor de la actividad
                                        dbOperations.updateActividad(idUsuarioActual,2)

                                        //Actualizar la variable PUNTOS_USUARIO_ACTUAL en SharedPreferences
                                        editor.putInt("PUNTOS_USUARIO_ACTUAL", nuevosPuntos)
                                        editor.apply()
                                    }
                                }
                            }

                            // Este bloque de código se ejecutará después de cerrar el diálogo
                            val intent = Intent(this@Actividad2, Mapa::class.java)
                            intent.putExtra("letraActividadCompletada", "B")
                            startActivity(intent)
                        }
                    }
                }
            }
            return true
        }
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