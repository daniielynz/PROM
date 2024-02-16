package com.example.proyecto_didactik

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
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
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implementa la lógica de actualización de la base de datos aquí si es necesario
    }

    companion object {
        const val DATABASE_NAME = "usuarios.db"
        const val DATABASE_VERSION = 2 // Cambiado a 2 para reflejar la nueva versión
    }
}