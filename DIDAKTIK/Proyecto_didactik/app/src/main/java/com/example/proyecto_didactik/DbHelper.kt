package com.example.proyecto_didactik

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

// Clase auxiliar para la creación y gestión de la base de datos SQLite
class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Método llamado al crear la base de datos por primera vez
    override fun onCreate(db: SQLiteDatabase) {
        // Sentencia SQL para crear la tabla de usuarios
        val SQL_CREATE_ENTRIES = """
            CREATE TABLE ${DbContract.UserEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${DbContract.UserEntry.COLUMN_NOMBRE} TEXT,
                ${DbContract.UserEntry.COLUMN_PUNTOS} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD1} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD2} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD3} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD4} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD5} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD6} INTEGER,
                ${DbContract.UserEntry.COLUMN_ACTIVIDAD7} INTEGER
            )
        """
        // Ejecuta la sentencia SQL para crear la tabla de usuarios
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    // Método llamado cuando la versión de la base de datos es actualizada
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //No se utiliza :)
    }

    companion object {
        // Constantes para el nombre y la versión de la base de datos
        const val DATABASE_NAME = "usuarios.db"
        const val DATABASE_VERSION = 2 // Se cambió a 2 para reflejar la nueva versión
    }
}