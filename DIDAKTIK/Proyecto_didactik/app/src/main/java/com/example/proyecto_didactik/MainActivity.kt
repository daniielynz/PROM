package com.example.proyecto_didactik

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    // Declaración de variables de la interfaz de usuario
    private lateinit var titleTextView: TextView
    private lateinit var belowImageViewText: TextView
    private lateinit var imageView: ImageView
    private lateinit var botonJugar: Button
    private lateinit var bocadillo: TextView

    // Variables para almacenar información del usuario
    private var nombreUsuario: String = ""
    private var puntosUsuario: Int = 0

    // Variable para controlar si la animación está en curso
    private var isAnimationRunning = false

    companion object {
        const val DIALOG_NOMBRE_REQUEST_CODE = 123
        const val DEFAULT_USER_ID: Long = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Inicializar elementos de la interfaz de usuario
        titleTextView = findViewById(R.id.titleTextView)
        belowImageViewText = findViewById(R.id.belowImageViewText)
        imageView = findViewById(R.id.imageView)
        botonJugar = findViewById(R.id.botonJugar)
        bocadillo = findViewById(R.id.bocadillo)

        // Cargar usuario o mostrar diálogo para generarlo
        val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("ID_USUARIO_ACTUAL")) {
            //DEBUG
            Log.d("MainActivity", sharedPreferences.getLong("ID_USUARIO_ACTUAL", DEFAULT_USER_ID).toString() + " idUsuarioActual existe")

            val editor = sharedPreferences.edit()
            editor.clear().apply()
        }
        val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", DEFAULT_USER_ID)

        val dbOperations = DbOperations(this)

        //DEBUG
        Log.d("MainActivity", idUsuarioActual.toString() + " idUsuarioActual")

        if (idUsuarioActual != -1L) {
            //DEBUG
            val usuarios = dbOperations.readAllUsers()
            Log.d("MainActivity", "Número de usuarios en la base de datos: ${usuarios.size}")

            // Cargar usuario desde la base de datos utilizando el ID guardado
            val usuarioGuardado = dbOperations.getUserById(idUsuarioActual)

            // Actualizar campos como nombre, puntos, misiones y ranking
            if (usuarioGuardado != null) {
                nombreUsuario = usuarioGuardado.nombre
                puntosUsuario = usuarioGuardado.puntos
            }
        } else {
            // Crear usuarios iniciales
            val usuarios = dbOperations.readAllUsers()
            if (usuarios.isEmpty()) {
                crearUsuariosIniciales()
            }

            // Solicitar nombre mediante diálogo
            val intent = Intent(this, DialogNombreActivity::class.java)
            intent.putExtra("nombreUsuario", "NombreInicial")
            startActivityForResult(intent, DIALOG_NOMBRE_REQUEST_CODE)
        }
    }

    // Método para crear un usuario con la información proporcionada
    fun crearUsuario(view: View, nombreUsuario: String) {
        val nuevoUsuario = User(
            id = 0, // Poner 0 ya que se asignará automáticamente un ID único
            nombre = nombreUsuario,
            puntos = 0,
            actividad1completada = false,
            actividad2completada = false,
            actividad3completada = false,
            actividad4completada = false,
            actividad5completada = false,
            actividad6completada = false,
            actividad7completada = false
        )

        // Obtener una instancia de DbOperations
        val dbOperations = DbOperations(this)

        // Insertar el nuevo usuario en la base de datos
        val nuevoUsuarioId = dbOperations.insertUser(nuevoUsuario)

        if (nuevoUsuarioId != -1L) {
            // Guardar el ID del usuario en SharedPreferences para futuras referencias
            val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putLong("ID_USUARIO_ACTUAL", nuevoUsuarioId)
            editor.putInt("PUNTOS_USUARIO_ACTUAL", 0)
            editor.putString("PALABRA_ABESTIA", "_______")
            editor.putInt("ACTIVIDAD_1", 0)
            editor.putInt("ACTIVIDAD_2", 0)
            editor.putInt("ACTIVIDAD_3", 0)
            editor.putInt("ACTIVIDAD_4", 0)
            editor.putInt("ACTIVIDAD_5", 0)
            editor.putInt("ACTIVIDAD_6", 0)
            editor.putInt("ACTIVIDAD_7", 0)
            editor.apply()
        } else {
            // Manejar el caso en el que la inserción no fue exitosa
        }
    }

    // Método para mostrar el cuadro de texto con animación
    fun showTextBox(view: View) {
        // Ocultar botón jugar
        botonJugar.visibility = View.INVISIBLE

        // Ocultar bocadillo
        bocadillo.visibility = View.INVISIBLE

        // Cambiar la imagen al pulsar
        Glide.with(this).asGif().load(R.drawable.vasorojomovimiento).placeholder(imageView.drawable).into(imageView)

        // Verificar si la animación ya está en curso
        if (isAnimationRunning) {
            return
        }

        val textToShow = getString(R.string.mensajePrincipal)

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
                    Glide.with(this@MainActivity).asGif().load(R.drawable.vasorojo).placeholder(imageView.drawable).into(imageView)

                    // Mostrar botón jugar
                    botonJugar.visibility = View.VISIBLE
                }
            }
        }
        handler.postDelayed(runnable, delayMillis.toLong())
    }

    // Método para mostrar el botón jugar
    fun botonJugar(view: View) {
        val intent = Intent(this@MainActivity, Mapa::class.java)
        startActivity(intent)
    }

    // Método para crear usuarios por defecto en la base de datos
    private fun crearUsuariosIniciales() {
        // Crea los usuarios iniciales y guárdalos en la base de datos
        val usuario1 = User(0, "Leo Messi", 330, false, true, false, true, false, true, false)
        val usuario2 = User(0, "Cristiano", 335, true, false, true, false, true, false, true)
        val usuario3 = User(0, "Rihanna", 50, true, false, true, false, true, false, true)
        val usuario4 = User(0, "Patxi", 120, true, false, true, false, true, false, true)
        val usuario5 = User(0, "Bululi", 22, true, false, true, false, true, false, true)
        val usuario6 = User(0, "Irati", 69, true, false, true, false, true, false, true)
        val usuario7 = User(0, "Facundo", 1, true, false, true, false, true, false, true)
        val usuario8 = User(0, "Jone", 260, true, false, true, false, true, false, true)

        val dbOperations = DbOperations(this)
        dbOperations.insertUser(usuario1)
        dbOperations.insertUser(usuario2)
        dbOperations.insertUser(usuario3)
        dbOperations.insertUser(usuario4)
        dbOperations.insertUser(usuario5)
        dbOperations.insertUser(usuario6)
        dbOperations.insertUser(usuario7)
        dbOperations.insertUser(usuario8)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DIALOG_NOMBRE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Recibe el nombre del diálogo
            val nombreIngresado = data?.getStringExtra("nombreUsuario")

            if (nombreIngresado != null) {
                crearUsuario(findViewById(android.R.id.content), nombreIngresado)
            }
        }
    }
}