package com.example.a11810745.demomaps.Models;

public class Atracao {
    public int idAtra;
    public int idEven;
    public String nomAtra;
    public String horAtra;
    public int idCate;
    public int avaAtra;

    public Atracao() {
    }

    public Atracao(int idAtra, int idEven, String nomAtra, String horAtra, int idCate, int avaAtra) {
        this.idAtra = idAtra;
        this.idEven = idEven;
        this.nomAtra = nomAtra;
        this.horAtra = horAtra;
        this.idCate = idCate;
        this.avaAtra = avaAtra;
    }
}
