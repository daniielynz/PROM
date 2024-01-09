package com.example.ficheros_ejercicio1;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button btnWriteInternal = findViewById(R.id.btnWriteInternal);
        Button btnReadInternal = findViewById(R.id.btnReadInternal);
        Button btnDeleteInternal = findViewById(R.id.btnDeleteInternal);

        Button btnWriteExternal = findViewById(R.id.btnWriteExternal);
        Button btnReadExternal = findViewById(R.id.btnReadExternal);
        Button btnDeleteExternal = findViewById(R.id.btnDeleteExternal);

        // Configurar listeners para botones internos
        btnWriteInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                try {
                    FileOutputStream fos = openFileOutput("internal_file.txt", Context.MODE_PRIVATE);
                    fos.write(content.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnReadInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder content = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File(getFilesDir(), "internal_file.txt")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textView.setText(content.toString());
            }
        });

        btnDeleteInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getFilesDir(), "internal_file.txt");
                if (file.exists()) {
                    file.delete();
                }
            }
        });

        // Configurar listeners para botones externos
        btnWriteExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    OutputStreamWriter osw=
                            new OutputStreamWriter(openFileOutput("prueba_int.txt",
                                    Context.MODE_PRIVATE));
                    osw.write(editText.getText().toString());
                    Log.i ("Ficheros", "Escribiendo en fichero de memoria interna");
                    osw.close();
                }
                catch (Exception e) {
                    Log.e ("Ficheros",
                            "ERROR!! al escribir fichero a memoria interna");
                }
            }
        });

        btnReadExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    BufferedReader fin =
                            new BufferedReader(
                                    new InputStreamReader(
                                            openFileInput("prueba_int.txt")));
                    String texto="";
                    String linea= fin.readLine();
                    while (linea!=null){
                        texto=texto+linea+"\n";
                        Log.i("Ficheros", linea);
                        linea=fin.readLine();
                    }
                    fin.close();
                    textView.setText(texto);
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                }
            }
        });

        btnDeleteExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica si el almacenamiento externo está disponible para borrar
                if (isExternalStorageWritable()) {
                    File externalFile = new File(Environment.getExternalStorageDirectory(), "external_file.txt");
                    if (externalFile.exists()) {
                        externalFile.delete();
                    }
                }
            }
        });
    }

    // Método auxiliar para verificar si el almacenamiento externo es escribible
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Método auxiliar para verificar si el almacenamiento externo es legible
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}

