package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ceza")
public class Ceza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cezaId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "odunc_id", nullable = false)
    private OduncAlma oduncAlma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ogrenci_id", nullable = false)
    private Ogrenci ogrenci;

    @Column(nullable = false)
    private BigDecimal cezaTutari;

    private String cezaNedeni;

    @Column(nullable = false)
    private Boolean odendiMi;

    public Ceza() {
    }

    public Ceza(OduncAlma oduncAlma, Ogrenci ogrenci, BigDecimal cezaTutari, 
                String cezaNedeni, Boolean odendiMi) {
        this.oduncAlma = oduncAlma;
        this.ogrenci = ogrenci;
        this.cezaTutari = cezaTutari;
        this.cezaNedeni = cezaNedeni;
        this.odendiMi = odendiMi;
    }

    public Integer getCezaId() {
        return cezaId;
    }

    public void setCezaId(Integer cezaId) {
        this.cezaId = cezaId;
    }

    public OduncAlma getOduncAlma() {
        return oduncAlma;
    }

    public void setOduncAlma(OduncAlma oduncAlma) {
        this.oduncAlma = oduncAlma;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public BigDecimal getCezaTutari() {
        return cezaTutari;
    }

    public void setCezaTutari(BigDecimal cezaTutari) {
        this.cezaTutari = cezaTutari;
    }

    public String getCezaNedeni() {
        return cezaNedeni;
    }

    public void setCezaNedeni(String cezaNedeni) {
        this.cezaNedeni = cezaNedeni;
    }

    public Boolean getOdendiMi() {
        return odendiMi;
    }

    public void setOdendiMi(Boolean odendiMi) {
        this.odendiMi = odendiMi;
    }
}
