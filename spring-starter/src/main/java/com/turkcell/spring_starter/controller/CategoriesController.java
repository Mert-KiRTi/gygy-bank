package com.turkcell.spring_starter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.spring_starter.dto.CreateCategoryRequest;
import com.turkcell.spring_starter.dto.CreatedCategoryResponse;
import com.turkcell.spring_starter.dto.ListCategoryResponse;
import com.turkcell.spring_starter.dto.UpdateCategoryRequest;
import com.turkcell.spring_starter.entity.Category;
import com.turkcell.spring_starter.service.CategoryServiceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private final CategoryServiceImpl categoryServiceImpl;

    public CategoriesController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @PostMapping()
    public CreatedCategoryResponse create(@RequestBody CreateCategoryRequest createCategoryRequest) {
        return categoryServiceImpl.create(createCategoryRequest);
    }

    @GetMapping
    public List<ListCategoryResponse> getAll() {
        return categoryServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public ListCategoryResponse getById(@PathVariable UUID id) {
        return categoryServiceImpl.getById(id);
    }

    @PutMapping("/{id}")
    public CreatedCategoryResponse update(@PathVariable UUID id, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        updateCategoryRequest.setId(id);
        return categoryServiceImpl.update(updateCategoryRequest);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable UUID id) {
        return categoryServiceImpl.delete(id);
    }
}
