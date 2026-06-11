package com.openwebinars.todo.rest.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Registrar usuario",
            description = "Permite registrar un usuario nuevo con rol USER"
    )
    @PostMapping("/auth/register")
    public ResponseEntity<NewUserResponse> createUser(@RequestBody NewUserCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NewUserResponse.of(userService.register(cmd)));
    }

    @Operation(
            summary = "Listar usuarios",
            description = "Permite listar todos los usuarios. Solo ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<NewUserResponse> getAll() {
        return userService.findAll()
                .stream()
                .map(NewUserResponse::of)
                .toList();
    }

    @Operation(
            summary = "Obtener usuario",
            description = "Permite obtener un usuario por id. Solo ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public NewUserResponse getById(@PathVariable Long id) {
        return NewUserResponse.of(userService.findById(id));
    }

    @Operation(
            summary = "Editar usuario",
            description = "Permite editar un usuario por id. Solo ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public NewUserResponse edit(@PathVariable Long id, @RequestBody NewUserCommand cmd) {
        return NewUserResponse.of(userService.edit(id, cmd));
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Permite eliminar un usuario por id. Solo ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Promocionar usuario a gestor",
            description = "Permite promocionar un usuario con rol USER a rol GESTOR. Solo ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/promote")
    public NewUserResponse promoteToGestor(@PathVariable Long id) {
        return NewUserResponse.of(userService.promoteToGestor(id));
    }

    @Operation(
            summary = "Degradar gestor a usuario",
            description = "Permite degradar un usuario con rol GESTOR a rol USER. Solo ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/degrade")
    public NewUserResponse degradeToUser(@PathVariable Long id) {
        return NewUserResponse.of(userService.degradeToUser(id));
    }
}