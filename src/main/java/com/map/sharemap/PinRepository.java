/*
 * 충북대 공유지도 - 핀 저장소 인터페이스
 * 작성자: 장래정
 * 설명: 핀 데이터를 데이터베이스에 저장하고 관리하기 위한 JPA 리포지토리 인터페이스
 * Pin 엔티티와 연동하여 CRUD 작업을 수행할 수 있도록 JpaRepository를 확장
 * PinController에서 핀 정보를 데이터베이스에 저장하고 조회하는 데 사용됨
 * 향후 기능 확장 시, 핀 저장소는 사용자와의 연관 관계 설정, 이미지 첨부 기능 등 추가적인 속성을 포함할 수 있다
 */

package com.map.sharemap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {
    // 💡 추가: 특정 username이 작성한 핀 목록만 조회하는 메서드
    List<Pin> findByUsername(String username);
}