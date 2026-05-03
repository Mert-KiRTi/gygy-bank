package com.turkcell.library_cqrs.library_cqrs.web.controller;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.Mediator;
import com.turkcell.library_cqrs.library_cqrs.application.features.kitap.command.create.CreateKitapCommand;
import com.turkcell.library_cqrs.library_cqrs.application.features.kitap.query.getall.GetAllKitapQuery;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreateKitapRequest;
import com.turkcell.library_cqrs.library_cqrs.application.dto.CreatedKitapResponse;
import com.turkcell.library_cqrs.library_cqrs.application.dto.ListKitapResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Kitaplar (Books)
 * Uses CQRS pattern via Mediator
 */
@RestController
@RequestMapping("/api/kitaplar")
public class KitaplarController {

    private final Mediator mediator;

    public KitaplarController(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Create a new Kitap (Book)
     * POST /api/kitaplar
     */
    @PostMapping
    public ResponseEntity<CreatedKitapResponse> createKitap(
            @RequestBody CreateKitapRequest request
    ) {
        CreateKitapCommand command = new CreateKitapCommand(
                request.getIsbn(),
                request.getKitapAdi(),
                request.getYazar(),
                request.getKategoriId(),
                request.getDurum()
        );
        CreatedKitapResponse response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all Kitaplar (Books)
     * GET /api/kitaplar
     */
    @GetMapping
    public ResponseEntity<List<ListKitapResponse>> getAllKitaplar() {
        GetAllKitapQuery query = new GetAllKitapQuery();
        List<ListKitapResponse> response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
