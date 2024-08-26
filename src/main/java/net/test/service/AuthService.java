package net.test.service;

import net.test.entity.User;
import net.test.payload.LoginRequest;
import net.test.payload.LoginResponse;
import net.test.repository.UserRepository;
import net.test.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Метод для аутентификации пользователя и генерации JWT-токена
     *
     * @param loginRequest объект запроса с логином и паролем
     * @return объект ответа с JWT-токеном и деталями пользователя
     */
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Аутентификация пользователя
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );

        // Устанавливаем контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Генерация JWT токена
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Получаем данные пользователя
        User user = userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Возвращаем ответ с JWT токеном и деталями пользователя
        return new LoginResponse(jwt, user);
    }

    /**
     * Метод для регистрации нового пользователя
     *
     * @param user пользователь, который должен быть зарегистрирован
     * @return зарегистрированный пользователь
     */
    public User registerUser(User user) {
        // Проверка, существует ли пользователь с таким же логином
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new RuntimeException("Username is already taken");
        }

        // Шифруем пароль перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Сохраняем пользователя в базе данных
        return userRepository.save(user);
    }
}

