package dev.tuchanski.api.mapper;

import dev.tuchanski.api.dto.follow.FollowResponseDTO;
import dev.tuchanski.api.entity.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

    public FollowResponseDTO toDTO(Follow follow) {
        return new FollowResponseDTO(
                follow.getId()
        );
    }

}
