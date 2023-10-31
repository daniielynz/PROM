package com.example.controlesseleccion1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner spOpciones;
    private TextView tvOpcionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spOpciones = (Spinner) findViewById(R.id.spinner);
        tvOpcionSeleccionada = (TextView) findViewById(R.id.tvOpcionSeleccionada);

        //Creamos el Array
        final String[] paises = new String [] {"-Elige un pais-","Francia" ,"Inglaterra",
                "Portugal", "Italia", "Alemania"} ;


        //Creamos el adaptador para el spinner
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paises);
        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spOpciones.setAdapter(adaptador);

        //Eventos para el spinner
        spOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Creamos el Array
                final String[] datos = new String [] {"","67,75 millones" ,"56 millones",
                        "10,33 millones", "59,11 millones", "83,2 millones"} ;

                if(parent.getItemIdAtPosition(position)>0){
                    int idDatos = Integer.parseInt(String.valueOf(parent.getItemIdAtPosition(position)));
                    tvOpcionSeleccionada.setText (parent.getItemAtPosition(position)+" tiene "+datos[idDatos]+" habitantes");
                }else{
                    tvOpcionSeleccionada.setText("");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                tvOpcionSeleccionada.setText("");
            }
        });
    }
}