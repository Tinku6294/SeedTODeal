package com.Coder.UserService.utill;

import com.Coder.UserService.dto.JwtResponseDto;
import com.Coder.UserService.dto.LoginDto;
import com.Coder.UserService.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponseDto login(LoginDto loginDto) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Generate JWT token using UserDetailsImpl
            String token = jwtTokenProvider.generateToken(userDetails);

            // Extract info back from JWT or directly from userDetails
            Date expiry = jwtTokenProvider.getExpiryDate(token);

            String roles = userDetails.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(","));

            return new JwtResponseDto(
                    token,
                    "Bearer",
                    LocalDateTime.ofInstant(expiry.toInstant(), java.time.ZoneId.systemDefault()),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles
            );

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username/email or password", ex);
        }
    }
}
