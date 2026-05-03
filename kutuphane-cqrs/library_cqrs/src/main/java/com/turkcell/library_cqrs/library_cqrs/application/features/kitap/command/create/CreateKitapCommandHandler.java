package com.turkcell.library_cqrs.library_cqrs.application.features.kitap.command.create;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreatedKitapResponse;
import com.turkcell.library_cqrs.library_cqrs.entity.Kategori;
import com.turkcell.library_cqrs.library_cqrs.entity.Kitap;
import com.turkcell.library_cqrs.library_cqrs.exception.BusinessException;
import com.turkcell.library_cqrs.library_cqrs.repository.KategoriRepository;
import com.turkcell.library_cqrs.library_cqrs.repository.KitapRepository;
import org.springframework.stereotype.Component;

/**
 * Command Handler for CreateKitapCommand
 * Handles the creation of a new Kitap (Book)
 */
@Component
public class CreateKitapCommandHandler implements CommandHandler<CreateKitapCommand, CreatedKitapResponse> {

    private final KitapRepository kitapRepository;
    private final KategoriRepository kategoriRepository;

    public CreateKitapCommandHandler(KitapRepository kitapRepository, KategoriRepository kategoriRepository) {
        this.kitapRepository = kitapRepository;
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public CreatedKitapResponse handle(CreateKitapCommand command) {
        // Validate that the category exists
        Kategori kategori = kategoriRepository.findById(command.getKategoriId())
                .orElseThrow(() -> new BusinessException("Kategori bulunamadi: " + command.getKategoriId()));

        // Create new Kitap entity
        Kitap kitap = new Kitap();
        kitap.setIsbn(command.getIsbn());
        kitap.setKitapAdi(command.getKitapAdi());
        kitap.setYazar(command.getYazar());
        kitap.setKategori(kategori);
        kitap.setDurum(command.getDurum());

        // Save to repository
        Kitap savedKitap = kitapRepository.save(kitap);

        // Return response DTO
        return new CreatedKitapResponse(
                savedKitap.getKitapId(),
                savedKitap.getIsbn(),
                savedKitap.getKitapAdi(),
                savedKitap.getYazar(),
                savedKitap.getKategori().getKategoriId(),
                savedKitap.getDurum()
        );
    }
}
