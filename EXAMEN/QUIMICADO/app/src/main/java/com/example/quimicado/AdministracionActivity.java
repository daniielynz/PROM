package com.example.quimicado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AdministracionActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextSimbolo;
    private EditText editTextNumeroAtomico;
    private EditText editTextEstado;

    private Button btnInsertar;
    private Button btnModificar;
    private Button btnBorrar;
    private Button btnVolver;

    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracionactivity);

        sqLiteHelper = new SQLiteHelper(this, "Elementos", null, 1);

        editTextId = findViewById(R.id.editTextId);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextSimbolo = findViewById(R.id.editTextSimbolo);
        editTextNumeroAtomico = findViewById(R.id.editTextNumeroAtomico);
        editTextEstado = findViewById(R.id.editTextEstado);

        btnInsertar = findViewById(R.id.btnInsertar);
        btnModificar = findViewById(R.id.btnModificar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnVolver = findViewById(R.id.btnVolver);

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

    public void borrarElemento(Elemento elemento) {
        Log.e ("DB", elemento.getNombre());
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        // Le pasamos el id como condicion para que lo borre
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(elemento.getidElemento())};
        db.delete("Elemento", whereClause, whereArgs);
        db.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminado")
                .setMessage("El Elemento introducido ha sido eliminado")
                .setPositiveButton("Aceptar", null)
                .show();
    }

    public void modificarElemento(Elemento elemento) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NOMBRE", elemento.getNombre());
        values.put("SIMBOLO", elemento.getSimbolo());
        values.put("NUMATOMICO", elemento.getNumAtomico());
        values.put("ESTADO", elemento.getEstado());

        // Le pasamos el id como condicion para que lo modifique
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(elemento.getidElemento())};
        db.update("Elemento", values, whereClause, whereArgs);
        db.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modificado")
                .setMessage("El Elemento ha sido modificado")
                .setPositiveButton("Aceptar", null)
                .show();
    }


    public void insertarElemento(Elemento elemento) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        // añadimos los valores para insertar
        ContentValues values = new ContentValues();
        values.put("ID", elemento.getidElemento());
        values.put("NOMBRE", elemento.getNombre());
        values.put("SIMBOLO", elemento.getSimbolo());
        values.put("NUMATOMICO", elemento.getNumAtomico());
        values.put("ESTADO", elemento.getEstado());

        db.insert("Elemento", null, values);
        db.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insertado")
                .setMessage("El Elemento introducido ha sido insertado")
                .setPositiveButton("Aceptar", null)
                .show();

    }

    public void listenerInsertar(View view){
        String nombre = editTextNombre.getText().toString();
        Elemento elemento = buscarElementoPorNombre(nombre);
        if(elemento!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("El Elemento introducido ya existe")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }else{
            int id = Integer.parseInt(editTextId.getText().toString());
            String simbolo = editTextSimbolo.getText().toString();
            int numAto = Integer.parseInt(editTextNumeroAtomico.getText().toString());
            String estado = editTextEstado.getText().toString();

            Elemento e = new Elemento(id, nombre, simbolo, numAto, estado);

            insertarElemento(e);
        }
    }

    public void listenerModificar(View view){
        String nombre = editTextNombre.getText().toString();
        Elemento elemento = buscarElementoPorNombre(nombre);
        if(elemento!=null){
            int id = Integer.parseInt(editTextId.getText().toString());
            String simbolo = editTextSimbolo.getText().toString();
            int numAto = Integer.parseInt(editTextNumeroAtomico.getText().toString());
            String estado = editTextEstado.getText().toString();

            elemento.setidElemento(id);
            elemento.setSimbolo(simbolo);
            elemento.setNumAtomico(numAto);
            elemento.setEstado(estado);

            modificarElemento(elemento);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("El Elemento introducido no existe")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    public void listenerBorrar(View view){
        String nombre = editTextNombre.getText().toString();
        Elemento elemento = buscarElementoPorNombre(nombre);
        if(elemento!=null){
            borrarElemento(elemento);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("El Elemento introducido no existe")
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }

    public void listenerVolver(View view){
        Intent i = new Intent(AdministracionActivity.this, MainActivity.class);
        startActivity(i);
    }
}