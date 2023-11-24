package com.example.notificaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewContador;
    private CheckBox checkConteoNegativo;
    private EditText editTextResetear;
    public int contador;
    private boolean conteoNegativoActivado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // cargamos los datos del formulario
        textViewContador = findViewById(R.id.textViewContador);
        conteoNegativoActivado = false;

        contador = 0;
        textViewContador = findViewById(R.id.textViewContador);
        textViewContador.setText(contador+"");


    }

    public void accionVolver(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void accionSumar(View v){
        contador++;
        if(contador >= 0){
            textViewContador.setTextColor(Color.BLACK);
        }
        textViewContador.setText(contador+"");
    }

    public void accionRestar(View v){
        // comprobamos si el checbox esta seleccionado
        checkConteoNegativo = findViewById(R.id.checkConteoNegativo);
        if(checkConteoNegativo.isChecked()){
            conteoNegativoActivado = true;
        }else{
            conteoNegativoActivado = false;
        }
        // si el checkbox no esta seleccionado no se pueden poner numeros negativos
        if(!conteoNegativoActivado){
            if(contador>0){
                contador--;
            }
        }else{
            contador--;
        }
        if(contador < 0){
            textViewContador.setTextColor(Color.RED);
        }
        textViewContador.setText(contador+"");

    }

    public void accionResetear(View v){
        editTextResetear = findViewById(R.id.editTextResetear);

        if(editTextResetear.getText().toString().isEmpty()){
            contador = 0;
        }else{
            contador = Integer.parseInt(editTextResetear.getText().toString());
        }
        if(contador < 0){
            textViewContador.setTextColor(Color.RED);
        }else{
            textViewContador.setTextColor(Color.BLACK);
        }
        textViewContador.setText(contador+"");
    }

    public void salir(View view){
        finish();
    }

}