package com.example.ejerciciofragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements FragmentListado.PeliculaListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentListado fragmentListado = (FragmentListado)
                getSupportFragmentManager().findFragmentById(R.id.frgListado);
        fragmentListado.setPeliculaListener(this);
    }

    @Override
    public void onPeliculaSeleccionada(Pelicula c) {
        boolean hayDetalle = (getSupportFragmentManager().
                findFragmentById(R.id.frgDetalle)!= null);
        if (hayDetalle) {
            ((FragmentDetalle)getSupportFragmentManager().
                    findFragmentById(R.id.frgDetalle)).
                    mostrarDetalle(c.getSinopsis());
        }
        else {
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(DetalleActivity.EXTRA_TEXTO, c.getSinopsis());
            startActivity(i);
        }
    }
}