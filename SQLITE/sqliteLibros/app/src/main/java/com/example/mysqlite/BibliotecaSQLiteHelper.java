package com.example.mysqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BibliotecaSQLiteHelper extends SQLiteOpenHelper
{
    private final String sqlCreateLibro =  "CREATE TABLE Libro (" +
                                "idLibro INTEGER NOT NULL, " +
                                "nomLibro TEXT, " +
                                "nomEditorial TEXT, " +
                                "PRIMARY KEY (idLibro))";

    public BibliotecaSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(sqlCreateLibro);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Libro");

        //Se crea la nueva version de la tabla
        onCreate(db);
    }
}