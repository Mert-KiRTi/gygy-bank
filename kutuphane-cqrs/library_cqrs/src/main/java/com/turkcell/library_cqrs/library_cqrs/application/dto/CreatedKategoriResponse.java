package com.turkcell.library_cqrs.library_cqrs.application.dto;

/**
 * Response DTO for created Kategori (Category)
 */
public class CreatedKategoriResponse {
    private Integer kategoriId;
    private String kategoriAdi;

    public CreatedKategoriResponse() {
    }

    public CreatedKategoriResponse(Integer kategoriId, String kategoriAdi) {
        this.kategoriId = kategoriId;
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

    @Override
    public String toString() {
        return "CreatedKategoriResponse{" +
                "kategoriId=" + kategoriId +
                ", kategoriAdi='" + kategoriAdi + '\'' +
                '}';
    }
}
