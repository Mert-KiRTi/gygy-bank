package com.turkcell.library_cqrs.library_cqrs.application.features.kitap.query.getall;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_cqrs.library_cqrs.application.dto.ListKitapResponse;
import com.turkcell.library_cqrs.library_cqrs.entity.Kitap;
import com.turkcell.library_cqrs.library_cqrs.repository.KitapRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Query Handler for GetAllKitapQuery
 * Handles retrieving all Kitaplar (Books)
 */
@Component
public class GetAllKitapQueryHandler implements QueryHandler<GetAllKitapQuery, List<ListKitapResponse>> {

    private final KitapRepository kitapRepository;

    public GetAllKitapQueryHandler(KitapRepository kitapRepository) {
        this.kitapRepository = kitapRepository;
    }

    @Override
    public List<ListKitapResponse> handle(GetAllKitapQuery query) {
        // Get all kitaplar from repository
        List<Kitap> kitaplar = kitapRepository.findAll();

        // Map to response DTOs
        return kitaplar.stream()
                .map(this::mapToListResponse)
                .collect(Collectors.toList());
    }

    private ListKitapResponse mapToListResponse(Kitap kitap) {
        return new ListKitapResponse(
                kitap.getKitapId(),
                kitap.getIsbn(),
                kitap.getKitapAdi(),
                kitap.getYazar(),
                kitap.getKategori().getKategoriId(),
                kitap.getKategori().getKategoriAdi(),
                kitap.getDurum()
        );
    }
}
