package com.example.proyecto_didactik

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

// Clase que realiza operaciones en la base de datos SQLite
class DbOperations(context: Context) {

    // Instancia de la clase DbHelper para gestionar la base de datos
    private val dbHelper = DbHelper(context)

    // Método para insertar un nuevo usuario en la base de datos
    fun insertUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DbContract.UserEntry.COLUMN_NOMBRE, user.nombre)
            put(DbContract.UserEntry.COLUMN_PUNTOS, user.puntos)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD1, user.actividad1completada)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD2, user.actividad2completada)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD3, user.actividad3completada)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD4, user.actividad4completada)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD5, user.actividad5completada)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD6, user.actividad6completada)
            put(DbContract.UserEntry.COLUMN_ACTIVIDAD7, user.actividad7completada)
        }

        // Inserta el nuevo usuario y retorna el ID generado
        return db.insert(DbContract.UserEntry.TABLE_NAME, null, values)
    }

    // Método para obtener la lista de todos los usuarios desde la base de datos
    fun readAllUsers(): List<User> {
        val db = dbHelper.readableDatabase
        val projection = null // null para seleccionar todas las columnas
        val sortOrder = "${BaseColumns._ID} ASC"

        // Consulta a la base de datos para obtener todos los usuarios
        val cursor: Cursor = db.query(
            DbContract.UserEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        // Lista que almacenará los usuarios
        val userList = mutableListOf<User>()

        // Iterar a través del cursor y construir la lista de usuarios
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val nombre = getString(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_NOMBRE))
                val puntos = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_PUNTOS))
                val actividad1 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD1)) == 1
                val actividad2 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD2)) == 1
                val actividad3 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD3)) == 1
                val actividad4 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD4)) == 1
                val actividad5 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD5)) == 1
                val actividad6 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD6)) == 1
                val actividad7 = getInt(getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD7)) == 1

                val user = User(id, nombre, puntos, actividad1, actividad2, actividad3, actividad4, actividad5, actividad6, actividad7)
                userList.add(user)
            }
        }

        // Cerrar el cursor
        cursor.close()

        return userList
    }

    // Método para eliminar todos los usuarios de la base de datos
    fun deleteAllUsers() {
        val db = dbHelper.writableDatabase
        db.delete(DbContract.UserEntry.TABLE_NAME, null, null)
    }

    // Método para cerrar la base de datos
    fun closeDatabase() {
        dbHelper.close()
    }

    // Método para actualizar los puntos de un usuario
    fun updateUserPoints(userId: Long, newPoints: Int) {
        val dbHelper = dbHelper
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put(DbContract.UserEntry.COLUMN_PUNTOS, newPoints)

        // Actualizar el registro del usuario en la base de datos
        db.update(DbContract.UserEntry.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(userId.toString()))

        // Cerrar la base de datos
        db.close()
    }

    // Método para marcar como completada una actividad específica para un usuario
    fun updateActividad(userId: Long, actividadNumber: Int) {
        // Verificar si el número de actividad es válido
        if (actividadNumber < 1 || actividadNumber > 7) {
            throw IllegalArgumentException("Número de actividad no válido: $actividadNumber")
        }

        val dbHelper = dbHelper
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put("actividad${actividadNumber}completada", 1)

        // Actualizar el registro del usuario en la base de datos
        db.update(DbContract.UserEntry.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(userId.toString()))

        // Cerrar la base de datos
        db.close()
    }

    // Método para obtener un usuario por su ID
    fun getUserById(idUsuarioActual: Long): User? {
        val db = dbHelper.readableDatabase

        val projection = null // null para seleccionar todas las columnas
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(idUsuarioActual.toString())
        val sortOrder = null

        // Consulta a la base de datos para obtener el usuario por su ID
        val cursor = db.query(
            DbContract.UserEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        // Usar el cursor dentro de una expresión lambda para asegurar que se cierre correctamente
        return cursor.use { cursor ->
            // Verificar si el cursor tiene algún resultado
            if (cursor.moveToFirst()) {
                // Obtener los datos del usuario desde el cursor
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_NOMBRE))
                val puntos = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_PUNTOS))
                val actividad1 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD1)) == 1
                val actividad2 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD2)) == 1
                val actividad3 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD3)) == 1
                val actividad4 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD4)) == 1
                val actividad5 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD5)) == 1
                val actividad6 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD6)) == 1
                val actividad7 = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.UserEntry.COLUMN_ACTIVIDAD7)) == 1

                // Construir y retornar un objeto User
                User(idUsuarioActual, nombre, puntos, actividad1, actividad2, actividad3, actividad4, actividad5, actividad6, actividad7)
            } else {
                // Si no hay resultado, retornar null
                null
            }
        }
    }
}