package dev.tuchanski.api.service.follow;

import dev.tuchanski.api.dto.follow.FollowResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    FollowResponseDTO createFollow(String token, String usernameFollowTarget);
    List<FollowResponseDTO> getFollowing(String username);
    List<FollowResponseDTO> getFollowers(String username);
    void delete(String token, String usernameUnfollowTarget);
}
