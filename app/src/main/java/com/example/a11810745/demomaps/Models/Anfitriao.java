package com.example.a11810745.demomaps.Models;

public class Anfitriao {
    public static Anfitriao anfitriaoLogado;

    public int idAnfi;
    public String nomAnfi;
    public String mailAnfi;
    public String cpfAnfi;
    public String senAnfi;
    public double latAnfi;
    public double lngAnfi;
    public int idCate;

    public Anfitriao() {
    }

    public Anfitriao(int idAnfi, String nomAnfi, String mailAnfi, String cpfAnfi, String senAnfi, double latAnfi, double lngAnfi, int idCate) {
        this.idAnfi = idAnfi;
        this.nomAnfi = nomAnfi;
        this.mailAnfi = mailAnfi;
        this.cpfAnfi = cpfAnfi;
        this.senAnfi = senAnfi;
        this.latAnfi = latAnfi;
        this.lngAnfi = lngAnfi;
        this.idCate = idCate;
    }
}
