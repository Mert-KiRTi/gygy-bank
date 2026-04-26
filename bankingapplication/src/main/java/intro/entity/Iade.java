package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "iade")
public class Iade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iadeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "odunc_id", nullable = false, unique = true)
    private OduncAlma oduncAlma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alan_gorevli_id", nullable = false)
    private Gorevli alanGorevli;

    @Column(nullable = false)
    private LocalDate gercekIadeTarihi;

    @Column(nullable = false)
    private String kitapKondisyonu;

    public Iade() {
    }

    public Iade(OduncAlma oduncAlma, Gorevli alanGorevli, LocalDate gercekIadeTarihi, String kitapKondisyonu) {
        this.oduncAlma = oduncAlma;
        this.alanGorevli = alanGorevli;
        this.gercekIadeTarihi = gercekIadeTarihi;
        this.kitapKondisyonu = kitapKondisyonu;
    }

    public Integer getIadeId() {
        return iadeId;
    }

    public void setIadeId(Integer iadeId) {
        this.iadeId = iadeId;
    }

    public OduncAlma getOduncAlma() {
        return oduncAlma;
    }

    public void setOduncAlma(OduncAlma oduncAlma) {
        this.oduncAlma = oduncAlma;
    }

    public Gorevli getAlanGorevli() {
        return alanGorevli;
    }

    public void setAlanGorevli(Gorevli alanGorevli) {
        this.alanGorevli = alanGorevli;
    }

    public LocalDate getGercekIadeTarihi() {
        return gercekIadeTarihi;
    }

    public void setGercekIadeTarihi(LocalDate gercekIadeTarihi) {
        this.gercekIadeTarihi = gercekIadeTarihi;
    }

    public String getKitapKondisyonu() {
        return kitapKondisyonu;
    }

    public void setKitapKondisyonu(String kitapKondisyonu) {
        this.kitapKondisyonu = kitapKondisyonu;
    }
}
