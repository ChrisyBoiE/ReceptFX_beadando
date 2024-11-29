package com.recept.recept;

public class CombinedData {
    private int etelId;
    private String etelNev;
    private String kategoriaNev;
    private String hozzavaloNev;

    public CombinedData(int etelId, String etelNev, String kategoriaNev, String hozzavaloNev) {
        this.etelId = etelId;
        this.etelNev = etelNev;
        this.kategoriaNev = kategoriaNev;
        this.hozzavaloNev = hozzavaloNev;
    }

    public int getEtelId() {
        return etelId;
    }

    public void setEtelId(int etelId) {
        this.etelId = etelId;
    }

    public String getEtelNev() {
        return etelNev;
    }

    public void setEtelNev(String etelNev) {
        this.etelNev = etelNev;
    }

    public String getKategoriaNev() {
        return kategoriaNev;
    }

    public void setKategoriaNev(String kategoriaNev) {
        this.kategoriaNev = kategoriaNev;
    }

    public String getHozzavaloNev() {
        return hozzavaloNev;
    }

    public void setHozzavaloNev(String hozzavaloNev) {
        this.hozzavaloNev = hozzavaloNev;
    }
}
