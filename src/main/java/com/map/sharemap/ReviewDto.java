package com.map.sharemap;

import lombok.Data;

@Data
public class ReviewDto {

    private Long id;
    private Long placeId;

    private Integer star;
    private String content;

    private String username;
    private String nickname;
}