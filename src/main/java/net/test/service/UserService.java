package net.test.service;


import net.test.entity.User;
import net.test.repository.UserRepository;
import net.test.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        PasswordValidator.validate(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!userDetails.getPassword().equals(user.getPassword())) {
            PasswordValidator.validate(userDetails.getPassword());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        user.setLogin(userDetails.getLogin());
        user.setFullName(userDetails.getFullName());
        user.setGender(userDetails.getGender());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}

