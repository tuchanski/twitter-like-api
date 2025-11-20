package dev.tuchanski.api.repository;

import dev.tuchanski.api.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {
    boolean existsByFollowerId(UUID followerId, UUID followingId);
}
