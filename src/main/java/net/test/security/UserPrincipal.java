package net.test.security;

import net.test.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER")); // Пример, можно добавить роли
        return new UserPrincipal(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Реализуйте по необходимости
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Реализуйте по необходимости
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Реализуйте по необходимости
    }

    @Override
    public boolean isEnabled() {
        return true; // Реализуйте по необходимости
    }
}
