package com.recept.recept;

public class Etel {
    private int id;
    private String nev;
    private String felirdatum;

    public Etel(int id, String nev, String felirdatum) {
        this.id = id;
        this.nev = nev;
        this.felirdatum = felirdatum;
    }

    // Getterek és setterek
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getFelirdatum() {
        return felirdatum;
    }

    public void setFelirdatum(String felirdatum) {
        this.felirdatum = felirdatum;
    }
}
