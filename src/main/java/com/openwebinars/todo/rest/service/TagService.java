package com.openwebinars.todo.rest.service;

import com.openwebinars.todo.rest.model.Tag.Tag;
import com.openwebinars.todo.rest.repos.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag edit(Long id, Tag tag) {
        return tagRepository.findById(id)
                .map(existingTag -> {
                    existingTag.setName(tag.getName());
                    return tagRepository.save(existingTag);
                })
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}