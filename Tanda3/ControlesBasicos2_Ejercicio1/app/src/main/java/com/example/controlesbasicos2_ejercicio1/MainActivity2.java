package com.example.controlesbasicos2_ejercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();

        String nombre = extras.getString("nombre");
        String apellidos = extras.getString("apellidos");

        TextView resultado = findViewById(R.id.tvResultado);

        resultado.setText("Hola "+nombre+" "+apellidos+" ¿Aceptas las condiciones?");
    }

    public void accionVolverAceptar (View v){
        Intent intent = new Intent();

        TextView resultadoAceptar = findViewById(R.id.btnAceptar);

        intent.putExtra("resultado" , resultadoAceptar.getText());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void accionVolverRechazar (View v){
        Intent intent = new Intent();

        TextView resultadoRechazar = findViewById(R.id.btnRechazar);

        intent.putExtra("resultado" , resultadoRechazar.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}