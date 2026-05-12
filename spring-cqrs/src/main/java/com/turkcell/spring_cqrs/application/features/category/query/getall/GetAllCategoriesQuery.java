package com.turkcell.spring_cqrs.application.features.category.query.getall;

import java.util.List;

import org.springframework.data.domain.Page;

import com.turkcell.spring_cqrs.core.mediator.cqrs.Query;
import com.turkcell.spring_cqrs.core.security.authorization.AuthorizableRequest;

/**
 * Kategorilerin paginated listesini getiren query.
 * 
 * Bu query için kimlik doğrulama zorunludur ve kullanıcı "ADMIN" veya "USER" rolüne sahip olmalıdır.
 * 
 * JWT olmaksızın veya geçersiz token ile request atılırsa: 401 Unauthorized
 * JWT var ama gerekli rollere sahip değilse: 403 Forbidden
 */
public record GetAllCategoriesQuery(int pageNumber, int pageSize) implements Query<Page<GetAllCategoriesResponse>>, AuthorizableRequest {
    
    /**
     * Bu query'nin çalışabilmesi için kimlik doğrulama gereklidir.
     */
    @Override
    public boolean isAuthenticated() {
        return true;
    }
    
    /**
     * Bu query'yi çalıştırabilmek için kullanıcının "ADMIN" veya "USER" rolüne sahip olması gerekir.
     */
    @Override
    public List<String> getRequiredRoles() {
        return List.of("ADMIN", "USER");
    }
}