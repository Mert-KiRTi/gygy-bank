package com.turkcell.library_cqrs.library_cqrs.application.dto;

/**
 * Response DTO for created Kitap (Book)
 */
public class CreatedKitapResponse {
    private Integer kitapId;
    private String isbn;
    private String kitapAdi;
    private String yazar;
    private Integer kategoriId;
    private String durum;

    public CreatedKitapResponse() {
    }

    public CreatedKitapResponse(Integer kitapId, String isbn, String kitapAdi, String yazar, Integer kategoriId, String durum) {
        this.kitapId = kitapId;
        this.isbn = isbn;
        this.kitapAdi = kitapAdi;
        this.yazar = yazar;
        this.kategoriId = kategoriId;
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

    public Integer getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Integer kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return "CreatedKitapResponse{" +
                "kitapId=" + kitapId +
                ", isbn='" + isbn + '\'' +
                ", kitapAdi='" + kitapAdi + '\'' +
                ", yazar='" + yazar + '\'' +
                ", kategoriId=" + kategoriId +
                ", durum='" + durum + '\'' +
                '}';
    }
}
