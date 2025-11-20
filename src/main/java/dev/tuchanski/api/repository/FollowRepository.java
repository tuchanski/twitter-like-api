package dev.tuchanski.api.repository;

import dev.tuchanski.api.entity.Follow;
import dev.tuchanski.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {
    boolean existsByFollowerAndFollowed(User follower, User followed);
    Follow findByFollowerAndFollowed(User follower, User followed);
}
