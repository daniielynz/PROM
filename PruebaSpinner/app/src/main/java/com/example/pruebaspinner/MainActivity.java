package com.example.pruebaspinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private ListView lvLibros;
    private Spinner spinnerLibros;
    private Button butBorrar;
    private EditText etIdLibro;
    private EditText etNomLibro;
    private EditText etEditorial;
    private Button butCambiarModo;

    private BibliotecaSQLiteHelper bsdbh;
    private boolean modoInsercion;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modoInsercion = false;

        etIdLibro = (EditText)findViewById(R.id.etIdLibro);
        etIdLibro.setFocusable(false);
        etIdLibro.setFocusableInTouchMode(false);
        etIdLibro.setClickable(false);
        etIdLibro.setTextColor(Color.GRAY);

        butBorrar = (Button)findViewById(R.id.butBorrar);
        butBorrar.setClickable(false);
        butBorrar.setBackgroundColor(Color.GRAY);

        lvLibros = (ListView)findViewById(R.id.lvLibros);
        spinnerLibros = (Spinner)findViewById(R.id.spinnerLibros);
        etNomLibro = (EditText)findViewById(R.id.etNomLibro);
        etEditorial = (EditText)findViewById(R.id.etEditorial);
        butCambiarModo = (Button)findViewById(R.id.butCambiarModo);

        bsdbh = new BibliotecaSQLiteHelper(this, "Biblioteca", null, 1);
        crearTablas();



        refrescarListaLibros();

        lvLibros.setOnItemClickListener(this);
        spinnerLibros.setOnItemSelectedListener(this);
    }

    private void crearTablas()
    {
        SQLiteDatabase db = bsdbh.getWritableDatabase();

        // Comprobar si la base de datos está vacía
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Libro", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            Log.e ("DB", "BASE DE DATOS VACIA");
            // La base de datos está vacía, realizar las operaciones
            db.delete("Libro", null, null);

            ContentValues nuevoRegistro = new ContentValues();

            // Insertar libros
            nuevoRegistro.put("idLibro", 0);
            nuevoRegistro.put("nomLibro", "Cien años de soledad");
            nuevoRegistro.put("nomEditorial", "Sudamericana");
            db.insert("Libro", null, nuevoRegistro);

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("idLibro", 1);
            nuevoRegistro.put("nomLibro", "1984");
            nuevoRegistro.put("nomEditorial", "Secker & Warburg");
            db.insert("Libro", null, nuevoRegistro);

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("idLibro", 2);
            nuevoRegistro.put("nomLibro", "El señor de los anillos");
            nuevoRegistro.put("nomEditorial", "George Allen & Unwin");
            db.insert("Libro", null, nuevoRegistro);

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("idLibro", 3);
            nuevoRegistro.put("nomLibro", "Harry Potter y la piedra filosofal");
            nuevoRegistro.put("nomEditorial", "Bloomsbury");
            db.insert("Libro", null, nuevoRegistro);
        }else{
            Log.i ("DB", "BASE DE DATOS COMPLETA");
        }

        //Cerramos la base de datos
        db.close();
    }

    private void refrescarListaLibros()
    {
        // cargamos los datos de la lista
        List<Libro> listaLibros = cargarLibros();
        AdaptadorLibros adaptadorLibros = new AdaptadorLibros(this, listaLibros);
        lvLibros.setAdapter(adaptadorLibros);

        // cargamos los datos del Spinner
        ArrayAdapter<Libro> adaptadorSpinnerLibros = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cargarLibrosSpinner());
        adaptadorSpinnerLibros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLibros.setAdapter(adaptadorSpinnerLibros);
    }

    private List<Libro> cargarLibrosSpinner() {
        ArrayList<Libro>listaLibros = new ArrayList<>();

        SQLiteDatabase db = bsdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Libro ", null);

        //Nos aseguramos de que existe el primer registro
        if (c.moveToFirst())
        {
            do
            {
                int idLibro = c.getInt(0);
                String nomLibro = c.getString(1);
                String nomEditorial = c.getString(2);

                Libro libro = new Libro(idLibro, nomLibro, nomEditorial);

                listaLibros.add(libro);
            }
            while(c.moveToNext());
        }
        c.close();
        db.close();
        return listaLibros;
    }

    private ArrayList<Libro> cargarLibros()
    {
        ArrayList<Libro>listaLibros = new ArrayList<Libro>();

        SQLiteDatabase db = bsdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT idLibro, nomLibro, nomEditorial FROM Libro ", null);

        //Nos aseguramos de que existe el primer registro
        if (c.moveToFirst())
        {
            do
            {
                int idLibro = c.getInt(0);
                String nomLibro = c.getString(1);
                String nomEditorial = c.getString(2);

                Libro libro = new Libro(idLibro, nomLibro, nomEditorial);

                listaLibros.add(libro);
            }
            while(c.moveToNext());
        }
        c.close();
        db.close();
        return listaLibros;
    }

    private class AdaptadorLibros extends ArrayAdapter<Libro>
    {
        private List<Libro> datos;

        public AdaptadorLibros(Context context, List<Libro> datos)
        {
            super(context, R.layout.listitem_libro, datos);
            this.datos = datos;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_libro, null);

            TextView tvNomLibro = (TextView)item.findViewById(R.id.tvNomLibro);
            tvNomLibro.setText(datos.get(position).getNomLibro().toString());

            TextView tvEditorial = (TextView)item.findViewById(R.id.tvEditorial);
            tvEditorial.setText(datos.get(position).getNomEditorial().toString());

            TextView tvIdLibro = (TextView)item.findViewById(R.id.tvIdLibro);
            tvIdLibro.setText(String.valueOf(datos.get(position).getIdLibro()));

            return item;
        }
    }

    public void listenerButBorrar(View view)
    {
        SQLiteDatabase db = bsdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null)
        {
            try
            {
                int idLibro = Integer.parseInt(etIdLibro.getText().toString());

                //Modificar
                db.execSQL("DELETE FROM Libro WHERE idLibro = '" + idLibro + "'");
            }
            catch(Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
        db.close();

        refrescarListaLibros();

        etIdLibro.setText("");
        etNomLibro.setText("");
        etEditorial.setText("");

        butBorrar.setClickable(false);
        butBorrar.setBackgroundColor(Color.GRAY);
    }

    public void listenerButCambiarModo(View view)
    {

        if(!modoInsercion)
        {
            etIdLibro.setFocusable(true);
            etIdLibro.setFocusableInTouchMode(true);
            etIdLibro.setClickable(true);
            etIdLibro.setTextColor(Color.BLACK);

            modoInsercion = true;
            butBorrar.setClickable(false);
            butBorrar.setBackgroundColor(Color.GRAY);
            butCambiarModo.setText("Modificar");
        }
        else
        {
            etIdLibro.setFocusable(false);
            etIdLibro.setFocusableInTouchMode(false);
            etIdLibro.setClickable(false);
            etIdLibro.setTextColor(Color.GRAY);

            modoInsercion = false;
            butCambiarModo.setText("Añadir");
        }
        etIdLibro.setText("");
        etNomLibro.setText("");
        etEditorial.setText("");
    }

    public void listenerButGuardar(View view)
    {
        try
        {
            if(etIdLibro.getText().toString().equals(""))
                throw new NumberFormatException();
            int idLibro = Integer.parseInt(etIdLibro.getText().toString());

            String nomLibro = etNomLibro.getText().toString();
            if(nomLibro.equals(""))
                throw new Exception("Debe introducirse un nombre para el libro.");

            String nomEditorial = etEditorial.getText().toString();
            if(nomEditorial.equals(""))
                throw new Exception("Debe introducirse un nombre para la editorial.");

            SQLiteDatabase db = bsdbh.getWritableDatabase();

            //Si hemos abierto correctamente la base de datos
            if (db != null)
            {
                if (modoInsercion)
                {
                    //Insertar
                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("idLibro", idLibro);
                    nuevoRegistro.put("nomLibro", nomLibro);
                    nuevoRegistro.put("nomEditorial", nomEditorial);

                    db.insert("Libro", null, nuevoRegistro);
                }
                else
                {
                    //Modificar
                    db.execSQL( "UPDATE Libro SET nomLibro = '" + nomLibro + "', " +
                                "nomEditorial = '" + nomEditorial + "' " +
                                "WHERE idLibro = '" + idLibro + "'");
                }
            }
            db.close();

            refrescarListaLibros();

            etIdLibro.setText("");
            etNomLibro.setText("");
            etEditorial.setText("");
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this,"El identificador debe tratarse de un valor numérico.",Toast.LENGTH_LONG ).show();
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(!modoInsercion)
        {
            Libro libro = (Libro) parent.getItemAtPosition(position);
            int idLibro = libro.getIdLibro();
            String nomLibro = libro.toString();
            String nomEditorial = libro.getNomEditorial();

            etIdLibro.setText(String.valueOf(idLibro));
            etEditorial.setText(nomEditorial);
            etNomLibro.setText(nomLibro);

            butBorrar.setClickable(true);
            butBorrar.setBackgroundColor(Color.RED);
        }
        else
        {
            Toast.makeText(this,"Los libros no son seleccionables en el modo de inserción.",Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!modoInsercion) {
            Libro libroSeleccionado = (Libro)parent.getItemAtPosition(position);

            int idLibro = libroSeleccionado.getIdLibro();
            String nomLibro = libroSeleccionado.getNomLibro();
            String nomEditorial = libroSeleccionado.getNomEditorial();

            etIdLibro.setText(String.valueOf(idLibro));
            etEditorial.setText(nomEditorial);
            etNomLibro.setText(nomLibro);

            butBorrar.setClickable(true);
            butBorrar.setBackgroundColor(Color.RED);
        } else {
            Toast.makeText(this, "Los libros no son seleccionables en el modo de inserción.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}