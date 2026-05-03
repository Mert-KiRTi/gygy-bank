package com.turkcell.library_cqrs.library_cqrs.web.controller;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.Mediator;
import com.turkcell.library_cqrs.library_cqrs.application.features.kategori.command.create.CreateKategoriCommand;
import com.turkcell.library_cqrs.library_cqrs.application.features.kategori.query.getall.GetAllKategoriQuery;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreateKategoriRequest;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreatedKategoriResponse;
import com.turkcell.library_cqrs.library_cqrs.application.dto.ListKategoriResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Kategoriler (Categories)
 * Uses CQRS pattern via Mediator
 */
@RestController
@RequestMapping("/api/kategoriler")
public class KategorilerController {

    private final Mediator mediator;

    public KategorilerController(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Create a new Kategori (Category)
     * POST /api/kategoriler
     */
    @PostMapping
    public ResponseEntity<CreatedKategoriResponse> createKategori(
            @RequestBody CreateKategoriRequest request
    ) {
        CreateKategoriCommand command = new CreateKategoriCommand(request.getKategoriAdi());
        CreatedKategoriResponse response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all Kategoriler (Categories)
     * GET /api/kategoriler
     */
    @GetMapping
    public ResponseEntity<List<ListKategoriResponse>> getAllKategoriler() {
        GetAllKategoriQuery query = new GetAllKategoriQuery();
        List<ListKategoriResponse> response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
