package com.turkcell.spring_cqrs.core.security.authorization;

import java.util.ArrayList;
import java.util.List;

/**
 * CQRS request'lerinde authorization mantığını tanımlamak için kullanılan interface.
 * 
 * AuthorizableRequest'i implement eden request'ler, AuthorizationBehavior tarafından
 * işlenmeden önce doğrulama işleminden geçecektir.
 * 
 * İlgili request'in kimlik doğrulama ve yetkilendirme gereksinimlerini belirtir.
 */
public interface AuthorizableRequest {
    
    /**
     * Request'in kimlik doğrulama gerektirip gerektirmediğini belirtir.
     * 
     * @return true ise request'i yapacak kişi giriş yapmış olmalı, false ise hiç kimse yapabilir
     */
    default boolean isAuthenticated() {
        return false;
    }
    
    /**
     * Request'i yapabilmek için gereken rollerin listesini döner.
     * 
     * Boş liste dönerirse hiçbir rol kontrolü yapılmaz.
     * Boş olmayan bir liste dönerirse, kullanıcının bu rollerden en az birini taşıması gerekir.
     * 
     * @return İhtiyaç duyulan rollerin listesi
     */
    default List<String> getRequiredRoles() {
        return new ArrayList<>();
    }
}

