package com.turkcell.spring_starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.turkcell.spring_starter.dto.CreateTagRequest;
import com.turkcell.spring_starter.dto.CreatedTagResponse;
import com.turkcell.spring_starter.dto.ListTagResponse;
import com.turkcell.spring_starter.dto.UpdateTagRequest;
import com.turkcell.spring_starter.entity.Tag;
import com.turkcell.spring_starter.repository.TagRepository;

@Service
public class TagServiceImpl {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public CreatedTagResponse create(CreateTagRequest createTagRequest) {
        Tag tag = new Tag();
        tag.setName(createTagRequest.getName());

        tag = tagRepository.save(tag);

        CreatedTagResponse response = new CreatedTagResponse();
        response.setId(tag.getId());
        response.setName(tag.getName());

        return response;
    }

    public List<ListTagResponse> getAll() {
        List<Tag> tags = tagRepository.findAll();

        List<ListTagResponse> responseList = new ArrayList<>();

        for (Tag tag : tags) {
            ListTagResponse response = new ListTagResponse();
            response.setId(tag.getId());
            response.setName(tag.getName());
            responseList.add(response);
        }

        return responseList;
    }

    public ListTagResponse getById(UUID id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            ListTagResponse response = new ListTagResponse();
            response.setId(tag.get().getId());
            response.setName(tag.get().getName());
            return response;
        }
        return null;
    }

    public CreatedTagResponse update(UpdateTagRequest updateTagRequest) {
        Optional<Tag> tag = tagRepository.findById(updateTagRequest.getId());
        if (tag.isPresent()) {
            tag.get().setName(updateTagRequest.getName());
            Tag updatedTag = tagRepository.save(tag.get());

            CreatedTagResponse response = new CreatedTagResponse();
            response.setId(updatedTag.getId());
            response.setName(updatedTag.getName());
            return response;
        }
        return null;
    }

    public boolean delete(UUID id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
