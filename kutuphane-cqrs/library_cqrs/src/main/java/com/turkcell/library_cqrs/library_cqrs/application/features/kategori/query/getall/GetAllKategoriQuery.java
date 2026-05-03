package com.turkcell.library_cqrs.library_cqrs.application.features.kategori.query.getall;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Query;
import com.turkcell.library_cqrs.library_cqrs.application.dto.ListKategoriResponse;
import java.util.List;

/**
 * Query for getting all Kategoriler (Categories)
 */
public class GetAllKategoriQuery implements Query<List<ListKategoriResponse>> {

    public GetAllKategoriQuery() {
    }

    @Override
    public String toString() {
        return "GetAllKategoriQuery{}";
    }
}
