package dev.tuchanski.api.mapper;

import dev.tuchanski.api.dto.like.LikeResponseDTO;
import dev.tuchanski.api.entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeResponseDTO toDTO(Like like) {
        return new LikeResponseDTO(
                like.getId(),
                like.getUser().getUsername(),
                like.getTweet().getId()
        );
    }

}
