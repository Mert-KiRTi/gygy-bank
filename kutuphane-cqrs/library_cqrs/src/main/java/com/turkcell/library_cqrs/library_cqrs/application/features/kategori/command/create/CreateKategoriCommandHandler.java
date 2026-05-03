package com.turkcell.library_cqrs.library_cqrs.application.features.kategori.command.create;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreatedKategoriResponse;
import com.turkcell.library_cqrs.library_cqrs.entity.Kategori;
import com.turkcell.library_cqrs.library_cqrs.repository.KategoriRepository;
import org.springframework.stereotype.Component;

/**
 * Command Handler for CreateKategoriCommand
 * Handles the creation of a new Kategori (Category)
 */
@Component
public class CreateKategoriCommandHandler implements CommandHandler<CreateKategoriCommand, CreatedKategoriResponse> {

    private final KategoriRepository kategoriRepository;

    public CreateKategoriCommandHandler(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public CreatedKategoriResponse handle(CreateKategoriCommand command) {
        // Create new Kategori entity
        Kategori kategori = new Kategori();
        kategori.setKategoriAdi(command.getKategoriAdi());

        // Save to repository
        Kategori savedKategori = kategoriRepository.save(kategori);

        // Return response DTO
        return new CreatedKategoriResponse(
                savedKategori.getKategoriId(),
                savedKategori.getKategoriAdi()
        );
    }
}
