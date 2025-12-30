package com.Coder.UserService.repository;

import com.Coder.UserService.model.User;
import com.Coder.UserService.model.UserStatus;
import com.Coder.UserService.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Find user by email
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRole(@Param("email") String email);


    // Check if user exists by email
    boolean existsByEmail(String email);


}