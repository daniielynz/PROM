package com.example.proyecto_didactik

//Variables para la clase User (a utilizar en base de datos)
data class User(
    val id: Long,
    val nombre: String,
    val puntos: Int,
    val actividad1completada: Boolean,
    val actividad2completada: Boolean,
    val actividad3completada: Boolean,
    val actividad4completada: Boolean,
    val actividad5completada: Boolean,
    val actividad6completada: Boolean,
    val actividad7completada: Boolean
)
