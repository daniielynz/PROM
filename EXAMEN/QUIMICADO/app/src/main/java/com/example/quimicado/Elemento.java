package com.example.quimicado;

public class Elemento {
    private int idElemento;
    private String nombre;
    private String simbolo;
    private int NumAtomico;
    private String estado;

    public Elemento(int idElemento, String nombre, String simbolo, int numAtomico, String estado)
    {
        this.idElemento = idElemento;
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.NumAtomico = numAtomico;
        this.estado = estado;
    }

    public int getidElemento()
    {
        return idElemento;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getSimbolo()
    {
        return simbolo;
    }
    public int getNumAtomico()
    {
        return NumAtomico;
    }
    public String getEstado()
    {
        return estado;
    }

    public void setidElemento(int idElemento)
    {
        this.idElemento = idElemento;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setSimbolo(String simbolo)
    {
        this.simbolo = simbolo;
    }

    public void setNumAtomico(int numAtomico)
    {
        this.NumAtomico = numAtomico;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return idElemento + " - " + nombre;
    }
}
