package com.example.mysqlite;

public class Libro
{
    private int idLibro;
    private String nomLibro;
    private String nomEditorial;

    public Libro(int idLibro, String nomLibro, String nomEditorial)
    {
        this.idLibro = idLibro;
        this.nomLibro = nomLibro;
        this.nomEditorial = nomEditorial;
    }

    public int getIdLibro()
    {
        return idLibro;
    }

    public String getNomEditorial()
    {
        return nomEditorial;
    }

    public String toString()
    {
        return nomLibro;
    }
}