package com.example.quimicado;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private SQLiteHelper sqLiteHelper;

    private TextView tvConsultasTotales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConsultasTotales = findViewById(R.id.tvConsultasTotales);

        // crear la base de datos
        sqLiteHelper = new SQLiteHelper(this, "Elementos", null, 1);
        crearTablas();
    }

    private void crearTablas()
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        // Comprobar si la base de datos está vacía
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Elemento", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            Log.e ("DB", "BASE DE DATOS VACIA");
            db.delete("Elemento", null, null);

            // Insertar Elementos
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("ID", 1);
            nuevoRegistro.put("NOMBRE", "HELIO");
            nuevoRegistro.put("SIMBOLO", "He");
            nuevoRegistro.put("NUMATOMICO", 2);
            nuevoRegistro.put("ESTADO", "GAS");
            db.insert("Elemento", null, nuevoRegistro);

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("ID", 2);
            nuevoRegistro.put("NOMBRE", "HIERRO");
            nuevoRegistro.put("SIMBOLO", "Fe");
            nuevoRegistro.put("NUMATOMICO", 26);
            nuevoRegistro.put("ESTADO", "SOLIDO");
            db.insert("Elemento", null, nuevoRegistro);

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("ID", 3);
            nuevoRegistro.put("NOMBRE", "MERCURIO");
            nuevoRegistro.put("SIMBOLO", "Hg");
            nuevoRegistro.put("NUMATOMICO", 80);
            nuevoRegistro.put("ESTADO", "LIQUIDO");
            db.insert("Elemento", null, nuevoRegistro);
        }else{
            Log.i ("DB", "BASE DE DATOS COMPLETA");
        }

        //Cerramos la base de datos
        db.close();
    }

    // Al pulsar en la Actividad 1
    public void accionActividadConsulta(View v) {
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivityForResult(i, 1234);
    }

    protected void onActivityResult (int requestCode, int resultCode,
                                     Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            int res = data.getExtras().getInt("contador");
            if(res==0){
                tvConsultasTotales.setText("Consultas Totales :  0");
            }else{
                tvConsultasTotales.setText("Consultas Totales :  "+res);
            }
        }
    }

    // Al pulsar en la Actividad 2
    public void accionActividadLogin(View v) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    // Al pulsar en el botón salir
    public void salir(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("Hasta pronto")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .show();
    }


}