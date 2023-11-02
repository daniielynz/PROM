package com.example.controlesseleccion2;

public class WebFavorita {
    private String nombre;
    private String url;
    private int imagenResource;

    public WebFavorita(String nombre, String url, int imagenResource) {
        this.nombre = nombre;
        this.url = url;
        this.imagenResource = imagenResource;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public int getImagenResource() {
        return imagenResource;
    }
}

