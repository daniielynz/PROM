package com.example.ficheros_ejercicio3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listViewWebs);

        // Cargar los datos desde el archivo de recurso
        List<Web> webItemList = cargarDatosDesdeRecurso();

        // Crear un ArrayAdapter para el ListView
        WebAdapter adapter = new WebAdapter(this, webItemList);

        // Asignar el adaptador al ListView
        listView.setAdapter(adapter);
    }

    private List<Web> cargarDatosDesdeRecurso() {
        List<Web> webItemList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.webs);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                // Dividir la línea utilizando el delimitador ";"
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                if (tokenizer.countTokens() == 4) {
                    // Crear un objeto WebItem y añadirlo a la lista
                    String nombre = tokenizer.nextToken().trim();
                    String enlace = tokenizer.nextToken().trim();
                    String logo = tokenizer.nextToken().trim();
                    int identificador = Integer.parseInt(tokenizer.nextToken().trim());

                    Web web = new Web(nombre, enlace, logo, identificador);
                    webItemList.add(web);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return webItemList;
    }
}
