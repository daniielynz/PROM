package com.example.proyecto_didactik

data class User(
    val id: Long, // Puedes utilizar un ID único para cada usuario
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
