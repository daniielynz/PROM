package com.example.controlesbasicos2_ejercicio4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    String resultado;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        intent = new Intent();
        Bundle extras = getIntent().getExtras();

        String respuesta = extras.getString("numeroElegido") ;
        String solucion = extras.getString("solucion") ;

        TextView tvResultado = findViewById(R.id.tvResultado);
        if(respuesta.equals(solucion)){
            resultado=1+"";
            tvResultado.setText("Acertaste");
        }else{
            resultado=0+"";
            intent.putExtra("ocultar" , respuesta);
            tvResultado.setText("Vuelve a Intentarlo");
        }
    }

    public void accionVolver (View v){
        intent.putExtra("resultado" , resultado);
        setResult(RESULT_OK, intent);
        finish();
    }
}