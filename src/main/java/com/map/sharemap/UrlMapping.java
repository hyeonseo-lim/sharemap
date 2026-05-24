package com.map.sharemap;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity //JPA 엔티티 선언
@Getter
@Setter
@NoArgsConstructor
public class UrlMapping {

    @Id //urlKey를 기본 키로 사용 (UUID 문자열)
    private String urlKey;   // UUID 기반 URL 키

    // 리스트와 매핑 (FK)
    @ManyToOne //여러 URL 매핑이 하나의 리스트를 가리킬 수 있으므로 관계 설정
    @JoinColumn(name = "list_id") //ListEntity의 listId와 연결
    private ListEntity list;
}

