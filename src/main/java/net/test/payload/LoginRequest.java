package net.test.payload;
import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}

