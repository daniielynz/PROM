package com.example.proyecto_didactik


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
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

internal class Mapa : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    companion object {
        val listaLetras: MutableList<Any?> = mutableListOf(null, null, null, null, null, null, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        // Recibo el mensaje de la actividad completada (si existe)
        val MensajeActividadCompletada = intent.getStringExtra("MensajeActividadCompletada")

        // en caso de que exista una actividad completada, mostramos el mensaje
        if(MensajeActividadCompletada != null){
            // Obtenemos la letra de la actividad completada
            val letra = intent.getStringExtra("letraActividadCompletada")

            if(letra == "A"){
                listaLetras[0] = letra
            }else if(letra == "B"){
                listaLetras[1] = letra
            }else if(letra == "E"){
                listaLetras[2] = letra
            }else if(letra == "S"){
                listaLetras[3] = letra
            }else if(letra == "T"){
                listaLetras[4] = letra
            }else if(letra == "I"){
                listaLetras[5] = letra
            }else if(letra == "X"){
                listaLetras[6] = "A"
            }

            Toast.makeText(
                this,
                MensajeActividadCompletada,
                Toast.LENGTH_SHORT
            ).show()
        }
        // en caso de que en la lista de letras haya alguna letra, se muestra
        val tvListaLetras = findViewById<TextView>(R.id.TvListaLetras)

        // creamos el string donde iremos guardando la frase
        var palabra: String = ""
        var mostrar: Boolean = false
        // recorremos la lista de letras
        for (letra in listaLetras) {
            if(letra==null){
                palabra = palabra + "_"
            }else{
                palabra = palabra + letra
                mostrar = true
            }
        }
        if(mostrar){
            tvListaLetras.setText(palabra)
            tvListaLetras.visibility = View.VISIBLE
        }else{
            tvListaLetras.setText("")
            tvListaLetras.visibility = View.INVISIBLE
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
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.custommappersonalizado))

        // Marcador de Andra Mariko eliza (Iglesia)
        val eliza = LatLng(43.3629, -3.01772)
        mMap.addMarker(MarkerOptions()
            .position(eliza)
            .title("Andra Mariko eliza")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.iglesia, 100, 100)))

        // Marcador de Aixerrotako Paellak (Paella)
        val paellak = LatLng(43.36303, -3.02443)
        mMap.addMarker(MarkerOptions()
            .position(paellak)
            .title("Aixerrotako Paellak")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.paella, 100, 100)))

        // Marcador de Portu Zaharra (Puerto viejo)
        val portuZaharra = LatLng(43.34905, -3.01464)
        mMap.addMarker(MarkerOptions()
            .position(portuZaharra)
            .title("Portu Zaharra")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.pez, 100, 100)))

        // Marcador de Portu zaharreko jaiak (Fiestas del puerto viejo)
        val portuZaharreko = LatLng( 43.34978, -3.01618)
        mMap.addMarker(MarkerOptions()
            .position(portuZaharreko)
            .title("Portu zaharreko jaiak")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.iconovaca, 100, 100)))

        // Marcador de Jose Luis de Ugarte
        val jsoeLuisUgarte = LatLng(43.33002136690697, -3.014913015931856)
        mMap.addMarker(MarkerOptions()
            .position(jsoeLuisUgarte)
            .title("Jose Luis de Ugarte")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.barco, 100, 100)))

        // Marcador de Punta Begoña
        val puntaBegoña = LatLng(43.34051834268402, -3.0136770625213565)

        mMap.addMarker(MarkerOptions()
            .position(puntaBegoña)
            .title("Punta Begoña")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.puntabegonia, 100, 100)))

        // Marcador de Bizkaiko zubia (Puente de Bizkaia)
        val bizkaikoZubia = LatLng(43.3231207201999, -3.0171309265679445)
        mMap.addMarker(MarkerOptions()
            .position(bizkaikoZubia)
            .title("Bizkaiko zubia")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.puente, 100, 100)))
        // PRUEBA
        val video = LatLng(43.31, -3.014)
        mMap.addMarker(MarkerOptions()
            .position(video)
            .title("Abestia")
            .icon(bitmapFromVector(getApplicationContext(), R.drawable.logokali, 100, 100)))

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

}

