package com.openwebinars.todo.rest.controller;

import com.openwebinars.todo.rest.dto.CategoryDto;
import com.openwebinars.todo.rest.model.category.Category;
import com.openwebinars.todo.rest.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Listar categorías",
            description = "Permite listar todas las categorías disponibles"
    )
    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll()
                .stream()
                .map(CategoryDto::of)
                .toList();
    }

    @Operation(
            summary = "Obtener una categoría",
            description = "Permite obtener una categoría concreta por su id"
    )
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return CategoryDto.of(categoryService.findById(id));
    }

    @Operation(
            summary = "Crear categoría",
            description = "Permite crear una categoría. Solo ADMIN y GESTOR pueden crear categorías"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryDto.of(categoryService.save(category)));
    }

    @Operation(
            summary = "Editar categoría",
            description = "Permite editar una categoría. Solo ADMIN y GESTOR pueden editar categorías"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PutMapping("/{id}")
    public CategoryDto edit(@PathVariable Long id, @RequestBody Category category) {
        return CategoryDto.of(categoryService.edit(id, category));
    }

    @Operation(
            summary = "Eliminar categoría",
            description = "Permite eliminar una categoría. Solo ADMIN y GESTOR pueden eliminar categorías"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}