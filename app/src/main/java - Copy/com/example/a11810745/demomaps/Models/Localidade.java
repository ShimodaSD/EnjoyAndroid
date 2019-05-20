package com.example.a11810745.demomaps.Models;

public class Localidade {
    public int idLoca;
    public double latLoca;
    public double lngLoca;
    public String nomLoca;

    public Localidade() {
    }

    public Localidade(int idLoca, double latLoca, double lngLoca, String nomLoca) {
        this.idLoca = idLoca;
        this.latLoca = latLoca;
        this.lngLoca = lngLoca;
        this.nomLoca = nomLoca;
    }
}
