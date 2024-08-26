package net.test.util;

import javax.validation.ValidationException;

public class PasswordValidator {

    // Минимальное количество символов в пароле
    private static final int MIN_LENGTH = 7;
    // Регулярное выражение для проверки наличия хотя бы одного спецсимвола
    private static final String SPECIAL_CHARACTER_PATTERN = ".*[!@#$%^&*(),.?\":{}|<>].*";
    // Регулярное выражение для проверки наличия хотя бы трех цифр
    private static final String DIGIT_PATTERN = ".*(\\d.*\\d.*\\d).*";

    public static void validate(String password) {
        if (password.length() < MIN_LENGTH) {
            throw new ValidationException("Password must be at least " + MIN_LENGTH + " characters long.");
        }

        if (!password.matches(SPECIAL_CHARACTER_PATTERN)) {
            throw new ValidationException("Password must contain at least one special character.");
        }

        if (!password.matches(DIGIT_PATTERN)) {
            throw new ValidationException("Password must contain at least three digits.");
        }
    }
}
