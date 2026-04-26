package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gorevli")
public class Gorevli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gorevliId;

    @Column(nullable = false)
    private String adSoyad;

    @Column(nullable = false)
    private String rol;

    public Gorevli() {
    }

    public Gorevli(String adSoyad, String rol) {
        this.adSoyad = adSoyad;
        this.rol = rol;
    }

    public Integer getGorevliId() {
        return gorevliId;
    }

    public void setGorevliId(Integer gorevliId) {
        this.gorevliId = gorevliId;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
