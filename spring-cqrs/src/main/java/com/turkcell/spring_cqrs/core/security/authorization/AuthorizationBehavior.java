package com.turkcell.spring_cqrs.core.security.authorization;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.turkcell.spring_cqrs.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.spring_cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import com.turkcell.spring_cqrs.core.security.context.UserContext;
import com.turkcell.spring_cqrs.core.security.exception.AuthenticatedException;
import com.turkcell.spring_cqrs.core.security.exception.AuthorizationException;

/**
 * CQRS pipeline'da kimlik doğrulama (authentication) ve yetkilendirme (authorization) kontrollerini gerçekleştiren behavior.
 * 
 * AuthorizableRequest'i implement eden request'ler bu behavior'dan geçer.
 * İlgili request'in authentication ve role-based authorization gereksinimlerini kontrol eder.
 */
@Component
@Order(10)
public class AuthorizationBehavior implements PipelineBehavior {
    private final UserContext userContext;

    public AuthorizationBehavior(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public boolean supports(Object request) {
        return request instanceof AuthorizableRequest;
    }

    /**
     * Pipeline'da request'in şu kontrolleri gerçekleştirir:
     * 
     * 1. Kimlik doğrulama kontrolü: Eğer isAuthenticated() true ise ve kullanıcı giriş yapmamışsa
     *    AuthenticatedException fırlatır.
     * 
     * 2. Yetkilendirme kontrolü: Eğer getRequiredRoles() boş değilse, kullanıcının bu rollerden
     *    en az birini taşıyıp taşımadığını kontrol eder. Eğer yoksa AuthorizationException fırlatır.
     */
    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        AuthorizableRequest authRequest = (AuthorizableRequest) request;
        
        // 1. Kimlik doğrulama kontrolü
        if (authRequest.isAuthenticated() && !userContext.isAuthenticated()) {
            throw new AuthenticatedException("Kimlik doğrulama başarısız. Lütfen giriş yapın.");
        }
        
        // 2. Yetkilendirme kontrolü (rol kontrolü)
        List<String> requiredRoles = authRequest.getRequiredRoles();
        if (!requiredRoles.isEmpty()) {
            // Kullanıcının gerekli rollerden en az birini taşıyıp taşımadığını kontrol et
            List<String> userRoles = userContext.getRoles();
            boolean hasRequiredRole = userRoles.stream()
                .anyMatch(requiredRoles::contains);
            
            if (!hasRequiredRole) {
                throw new AuthorizationException("Bu işlemi gerçekleştirmek için gerekli yetkiye sahip değilsiniz.");
            }
        }
        
        // Zincirdeki sonraki handler'ı çağır
        return next.invoke();
    }
}

