package com.example.proyecto_didactik

import android.provider.BaseColumns

object DbContract {
    // Definir el esquema de la tabla
    class UserEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "usuarios"
            const val COLUMN_NOMBRE = "nombre"
            const val COLUMN_PUNTOS = "puntos"
            const val COLUMN_ACTIVIDAD1 = "actividad1completada"
            const val COLUMN_ACTIVIDAD2 = "actividad2completada"
            const val COLUMN_ACTIVIDAD3 = "actividad3completada"
            const val COLUMN_ACTIVIDAD4 = "actividad4completada"
            const val COLUMN_ACTIVIDAD5 = "actividad5completada"
            const val COLUMN_ACTIVIDAD6 = "actividad6completada"
            const val COLUMN_ACTIVIDAD7 = "actividad7completada"
        }
    }
}
