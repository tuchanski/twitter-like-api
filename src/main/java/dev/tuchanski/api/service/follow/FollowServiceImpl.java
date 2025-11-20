package dev.tuchanski.api.service.follow;

import dev.tuchanski.api.dto.follow.FollowResponseDTO;
import dev.tuchanski.api.entity.Follow;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.exception.follow.RelationshipAlreadyExistsException;
import dev.tuchanski.api.exception.follow.RelationshipIsNotValidException;
import dev.tuchanski.api.exception.follow.RelationshipNotFoundException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import dev.tuchanski.api.mapper.FollowMapper;
import dev.tuchanski.api.repository.FollowRepository;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static dev.tuchanski.api.service.user.UserServiceImpl.getUser;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final FollowMapper followMapper;

    @Override
    @Transactional
    public FollowResponseDTO createFollow(String token, String usernameFollowTarget) {
        User sessionUser = getUserFromToken(token);

        User followTarget = (User) userRepository.findByUsername(usernameFollowTarget);

        if (followTarget == null) {
            throw new UserNotFoundException("User not found with username " + usernameFollowTarget);
        }

        if (sessionUser.getId().equals(followTarget.getId())) {
            throw new RelationshipIsNotValidException("You are not allowed to follow yourself");
        }

        if (followRepository.existsByFollowerAndFollowed(sessionUser, followTarget)) {
            throw new RelationshipAlreadyExistsException("This user is already following " + followTarget.getUsername());
        }

        Follow relationship = new Follow();

        relationship.setFollower(sessionUser);
        relationship.setFollowed(followTarget);

        relationship = followRepository.save(relationship);

        return followMapper.toDTO(relationship);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowResponseDTO> getFollowing(String username) {
        User user = (User) userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found with name " + username);
        }

        return user.getFollowing().stream().map(followMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowResponseDTO> getFollowers(String username) {
        User user = (User) userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found with name " + username);
        }

        return user.getFollowers().stream().map(followMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String token, String usernameUnfollowTarget) {
        User sessionUser = getUserFromToken(token);
        User unfollowTarget = (User) userRepository.findByUsername(usernameUnfollowTarget);

        if (unfollowTarget == null) {
            throw new UserNotFoundException("User not found with name " + usernameUnfollowTarget);
        }

        if (sessionUser.getId().equals(unfollowTarget.getId())) {
            throw new RelationshipIsNotValidException("You are not allowed to follow/unfollow yourself");
        }

        Follow follow = followRepository.findByFollowerAndFollowed(sessionUser, unfollowTarget);

        if (follow == null) {
            throw new RelationshipNotFoundException("Follow not found");
        }

        followRepository.delete(follow);
    }

    private User getUserFromToken(String token) {
        return getUser(token, tokenService, userRepository);
    }
}
