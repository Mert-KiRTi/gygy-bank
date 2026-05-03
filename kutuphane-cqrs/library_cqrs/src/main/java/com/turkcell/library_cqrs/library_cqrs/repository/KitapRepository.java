package com.turkcell.library_cqrs.library_cqrs.repository;

import com.turkcell.library_cqrs.library_cqrs.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Kitap (Book) entity
 */
@Repository
public interface KitapRepository extends JpaRepository<Kitap, Integer> {
}
