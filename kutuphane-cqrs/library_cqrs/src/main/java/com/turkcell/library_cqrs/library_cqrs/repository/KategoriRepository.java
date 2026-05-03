package com.turkcell.library_cqrs.library_cqrs.repository;

import com.turkcell.library_cqrs.library_cqrs.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Kategori (Category) entity
 */
@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Integer> {
}
