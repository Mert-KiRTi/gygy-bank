package com.turkcell.spring_starter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "kitap")
public class Kitap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kitapId;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String kitapAdi;

    @Column(nullable = false)
    private String yazar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    @Column(nullable = false)
    private String durum;

    public Kitap() {
    }

    public Kitap(String isbn, String kitapAdi, String yazar, Kategori kategori, String durum) {
        this.isbn = isbn;
        this.kitapAdi = kitapAdi;
        this.yazar = yazar;
        this.kategori = kategori;
        this.durum = durum;
    }

    public Integer getKitapId() {
        return kitapId;
    }

    public void setKitapId(Integer kitapId) {
        this.kitapId = kitapId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        this.yazar = yazar;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
