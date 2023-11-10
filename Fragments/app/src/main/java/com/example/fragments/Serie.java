package com.example.fragments;

public class Serie {
    private String nombre, descripcion, director;
    private int anioEstreno;
    public Serie(String nombre, String descripcion, String director, int anioEstreno){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.director = director;
        this.anioEstreno = anioEstreno;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion (){
        return descripcion;
    }
    public String getDirector(){
        return director;
    }
    public Integer getanioEstreno(){
        return anioEstreno;
    }
}