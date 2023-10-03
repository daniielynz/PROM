package com.example.calculadoracontrolesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void botonSumarPulsado (View v){
        EditText texto1 = findViewById(R.id.primerNum);
        EditText texto2 = findViewById(R.id.segundoNum);
        TextView tvResultado = findViewById(R.id.lblResultado);

        Double num1 = Double.parseDouble(texto1.getText().toString());
        Double num2 = Double.parseDouble(texto2.getText().toString());

        double resultado = num1 + num2;

        tvResultado.setText("Resultado: "+resultado);
    }
   public void botonRestarPulsado (View v){
       EditText texto1 = findViewById(R.id.primerNum);
       EditText texto2 = findViewById(R.id.segundoNum);
       TextView tvResultado = findViewById(R.id.lblResultado);

        Double num1 = Double.parseDouble(texto1.getText().toString());
        Double num2 = Double.parseDouble(texto2.getText().toString());

        double resultado = num1 - num2;

        tvResultado.setText("Resultado: "+resultado);
    }
    public void botonMultiplicarPulsado (View v){
        EditText texto1 = findViewById(R.id.primerNum);
        EditText texto2 = findViewById(R.id.segundoNum);
        TextView tvResultado = findViewById(R.id.lblResultado);

        Double num1 = Double.parseDouble(texto1.getText().toString());
        Double num2 = Double.parseDouble(texto2.getText().toString());

        double resultado = num1 * num2;

        tvResultado.setText("Resultado: "+resultado);
    }
    public void botonDividirPulsado (View v){
        EditText texto1 = findViewById(R.id.primerNum);
        EditText texto2 = findViewById(R.id.segundoNum);
        TextView tvResultado = findViewById(R.id.lblResultado);

        Double num1 = Double.parseDouble(texto1.getText().toString());
        Double num2 = Double.parseDouble(texto2.getText().toString());

        double resultado = num1 / num2;

        tvResultado.setText("Resultado: "+resultado);
    }
}