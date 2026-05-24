package com.map.sharemap;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // 이름
    private String category;      // 카테고리
    private Double rating;        // 별점
    private String phone;         // 전화번호
    private String address;       // 주소
    private String businessHours; // 영업시간
    private Double latitude;      // 위도
    private Double longitude;     // 경도
    private String imageUrl;      // 이미지
}
