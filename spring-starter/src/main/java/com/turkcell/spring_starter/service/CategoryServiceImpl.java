package com.turkcell.spring_starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import com.turkcell.spring_starter.dto.CreateCategoryRequest;
import com.turkcell.spring_starter.dto.CreatedCategoryResponse;
import com.turkcell.spring_starter.dto.ListCategoryResponse;
import com.turkcell.spring_starter.dto.UpdateCategoryRequest;
import com.turkcell.spring_starter.entity.Category;
import com.turkcell.spring_starter.repository.CategoryRepository;

@Service
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CreatedCategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        // Veritabanında insert-update çalıştır.
        // entity id'e sahipse update
        // entity id'si null ise insert

        Category category = new Category();
        category.setName(createCategoryRequest.getName());

        category = this.categoryRepository.save(category); // ekledikten sonraki halini al

        CreatedCategoryResponse response = new CreatedCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    } 

    public List<ListCategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();

        // TODO: Refactor
        List<ListCategoryResponse> responseList = new ArrayList<>();

        for (Category category : categories) {
            ListCategoryResponse response = new ListCategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            responseList.add(response);
        }

        return responseList;
    }

    public ListCategoryResponse getById(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            ListCategoryResponse response = new ListCategoryResponse();
            response.setId(category.get().getId());
            response.setName(category.get().getName());
            return response;
        }
        return null;
    }

    public CreatedCategoryResponse update(UpdateCategoryRequest updateCategoryRequest) {
        Optional<Category> category = categoryRepository.findById(updateCategoryRequest.getId());
        if (category.isPresent()) {
            category.get().setName(updateCategoryRequest.getName());
            Category updatedCategory = categoryRepository.save(category.get());

            CreatedCategoryResponse response = new CreatedCategoryResponse();
            response.setId(updatedCategory.getId());
            response.setName(updatedCategory.getName());
            return response;
        }
        return null;
    }

    public boolean delete(UUID id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
