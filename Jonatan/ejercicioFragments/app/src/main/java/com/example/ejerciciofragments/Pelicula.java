package com.example.ejerciciofragments;

public class Pelicula {
    private String titulo;
    private String director;
    private String sinopsis;

    public Pelicula(String titulo, String director, String sinopsis){
        this.director=director;
        this.sinopsis=sinopsis;
        this.titulo=titulo;
    }

    public String getDirector() {
        return director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }
}
