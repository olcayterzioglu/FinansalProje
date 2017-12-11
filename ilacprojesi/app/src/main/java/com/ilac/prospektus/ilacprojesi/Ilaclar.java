package com.ilac.prospektus.ilacprojesi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Lenovo on 7.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class Ilaclar {
    private String ad;
    private String firma_adi;
    private String barkod_no;
    private String fiyat;
    private String etken_madde;
    private String formul;
    private String farmokolojik_ozellik;
    private String endikasyonlar;
    private String kontrendikasyonlar;
    private String uyarilar;
    private String yan_etkiler;
    private String etkilesimler;
    private String kullanim_sekli;
    private String doz_asimi;

    public Ilaclar(String ad, String firma_adi, String  barkod_no, String fiyat, String etken_madde, String formul , String farmokolojik_ozellik, String endikasyonlar, String kontrendikasyonlar,String uyarilar, String yan_etkiler, String etkilesimler, String kullanim_sekli, String doz_asimi ){

        this.ad = ad;
        this.firma_adi = firma_adi;
        this.barkod_no = barkod_no;
        this.fiyat = fiyat;
        this.etken_madde = etken_madde;
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

    public Ilaclar(ilacDetay ilacDetay) {

    }

    public Ilaclar() {

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

    public String getFirma_adi() {
        return firma_adi;
    }

    public void setFirma_adi(String firma_adi) {
        this.firma_adi = firma_adi;
    }

    public String getBarkod_no() {
        return barkod_no;
    }

    public void setBarkod_no(String barkod_no) {
        this.barkod_no = barkod_no;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }

    public String getEtken_madde() {
        return etken_madde;
    }

    public void setEtken_madde(String etken_madde) {
        this.etken_madde = etken_madde;
    }
}

