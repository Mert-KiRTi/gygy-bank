package com.turkcell.library_cqrs.library_cqrs.application.features.kategori.command.create;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Command;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreatedKategoriResponse;

/**
 * Command for creating a new Kategori (Category)
 */
public class CreateKategoriCommand implements Command<CreatedKategoriResponse> {
    private String kategoriAdi;

    public CreateKategoriCommand() {
    }

    public CreateKategoriCommand(String kategoriAdi) {
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
        return "CreateKategoriCommand{" +
                "kategoriAdi='" + kategoriAdi + '\'' +
                '}';
    }
}
