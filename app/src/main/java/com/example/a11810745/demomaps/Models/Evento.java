package com.example.a11810745.demomaps.Models;


public class Evento {
    public String idEven;
    public String nomeEnve;
    public String dataEven;
    public String avalEven;
    public String horaEven;
    public int idAnfi;
    public double latEven;
    public double lngEven;
    public int idCate;

    public Evento() {
    }

    public Evento(String idEven, String nomeEnve, String dataEven, String avalEven, String horaEven, int idAnfi, double latEven, double lngEven, int idCate) {
        this.idEven = idEven;
        this.nomeEnve = nomeEnve;
        this.dataEven = dataEven;
        this.avalEven = avalEven;
        this.horaEven = horaEven;
        this.idAnfi = idAnfi;
        this.latEven = latEven;
        this.lngEven = lngEven;
        this.idCate = idCate;
    }
}



