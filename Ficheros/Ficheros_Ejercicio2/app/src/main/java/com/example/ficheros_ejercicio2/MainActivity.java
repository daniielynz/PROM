package com.example.ficheros_ejercicio2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerProvincias = findViewById(R.id.spinnerProvincias);

        List<String> provinciasList = cargarProvinciasDesdeRecurso();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinciasList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincias.setAdapter(adapter);
    }

    private List<String> cargarProvinciasDesdeRecurso() {
        List<String> provinciasList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.provincias);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                provinciasList.add(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return provinciasList;
    }
}
