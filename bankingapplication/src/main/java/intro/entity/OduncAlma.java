package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "odunc_alma")
public class OduncAlma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oduncId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ogrenci_id", nullable = false)
    private Ogrenci ogrenci;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veren_gorevli_id", nullable = false)
    private Gorevli verenGorevli;

    @Column(nullable = false)
    private LocalDate verilişTarihi;

    @Column(nullable = false)
    private LocalDate planlananiAdeTarihi;

    @Column(nullable = false)
    private String durum;

    public OduncAlma() {
    }

    public OduncAlma(Kitap kitap, Ogrenci ogrenci, Gorevli verenGorevli, 
                     LocalDate verilişTarihi, LocalDate planlananiAdeTarihi, String durum) {
        this.kitap = kitap;
        this.ogrenci = ogrenci;
        this.verenGorevli = verenGorevli;
        this.verilişTarihi = verilişTarihi;
        this.planlananiAdeTarihi = planlananiAdeTarihi;
        this.durum = durum;
    }

    public Integer getOduncId() {
        return oduncId;
    }

    public void setOduncId(Integer oduncId) {
        this.oduncId = oduncId;
    }

    public Kitap getKitap() {
        return kitap;
    }

    public void setKitap(Kitap kitap) {
        this.kitap = kitap;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public Gorevli getVerenGorevli() {
        return verenGorevli;
    }

    public void setVerenGorevli(Gorevli verenGorevli) {
        this.verenGorevli = verenGorevli;
    }

    public LocalDate getVerilişTarihi() {
        return verilişTarihi;
    }

    public void setVerilişTarihi(LocalDate verilişTarihi) {
        this.verilişTarihi = verilişTarihi;
    }

    public LocalDate getPlanlananiAdeTarihi() {
        return planlananiAdeTarihi;
    }

    public void setPlanlananiAdeTarihi(LocalDate planlananiAdeTarihi) {
        this.planlananiAdeTarihi = planlananiAdeTarihi;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
