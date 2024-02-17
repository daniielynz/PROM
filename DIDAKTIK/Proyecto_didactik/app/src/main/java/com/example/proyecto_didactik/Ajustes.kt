package com.example.proyecto_didactik

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Locale

class Ajustes : Activity() {

    // Elementos de la interfaz de usuario
    private lateinit var btnSpanish: Button
    private lateinit var btnBasque: Button
    private lateinit var btnEnglish: Button

    private lateinit var layoutEs: LinearLayout
    private lateinit var layoutEn: LinearLayout
    private lateinit var layoutEu: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_ajustes)

        // Configuración de la ventana de ajustes
        setFinishOnTouchOutside(false)

        // Botón para cerrar la ventana de ajustes
        val btnCerrar = findViewById<Button>(R.id.acceptButtonAjustes)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        //Cerrar ajustes
        btnCerrar.setOnClickListener { finish() }

        //Idiomas
        btnSpanish = findViewById(R.id.btnSpanish);
        btnBasque = findViewById(R.id.btnBasque);
        btnEnglish = findViewById(R.id.btnEnglish);

        //Layout de idiomas
        layoutEs = findViewById(R.id.layoutEs);
        layoutEn = findViewById(R.id.layoutEn);
        layoutEu = findViewById(R.id.layoutEu);
    }

    //Establecer idiomas
    fun idiomaES(view:View) {
        setLocale("es",this);
        recreate();
    }

    fun idiomaEU(view:View) {
        setLocale("eu",this);
        recreate();
    }

    fun idiomaEN(view:View) {
        setLocale("en",this);
        recreate();
    }

    // Función para cambiar el idioma de la aplicación
    fun setLocale(languageCode: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("IDIOMA_SELECCIONADO", languageCode)
        editor.apply()

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }


    //Reiniciar app
    fun reiniciarApp(view: View) {
        // Crear un cuadro de diálogo de confirmación
        val alertDialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.borrar_informacion))
            .setMessage(getString(R.string.texto_borrar_informacion))
            .setPositiveButton(getString(R.string.si)) { _, _ ->
                crearUsuarioBBDD(view)

                // Reiniciar la aplicación
                val intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()

        // Personalizar el fondo del cuadro de diálogo
        val dialogView = alertDialog.window?.decorView
        dialogView?.setBackgroundResource(R.color.masOscuro)
    }

    //Funciones de borrado de usuarios (Ajustes)
    fun crearUsuarioBBDD(view:View){
        //Crear usuario
        clearSharedPreferences(this@Ajustes, "UsuarioPreferences")
        clearDatabase(this)
    }

    //Mostrar acerca de
    fun showInfoDialog(view: View) {
        generarDialogo(view, getString(R.string.tituloCreditos), getString(R.string.creditos))
    }

    //Borrar fichero de usuario -> clearSharedPreferences(context, "UsuarioPreferences")
    fun clearSharedPreferences(context: Context, sharedPreferencesName: String) {
        val sharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    //Borrar base de datos -> clearDatabase(this)
    fun clearDatabase(context: Context) {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        // Eliminar la tabla "usuarios"
        db.execSQL("DROP TABLE IF EXISTS ${DbContract.UserEntry.TABLE_NAME}")

        // Volver a crear la base de datos
        dbHelper.onCreate(db)

        db.close()
        dbHelper.close()
    }

    //GENERAR DIÁLOGO MODAL
    fun generarDialogo(view: View, titulo: String, contenido: String){
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme) // Asigna un estilo personalizado
        val inflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.modal_layout, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = dialogView.findViewById<TextView>(R.id.contentTextView)
        val acceptButton = dialogView.findViewById<Button>(R.id.acceptButton)

        titleTextView.text = titulo
        contentTextView.text = contenido

        val alertDialog = builder.setView(dialogView).create()

        // Evita que el diálogo se cierre al tocar fuera de él
        alertDialog.setCanceledOnTouchOutside(false)

        acceptButton.setOnClickListener {
            //Cierra la ventana modal al hacer clic en el botón Aceptar
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}