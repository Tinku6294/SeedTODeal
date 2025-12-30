package com.Coder.UserService.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming user.getRoles() returns a Set<Role>
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // "ADMIN", "FARMER"
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();  // adjust to match your User entity field
    }

    @Override
    public String getUsername() {
        // Better to use email or phone as username, not fullName
        return user.getFullName();
    }
    public String getEmail(){
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // change logic if you track expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStatus().name().equalsIgnoreCase("BANNED");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change if you expire passwords
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().name().equalsIgnoreCase("ACTIVE");
    }
}
