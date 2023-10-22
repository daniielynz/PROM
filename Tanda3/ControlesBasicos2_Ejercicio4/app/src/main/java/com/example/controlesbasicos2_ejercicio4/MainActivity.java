package com.example.controlesbasicos2_ejercicio4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();

        // Generar un número aleatorio entre 0 y 100 (inclusive)
        this.respuesta = ""+(random.nextInt(4)+1);
    }

    public void accionComprobar (View v){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroupNumeros);
        int idSeleccionado = rg.getCheckedRadioButtonId();
        // Sacamos el Texto escrito en el boton seleccionado
        RadioButton buttonSeleccionado = findViewById(idSeleccionado);

        intent.putExtra("numeroElegido" , buttonSeleccionado.getText().toString());
        intent.putExtra("solucion" , respuesta);

        startActivityForResult(intent, 1234);
    }

    protected void onActivityResult (int requestCode, int resultCode,
                                     Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String res = data.getExtras().getString("resultado");
            String numeroOcultar = data.getExtras().getString("ocultar");
            TextView t = (TextView) findViewById(R.id.pruebas);
            if(res.equals("0")){
                if (numeroOcultar.equals("1")) {
                    RadioButton radio = (RadioButton)findViewById(R.id.radio1);
                    radio.setEnabled(false);
                }else if (numeroOcultar.equals("2")) {
                    RadioButton radio = (RadioButton)findViewById(R.id.radio2);
                    radio.setEnabled(false);
                }else if (numeroOcultar.equals("3")) {
                    RadioButton radio = (RadioButton)findViewById(R.id.radio3);
                    radio.setEnabled(false);
                }else if (numeroOcultar.equals("4")) {
                    RadioButton radio = (RadioButton)findViewById(R.id.radio4);
                    radio.setEnabled(false);
                }
            }else{
                juegoNuevo();
            }
        }
    }

    protected void juegoNuevo(){
        // generamos otra respuesta
        Random random = new Random();
        this.respuesta = ""+(random.nextInt(4)+1);
        // volvemos a activar todos los checkbox
        RadioButton radio1 = (RadioButton)findViewById(R.id.radio1);
        radio1.setEnabled(true);
        RadioButton radio2 = (RadioButton)findViewById(R.id.radio2);
        radio2.setEnabled(true);
        RadioButton radio3 = (RadioButton)findViewById(R.id.radio3);
        radio3.setEnabled(true);
        RadioButton radio4 = (RadioButton)findViewById(R.id.radio4);
        radio4.setEnabled(true);
    }
}