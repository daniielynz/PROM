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

        int nombre = Integer.parseInt(extras.getString("nombre")) ;
        int apellidos = Integer.parseInt(extras.getString("apellidos")) ;
      //  int sexo = Integer.parseInt(extras.getString("sexo")) ;
     //   ArrayList<String> aficiones = extras.getStringArrayList("aficiones");

     //   String resultado = "Nombre y apellidos : "+nombre+" "+apellidos+"/t Sexo:"+sexo+"/t Aficiones: ";
        String resultado = "Nombre y apellidos : "+nombre+" "+apellidos+"/t Sexo: ";
/*
        if (aficiones!=null){
            for (String str : aficiones){
                resultado+=" "+str;
            }
        } */

        TextView tvResultado = findViewById(R.id.tvResultado);
        tvResultado.setText(resultado);
    }

    public void accionVolver (View v){

    }
}