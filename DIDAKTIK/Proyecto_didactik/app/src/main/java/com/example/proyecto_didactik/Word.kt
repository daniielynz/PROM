package com.example.proyecto_didactik

class Word(word: String) {
    var isFound = false
    val wordVal = word
    var _start = 0
    var _end = 0

    // Función para verificar la ubicación de la palabra
    fun setLoc(tag: Int, isHorizontal: Boolean) {
        _start = tag
        _end = if (isHorizontal) tag + wordVal.length - 1 else tag + (wordVal.length - 1) * 10
    }
    // Función para verificar si las posiciones de inicio y fin proporcionadas coinciden con la ubicación de la palabra
    fun checkLoc(start: Int, end: Int, isHorizontal: Boolean): Boolean {

        if (isHorizontal) {
            // horizontal case: check if word length and selected equal
            if (end - start != wordVal.length - 1) {
                return false
            }
        } else {
            // vertical case: check if word length and selected equal
            if ((end - start) / 10 != wordVal.length - 1) {
                return false
            }
        }
        if (_start == start && _end == end) {
            return true
        }
        return false
    }
}