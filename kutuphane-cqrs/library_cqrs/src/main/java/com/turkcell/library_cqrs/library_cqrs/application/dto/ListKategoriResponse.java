package com.turkcell.library_cqrs.library_cqrs.application.dto;

/**
 * Response DTO for listing Kategori (Category)
 */
public class ListKategoriResponse {
    private Integer kategoriId;
    private String kategoriAdi;

    public ListKategoriResponse() {
    }

    public ListKategoriResponse(Integer kategoriId, String kategoriAdi) {
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
        return "ListKategoriResponse{" +
                "kategoriId=" + kategoriId +
                ", kategoriAdi='" + kategoriAdi + '\'' +
                '}';
    }
}
