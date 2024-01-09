package com.example.ficheros_ejercicio3;

public class Web {
    private String nombre;
    private String enlace;
    private String logo;
    private int identificador;

    public Web(String nombre, String enlace, String logo, int identificador) {
        this.nombre = nombre;
        this.enlace = enlace;
        this.logo = logo;
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public String getLogo() {
        return logo;
    }

    public int getIdentificador() {
        return identificador;
    }
}

