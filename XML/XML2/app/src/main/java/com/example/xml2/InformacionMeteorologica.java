package com.example.xml2;

public class InformacionMeteorologica {
    private String temperatura;
    private String estadoCielo;
    private String humedadRelativa;
    private String direccionViento;
    private String velocidadViento;
    private String sensacionTermica;

    public InformacionMeteorologica(String temperatura, String estadoCielo, String humedadRelativa, String direccionViento, String velocidadViento, String sensacionTermica) {
        this.temperatura = temperatura;
        this.estadoCielo = estadoCielo;
        this.humedadRelativa = humedadRelativa;
        this.direccionViento = direccionViento;
        this.velocidadViento = velocidadViento;
        this.sensacionTermica = sensacionTermica;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getEstadoCielo() {
        return estadoCielo;
    }

    public String getHumedadRelativa() {
        return humedadRelativa;
    }

    public String getDireccionViento() {
        return direccionViento;
    }

    public String getVelocidadViento() {
        return velocidadViento;
    }

    public String getSensacionTermica() {
        return sensacionTermica;
    }
}

