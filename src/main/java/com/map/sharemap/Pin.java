/*
 * 충북대 공유지도 - 핀 엔티티 클래스
 * 작성자: 장래정
 * 설명: 핀 엔티티 클래스는 지도에 표시되는 장소 정보를 저장하는 역할을 합니다. 각 핀은 고유 ID, 위도, 경도, 메모, 카테고리 등의 속성을 가진다
 * 속성 설명:
 * - id: 핀의 고유 식별자 (자동 생성)
 * - lat: 핀의 위도 정보 (double 타입)
 * - lng: 핀의 경도 정보 (double 타입)
 * memo: 핀에 대한 메모나 설명을 저장하는 문자열
 * category: 핀의 카테고리를 나타내는 문자열 (예: 음식점, 카페, 기타)
 * JPA를 사용하여 데이터베이스에 매핑되며, Lombok의 @Getter와 @Setter 어노테이션을 사용하여 getter와 setter 메서드를 자동으로 생성합니다. 이 엔티티는 PinController에서 핀 정보를 데이터베이스에 저장하고 조회하는 데 사용됩니다.
 * 향후 기능 확장 시, 핀 엔티티는 사용자와의 연관 관계 설정, 이미지 첨부 기능 등 추가적인 속성을 포함할 수 있다
 */

package com.map.sharemap;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double lat;
    private double lng;
    private String name;    //장소 이름
    private String memo;    //메모
    private String category;    //카테고리

    //추가: 이 핀을 등록한 사용자의 아이디를 저장할 필드
    private String username;
}