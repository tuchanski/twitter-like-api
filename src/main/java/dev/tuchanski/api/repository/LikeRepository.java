package dev.tuchanski.api.repository;

import dev.tuchanski.api.entity.Like;
import dev.tuchanski.api.entity.Tweet;
import dev.tuchanski.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    boolean existsByUserAndTweet(User user, Tweet tweet);
}
