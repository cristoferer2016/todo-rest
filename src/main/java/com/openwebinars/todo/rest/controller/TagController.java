package com.openwebinars.todo.rest.controller;

import com.openwebinars.todo.rest.dto.TagDto;
import com.openwebinars.todo.rest.model.Tag.Tag;
import com.openwebinars.todo.rest.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class TagController {

    private final TagService tagService;

    @Operation(
            summary = "Listar tags",
            description = "Permite listar todos los tags disponibles"
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping
    public List<TagDto> getAll() {
        return tagService.findAll()
                .stream()
                .map(TagDto::of)
                .toList();
    }

    @Operation(
            summary = "Obtener un tag",
            description = "Permite obtener un tag concreto por su id"
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        return TagDto.of(tagService.findById(id));
    }

    @Operation(
            summary = "Crear tag",
            description = "Permite crear un tag"
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PostMapping
    public ResponseEntity<TagDto> create(@RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TagDto.of(tagService.save(tag)));
    }

    @Operation(
            summary = "Editar tag",
            description = "Permite editar un tag"
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PutMapping("/{id}")
    public TagDto edit(@PathVariable Long id, @RequestBody Tag tag) {
        return TagDto.of(tagService.edit(id, tag));
    }

    @Operation(
            summary = "Eliminar tag",
            description = "Permite eliminar un tag"
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}