package com.turkcell.spring_starter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.spring_starter.dto.CreateTagRequest;
import com.turkcell.spring_starter.dto.CreatedTagResponse;
import com.turkcell.spring_starter.dto.ListTagResponse;
import com.turkcell.spring_starter.dto.UpdateTagRequest;
import com.turkcell.spring_starter.service.TagServiceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
    private final TagServiceImpl tagServiceImpl;

    public TagsController(TagServiceImpl tagServiceImpl) {
        this.tagServiceImpl = tagServiceImpl;
    }

    @PostMapping()
    public CreatedTagResponse create(@RequestBody CreateTagRequest createTagRequest) {
        return tagServiceImpl.create(createTagRequest);
    }

    @GetMapping
    public List<ListTagResponse> getAll() {
        return tagServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public ListTagResponse getById(@PathVariable UUID id) {
        return tagServiceImpl.getById(id);
    }

    @PutMapping("/{id}")
    public CreatedTagResponse update(@PathVariable UUID id, @RequestBody UpdateTagRequest updateTagRequest) {
        updateTagRequest.setId(id);
        return tagServiceImpl.update(updateTagRequest);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable UUID id) {
        return tagServiceImpl.delete(id);
    }
}
