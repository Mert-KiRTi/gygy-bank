package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ogrenci")
public class Ogrenci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ogrenciId;

    @Column(nullable = false, unique = true)
    private String okulNo;

    @Column(nullable = false)
    private String adSoyad;

    @Column(unique = true)
    private String email;

    private String telefon;

    public Ogrenci() {
    }

    public Ogrenci(String okulNo, String adSoyad, String email, String telefon) {
        this.okulNo = okulNo;
        this.adSoyad = adSoyad;
        this.email = email;
        this.telefon = telefon;
    }

    public Integer getOgrenciId() {
        return ogrenciId;
    }

    public void setOgrenciId(Integer ogrenciId) {
        this.ogrenciId = ogrenciId;
    }

    public String getOkulNo() {
        return okulNo;
    }

    public void setOkulNo(String okulNo) {
        this.okulNo = okulNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
