package com.turkcell.library_cqrs.library_cqrs.application.features.kitap.command.create;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Command;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreatedKitapResponse;

/**
 * Command for creating a new Kitap (Book)
 */
public class CreateKitapCommand implements Command<CreatedKitapResponse> {
    private String isbn;
    private String kitapAdi;
    private String yazar;
    private Integer kategoriId;
    private String durum;

    public CreateKitapCommand() {
    }

    public CreateKitapCommand(String isbn, String kitapAdi, String yazar, Integer kategoriId, String durum) {
        this.isbn = isbn;
        this.kitapAdi = kitapAdi;
        this.yazar = yazar;
        this.kategoriId = kategoriId;
        this.durum = durum;
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
        return "CreateKitapCommand{" +
                "isbn='" + isbn + '\'' +
                ", kitapAdi='" + kitapAdi + '\'' +
                ", yazar='" + yazar + '\'' +
                ", kategoriId=" + kategoriId +
                ", durum='" + durum + '\'' +
                '}';
    }
}
