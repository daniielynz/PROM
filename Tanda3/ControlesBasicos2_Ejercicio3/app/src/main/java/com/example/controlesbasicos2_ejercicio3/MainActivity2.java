package com.example.controlesbasicos2_ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();

        String nombre = extras.getString("nombre") ;
        String apellidos = extras.getString("apellidos") ;
        String sexo = extras.getString("sexo") ;
        ArrayList<String> aficiones = extras.getStringArrayList("aficiones");

        String resultado = "Nombre: "+nombre+"\nApellidos: "+apellidos+"\nSexo: "+sexo+"\nAficiones: ";

        if (aficiones!=null){
            for (String str : aficiones){
                resultado+=" "+str;
            }
        }

        TextView tvResultado = findViewById(R.id.tvResultado);
        tvResultado.setText(resultado);
    }

    public void accionVolver (View v){
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}