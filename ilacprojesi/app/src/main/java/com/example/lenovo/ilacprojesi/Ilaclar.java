package com.example.lenovo.ilacprojesi;

/**
 * Created by Lenovo on 7.12.2017.
 */

public class Ilaclar {
    private String ad;
    private String formul;
    private String farmokolojik_ozellik;
    private String endikasyonlar;
    private String kontrendikasyonlar;
    private String uyarilar;
    private String yan_etkiler;
    private String etkilesimler;
    private String kullanim_sekli;
    private String doz_asimi;

    public Ilaclar(String ad, String formul , String farmokolojik_ozellik, String endikasyonlar, String kontrendikasyonlar,String uyarilar, String yan_etkiler, String etkilesimler, String kullanim_sekli, String doz_asimi ){

        this.ad = ad;
        this.formul = formul;
        this.farmokolojik_ozellik = farmokolojik_ozellik;
        this.endikasyonlar = endikasyonlar;
        this.kontrendikasyonlar = kontrendikasyonlar;
        this.uyarilar = uyarilar;
        this.yan_etkiler = yan_etkiler;
        this.etkilesimler = etkilesimler;
        this.kullanim_sekli = kullanim_sekli;
        this.doz_asimi = doz_asimi;

    }

    public String getFormul() {
        return formul;
    }

    public void setFormul(String formul) {
        this.formul = formul;
    }

    public String getFarmokolojik_ozellik() {
        return farmokolojik_ozellik;
    }

    public void setFarmokolojik_ozellik(String farmokolojik_ozellik) {
        this.farmokolojik_ozellik = farmokolojik_ozellik;
    }

    public String getEndikasyonlar() {
        return endikasyonlar;
    }

    public void setEndikasyonlar(String endikasyonlar) {
        this.endikasyonlar = endikasyonlar;
    }

    public String getKontrendikasyonlar() {
        return kontrendikasyonlar;
    }

    public void setKontrendikasyonlar(String kontrendikasyonlar) {
        this.kontrendikasyonlar = kontrendikasyonlar;
    }

    public String getUyarilar() {
        return uyarilar;
    }

    public void setUyarilar(String uyarilar) {
        this.uyarilar = uyarilar;
    }

    public String getYan_etkiler() {
        return yan_etkiler;
    }

    public void setYan_etkiler(String yan_etkiler) {
        this.yan_etkiler = yan_etkiler;
    }

    public String getEtkilesimler() {
        return etkilesimler;
    }

    public void setEtkilesimler(String etkilesimler) {
        this.etkilesimler = etkilesimler;
    }

    public String getKullanim_sekli() {
        return kullanim_sekli;
    }

    public void setKullanim_sekli(String kullanim_sekli) {
        this.kullanim_sekli = kullanim_sekli;
    }

    public String getDoz_asimi() {
        return doz_asimi;
    }

    public void setDoz_asimi(String doz_asimi) {
        this.doz_asimi = doz_asimi;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}

