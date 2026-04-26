package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "kategori")
public class Kategori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kategoriId;

    @Column(nullable = false)
    private String kategoriAdi;

    public Kategori() {
    }

    public Kategori(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public Integer getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Integer kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }
}
