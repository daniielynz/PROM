package com.example.dni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void botonValidarLetra (View v){
        EditText numero = findViewById(R.id.numDNI);
        EditText letra = findViewById(R.id.letraDNI);
        TextView lbResultado = findViewById(R.id.lblResultado);

        int resutadoFormula = Integer.parseInt(numero.getText().toString())%23;

        Map<Integer, Character> arrayAsociativo = new HashMap<>();

        // Agregar elementos al array asociativo
        arrayAsociativo.put(0, 'T');
        arrayAsociativo.put(1, 'R');
        arrayAsociativo.put(2, 'W');
        arrayAsociativo.put(3, 'A');
        arrayAsociativo.put(4, 'G');
        arrayAsociativo.put(5, 'M');
        arrayAsociativo.put(6, 'Y');
        arrayAsociativo.put(7, 'F');
        arrayAsociativo.put(8, 'P');
        arrayAsociativo.put(9, 'D');
        arrayAsociativo.put(10, 'X');
        arrayAsociativo.put(11, 'B');
        arrayAsociativo.put(12, 'N');
        arrayAsociativo.put(13, 'J');
        arrayAsociativo.put(14, 'Z');
        arrayAsociativo.put(15, 'S');
        arrayAsociativo.put(16, 'Q');
        arrayAsociativo.put(17, 'V');
        arrayAsociativo.put(18, 'H');
        arrayAsociativo.put(19, 'L');
        arrayAsociativo.put(20, 'C');
        arrayAsociativo.put(21, 'K');
        arrayAsociativo.put(22, 'E');

        // Acceder a elementos por número
        char resultado = arrayAsociativo.get(resutadoFormula);

        if(letra.getText().toString().equals(resultado+"")){
            lbResultado.setText("DNI Correcto");
        }else{
            lbResultado.setText("La letra correcta es: "+resultado);
        }
    }
}
