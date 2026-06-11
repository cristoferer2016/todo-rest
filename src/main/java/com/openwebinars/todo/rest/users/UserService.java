package com.openwebinars.todo.rest.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(NewUserCommand cmd) {
        User user = User.builder()
                .username(cmd.username())
                .email(cmd.email())
                .password(passwordEncoder.encode(cmd.password()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User edit(Long id, NewUserCommand cmd) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(cmd.username());
                    user.setEmail(cmd.email());

                    if (cmd.password() != null && !cmd.password().isBlank()) {
                        user.setPassword(passwordEncoder.encode(cmd.password()));
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User promoteToGestor(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setRole(Role.GESTOR);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    public User editProfile(User currentUser, EditProfileDto cmd) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setUsername(cmd.username());
        user.setEmail(cmd.email());
        user.setFullname(cmd.fullname());

        if (cmd.password() != null && !cmd.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(cmd.password()));
        }

        return userRepository.save(user);
    }

    public User degradeToUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setRole(Role.USER);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}