package com.example.proyecto_didactik


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

internal class Mapa : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    //Variables
    private lateinit var mMap: GoogleMap

    lateinit var candado: ImageView
    lateinit var TvNombre: TextView
    lateinit var TvPuntos: TextView

    //Variables estáticas
    companion object {
        lateinit var PALABRA_ABESTIA: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        candado = findViewById(R.id.candado)
        TvNombre = findViewById(R.id.TvNombre)
        TvPuntos = findViewById(R.id.TvPuntos)


        // Inicializamos PALABRA_ABESTIA con el valor almacenado en las preferencias compartidas
        val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
        PALABRA_ABESTIA = sharedPreferences.getString("PALABRA_ABESTIA", "_______") ?: "_______"

        // Recibo el mensaje de la actividad completada (si existe)
        val letra = intent.getStringExtra("letraActividadCompletada")

        // Escribimos el nombre de usuario
        val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", MainActivity.DEFAULT_USER_ID)

        // Obtener la lista de usuarios desde la base de datos
        val dbOperations = DbOperations(this)
        val usuarios = dbOperations.readAllUsers()

        // Establecer el nombre y los puntos del usuario
        val usuario = usuarios.find { it.id == idUsuarioActual }
        if (usuario != null) {
            TvNombre.text = usuario.nombre
            TvPuntos.text = usuario.puntos.toString()+ " " +getString(R.string.puntos)
        }


        // En caso de que exista una actividad completada, actualizamos la palabra
        if (letra != null) {
            when (letra) {
                "A" -> actualizarPalabra(0, letra)
                "B" -> actualizarPalabra(1, letra)
                "E" -> actualizarPalabra(2, letra)
                "S" -> actualizarPalabra(3, letra)
                "T" -> actualizarPalabra(4, letra)
                "I" -> actualizarPalabra(5, letra)
                "X" -> actualizarPalabra(6, "A")
            }

            // Guardamos la palabra actualizada en las preferencias compartidas
            val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("PALABRA_ABESTIA", PALABRA_ABESTIA)
            editor.apply()
        }

        // Mostramos la palabra y el candado si el valor de PALABRA_ABESTIA no es "_______"
        val tvListaLetras = findViewById<TextView>(R.id.TvListaLetras)

        if (PALABRA_ABESTIA != "_______") {
            tvListaLetras.text = PALABRA_ABESTIA
            tvListaLetras.visibility = View.VISIBLE
            candado.visibility = View.VISIBLE
        } else {
            tvListaLetras.text = ""
            tvListaLetras.visibility = View.INVISIBLE
            candado.visibility = View.INVISIBLE
        }

        //Establecer imagen del candado cuando la palabra no está completa
        if(PALABRA_ABESTIA.equals("ABESTIA")){
            candado.visibility = View.INVISIBLE
            //candado.invalidate();
        }else{
            candado.setBackgroundResource(R.drawable.candado);
            candado.invalidate();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun volver(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun mostrarAjustes(view:View){
        val intent = Intent(this, Ajustes::class.java)
        startActivity(intent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Marcador a Getxo
        val getxo = LatLng(43.342119, -3.017551)
        val cameraPosition = CameraPosition.Builder()
            .target(getxo) // Establece la ubicación del centro del mapa
            .zoom(14f)    // Establece el nivel de zoom
            .bearing(-180f)   // Establece la orientación en grados
            .build()

        // Desactivar la brújula
        mMap.uiSettings.isCompassEnabled = false

        // Utiliza la posición de la cámara para actualizar el mapa
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Caracteristicas para el mapa
        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.custommappersonalizado))

        // Marcador de Andra Mariko eliza (Iglesia)
        val eliza = LatLng(43.3629, -3.01772)
        mMap.addMarker(MarkerOptions()
            .position(eliza)
            .title("Andra Mariko eliza")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.iglesia, 150, 150)))

        // Marcador de Aixerrotako Paellak (Paella)
        val paellak = LatLng(43.36303, -3.02443)
        mMap.addMarker(MarkerOptions()
            .position(paellak)
            .title("Aixerrotako Paellak")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.paella, 150, 150)))

        // Marcador de Portu Zaharra (Puerto viejo)
        val portuZaharra = LatLng(43.35305, -3.01664)
        mMap.addMarker(MarkerOptions()
            .position(portuZaharra)
            .title("Portu Zaharra")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.sopa, 150, 150)))

        // Marcador de Portu zaharreko jaiak (Fiestas del puerto viejo)
        val portuZaharreko = LatLng( 43.34978, -3.01618)
        mMap.addMarker(MarkerOptions()
            .position(portuZaharreko)
            .title("Portu zaharreko jaiak")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.jaiak, 150, 150)))

        // Marcador de Jose Luis de Ugarte
        val jsoeLuisUgarte = LatLng(43.33002136690697, -3.014913015931856)
        mMap.addMarker(MarkerOptions()
            .position(jsoeLuisUgarte)
            .title("Jose Luis de Ugarte")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.barco, 150, 150)))

        // Marcador de Punta Begoña
        val puntaBegoña = LatLng(43.34051834268402, -3.0136770625213565)

        mMap.addMarker(MarkerOptions()
            .position(puntaBegoña)
            .title("Punta Begoña")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.puntabegonia, 150, 150)))

        // Marcador de Bizkaiko zubia (Puente de Bizkaia)
        val bizkaikoZubia = LatLng(43.3231207201999, -3.0171309265679445)
        mMap.addMarker(MarkerOptions()
            .position(bizkaikoZubia)
            .title("Bizkaiko zubia")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.puente, 150, 150)))

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Aquí puedes responder al clic en el marcador
        val nombreActividad = marker.title.toString()

        // creamos el intent
        val intent = Intent(this, InterfazComun::class.java)
        intent.putExtra("nombre" , nombreActividad);

        startActivity(intent)
        return true
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int, width: Int, height: Int): BitmapDescriptor? {
        // drawable generator
        var vectorDrawable: Drawable
        vectorDrawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(0, 0, width, height)

        // bitmap generator
        var bitmap: Bitmap
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // canvas generator
        var canvas: Canvas

        // pass bitmap in canvas constructor
        canvas = Canvas(bitmap)

        // pass canvas in drawable
        vectorDrawable.draw(canvas)

        // return BitmapDescriptorFactory
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    //Mostrar palabra completa
    fun ejecutarPalabraCompleta(view: View){
        if(PALABRA_ABESTIA.equals("ABESTIA")){
            val intent = Intent(this, InterfazComun::class.java)
            intent.putExtra("nombre" , "Abestia");

            startActivity(intent)
        }else{
            //generarDialogo(view, "BLOQUEADO", "Completa todas las actividades para desbloquear")
            mostrarActividades(view)
            //AÑADIR TEXTO: Completa todas las actividades para desbloquear
        }
    }

    //Actualizar palabra
    private fun actualizarPalabra(posicion: Int, letra: String) {
        PALABRA_ABESTIA = StringBuilder(PALABRA_ABESTIA).replace(posicion, posicion + 1, letra).toString()
        //PALABRA_ABESTIA = "ABESTIA"
    }

    //Lista de misiones (Mapa)
    fun mostrarActividades(view: View) {
        val dialog = DialogMisionesActivity(this)
        dialog.show()
    }

    //Ranking (Mapa)
    fun mostrarRanking(view: View) {
        // Obtener la lista de usuarios desde la base de datos
        val dbOperations = DbOperations(this)
        val usuarios = dbOperations.readAllUsers()

        val sharedPreferences = getSharedPreferences("UsuarioPreferences", Context.MODE_PRIVATE)
        val idUsuarioActual = sharedPreferences.getLong("ID_USUARIO_ACTUAL", MainActivity.DEFAULT_USER_ID)


        // Convertir la lista de usuarios a la lista de rankings
        val rankings = usuarios.mapIndexed { index, usuario ->
            if(usuario.id == idUsuarioActual){
                Ranking(index + 1, R.drawable.avatarrojo, usuario.nombre, "${usuario.puntos} ${getString(R.string.puntos)}")
            }else{
                val numeroAleatorio = Random.nextInt(3)
                if(numeroAleatorio==0){
                    Ranking(index + 1, R.drawable.avatarazul, usuario.nombre, "${usuario.puntos} ${getString(R.string.puntos)}")
                }else if(numeroAleatorio==1){
                    Ranking(index + 1, R.drawable.avatarverde, usuario.nombre, "${usuario.puntos} ${getString(R.string.puntos)}")
                }else{
                    Ranking(index + 1, R.drawable.avatarrojo, usuario.nombre, "${usuario.puntos} ${getString(R.string.puntos)}")
                }
            }
        }

        // Crear la instancia del diálogo y pasar la lista de rankings
        val dialogRanking = DialogRankingActivity(this, rankings)

        // Mostrar el diálogo
        dialogRanking.show()
    }

    fun home(view: View) {
        if (!isFinishing) {
            val getxo = LatLng(43.342119, -3.017551)
            val cameraPosition = CameraPosition.Builder()
                .target(getxo)
                .zoom(14f)
                .bearing(-180f)
                .build()

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
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