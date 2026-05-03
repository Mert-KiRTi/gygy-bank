package com.turkcell.library_cqrs.library_cqrs.application.features.kategori.query.getall;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_cqrs.library_cqrs.application.dto.ListKategoriResponse;
import com.turkcell.library_cqrs.library_cqrs.entity.Kategori;
import com.turkcell.library_cqrs.library_cqrs.repository.KategoriRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Query Handler for GetAllKategoriQuery
 * Handles retrieving all Kategoriler (Categories)
 */
@Component
public class GetAllKategoriQueryHandler implements QueryHandler<GetAllKategoriQuery, List<ListKategoriResponse>> {

    private final KategoriRepository kategoriRepository;

    public GetAllKategoriQueryHandler(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public List<ListKategoriResponse> handle(GetAllKategoriQuery query) {
        // Get all kategoriler from repository
        List<Kategori> kategoriler = kategoriRepository.findAll();

        // Map to response DTOs
        return kategoriler.stream()
                .map(this::mapToListResponse)
                .collect(Collectors.toList());
    }

    private ListKategoriResponse mapToListResponse(Kategori kategori) {
        return new ListKategoriResponse(
                kategori.getKategoriId(),
                kategori.getKategoriAdi()
        );
    }
}
