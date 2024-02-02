package com.example.quimicado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    private static int contador = 0;
    private EditText editTextNombre;
    private EditText editTextId;
    private EditText editTextSimbolo;
    private EditText editTextNumAtomico;
    private EditText editTextEstado;
    private SQLiteHelper sqLiteHelper;
    private Button btnBuscarLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_consulta);

        sqLiteHelper = new SQLiteHelper(this, "Elementos", null, 1);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextId = findViewById(R.id.editTextId);
        editTextSimbolo = findViewById(R.id.editTextSimbolo);
        editTextNumAtomico = findViewById(R.id.editTextNumeroAtomico);
        editTextEstado = findViewById(R.id.editTextEstado);

        btnBuscarLimpiar = findViewById(R.id.btnBuscarLimpiar);
    }

    public Elemento buscarElementoPorNombre(String nombre) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        String query = "SELECT * FROM Elemento WHERE NOMBRE = ?";
        Cursor c = db.rawQuery(query, new String[]{nombre});
        Elemento elemento = null;

        if (c.moveToFirst()) {
            do
            {
                int id = c.getInt(0);
                String nombreElemento = c.getString(1);
                String simbolo = c.getString(2);
                int numAtomico = c.getInt(3);
                String estado = c.getString(4);

                elemento = new Elemento(id, nombreElemento, simbolo, numAtomico, estado);

                return elemento;
            }
            while(c.moveToNext());
        }
        c.close();
        db.close();

        return elemento;
    }

    public void listenerBuscar(View view){
        String nombre = editTextNombre.getText().toString();
        Elemento elemento = buscarElementoPorNombre(nombre);
        if(elemento!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("El Elemento introducido EXISTE")
                    .setPositiveButton("Aceptar", null)
                    .show();

            btnBuscarLimpiar.setText("Limpiar");

            editTextNombre.setText(elemento.getNombre().toString());
            editTextId.setText("ID : "+elemento.getidElemento()+"");
            editTextSimbolo.setText("Simbolo : "+elemento.getSimbolo().toString());
            editTextNumAtomico.setText("Numero Atomico : "+elemento.getNumAtomico()+"");
            editTextEstado.setText("Estado : "+elemento.getEstado().toString());

            contador++;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("El Elemento introducido no existe")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    public void listenerVolver(View view){
        Intent intent = new Intent();

        intent.putExtra("contador" , contador);
        setResult(RESULT_OK, intent);
        finish();
    }
}