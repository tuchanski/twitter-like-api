package dev.tuchanski.api.repository;

import dev.tuchanski.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
