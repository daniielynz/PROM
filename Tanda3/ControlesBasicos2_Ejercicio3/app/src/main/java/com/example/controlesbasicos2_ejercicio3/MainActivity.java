package com.example.controlesbasicos2_ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void accionEnviar (View v){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

        // Datos del nombre
        EditText nombre = findViewById(R.id.etNombre);
        intent.putExtra("nombre" , nombre.getText().toString());

        // Datos de los apellidos
        EditText apellidos = findViewById(R.id.etApellidos);
        intent.putExtra("apellidos" , apellidos.getText().toString());
/*
        // Datos de los radiobutton
        // Sacamos el id del radiobutton seleccionado
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroupSexo);
        int idSeleccionado = rg.getCheckedRadioButtonId();
        // Sacamos el Texto escrito en el boton seleccionado
        RadioButton buttonSeleccionado = findViewById(idSeleccionado);
        intent.putExtra("sexo" , buttonSeleccionado.getText().toString());

        // Datos de los checkbox
        CheckBox c1 = findViewById(R.id.checkbox1);
        CheckBox c2 = findViewById(R.id.checkbox2);
        CheckBox c3 = findViewById(R.id.checkbox3);
        CheckBox c4 = findViewById(R.id.checkbox4);

        ArrayList<String> arrlCheck = new ArrayList<String>();
        if(c1.isChecked()){
            arrlCheck.add(c1.getText().toString());
        }
        if(c2.isChecked()){
            arrlCheck.add(c2.getText().toString());
        }
        if(c3.isChecked()){
            arrlCheck.add(c3.getText().toString());
        }
        if(c4.isChecked()){
            arrlCheck.add(c2.getText().toString());
        }

        intent.putExtra("aficiones" , arrlCheck);

*/
        startActivity(intent);
    }
}