package com.turkcell.library_cqrs.library_cqrs.application.dto;

/**
 * Request DTO for creating a new Kategori (Category)
 */
public class CreateKategoriRequest {
    private String kategoriAdi;

    public CreateKategoriRequest() {
    }

    public CreateKategoriRequest(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    @Override
    public String toString() {
        return "CreateKategoriRequest{" +
                "kategoriAdi='" + kategoriAdi + '\'' +
                '}';
    }
}
