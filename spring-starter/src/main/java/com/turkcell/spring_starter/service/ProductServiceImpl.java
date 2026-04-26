package com.turkcell.spring_starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.turkcell.spring_starter.dto.CreateProductRequest;
import com.turkcell.spring_starter.dto.CreatedProductResponse;
import com.turkcell.spring_starter.dto.ListProductResponse;
import com.turkcell.spring_starter.dto.UpdateProductRequest;
import com.turkcell.spring_starter.entity.Category;
import com.turkcell.spring_starter.entity.Product;
import com.turkcell.spring_starter.repository.CategoryRepository;
import com.turkcell.spring_starter.repository.ProductRepository;

@Service
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public CreatedProductResponse create(CreateProductRequest createProductRequest) {
        Optional<Category> category = categoryRepository.findById(createProductRequest.getCategoryId());
        if (!category.isPresent()) {
            return null;
        }

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setDescription(createProductRequest.getDescription());
        product.setCategory(category.get());

        product = productRepository.save(product);

        CreatedProductResponse response = new CreatedProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategoryId(product.getCategory().getId());

        return response;
    }

    public List<ListProductResponse> getAll() {
        List<Product> products = productRepository.findAll();

        List<ListProductResponse> responseList = new ArrayList<>();

        for (Product product : products) {
            ListProductResponse response = new ListProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setCategoryId(product.getCategory().getId());
            responseList.add(response);
        }

        return responseList;
    }

    public ListProductResponse getById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ListProductResponse response = new ListProductResponse();
            response.setId(product.get().getId());
            response.setName(product.get().getName());
            response.setDescription(product.get().getDescription());
            response.setCategoryId(product.get().getCategory().getId());
            return response;
        }
        return null;
    }

    public CreatedProductResponse update(UpdateProductRequest updateProductRequest) {
        Optional<Product> product = productRepository.findById(updateProductRequest.getId());
        if (!product.isPresent()) {
            return null;
        }

        Optional<Category> category = categoryRepository.findById(updateProductRequest.getCategoryId());
        if (!category.isPresent()) {
            return null;
        }

        product.get().setName(updateProductRequest.getName());
        product.get().setDescription(updateProductRequest.getDescription());
        product.get().setCategory(category.get());

        Product updatedProduct = productRepository.save(product.get());

        CreatedProductResponse response = new CreatedProductResponse();
        response.setId(updatedProduct.getId());
        response.setName(updatedProduct.getName());
        response.setDescription(updatedProduct.getDescription());
        response.setCategoryId(updatedProduct.getCategory().getId());

        return response;
    }

    public boolean delete(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
