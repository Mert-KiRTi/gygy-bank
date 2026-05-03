package com.turkcell.library_cqrs.library_cqrs.application.features.kitap.query.getall;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Query;
import com.turkcell.library_cqrs.library_cqrs.application.dto.ListKitapResponse;
import java.util.List;

/**
 * Query for getting all Kitaplar (Books)
 */
public class GetAllKitapQuery implements Query<List<ListKitapResponse>> {

    public GetAllKitapQuery() {
    }

    @Override
    public String toString() {
        return "GetAllKitapQuery{}";
    }
}
