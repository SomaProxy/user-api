package net.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    @Size(max = 50)
    private String login;

    @Column(length = 20, nullable = false)
    @Size(min = 7, max = 20)
    @Pattern(regexp = "^(?=.*[0-9]{3})(?=.*[!@#$%^&*()]).{7,20}$")
    private String password;

    @Column(length = 256)
    @Size(max = 256)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}