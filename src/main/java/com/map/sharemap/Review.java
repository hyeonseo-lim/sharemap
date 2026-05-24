package com.map.sharemap;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 내용
    private String content;

    // 별점ㅁ
    private Integer star;

    private String nickname;

    private String username;

    @ManyToOne
    private Place place;

}