package com.example.controlesbasicos2_ejercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    public void accionVerificar (View v){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

        EditText nombre = findViewById(R.id.etNombre);
        EditText apellidos = findViewById(R.id.etApellidos);

        intent.putExtra("nombre" , nombre.getText().toString());
        intent.putExtra("apellidos" , apellidos.getText().toString());

        startActivityForResult(intent, 1234);
    }

    protected void onActivityResult (int requestCode, int resultCode,
                                     Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String res = data.getExtras().getString("resultado");

            TextView t = findViewById(R.id.tvAceptasCondiciones);
            t.setText("Aceptar condiciones "+res);
        }
    }
}