package com.example.ejerciciofragments;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
public class DetalleActivity extends AppCompatActivity {
    public static final String EXTRA_TEXTO =
            "com.example.ejfragments.EXTRA_TEXTO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        FragmentDetalle detalle = (FragmentDetalle)
                getSupportFragmentManager().findFragmentById(R.id.frgDetalle);
        detalle.mostrarDetalle(getIntent().getStringExtra(EXTRA_TEXTO));
    }
}
