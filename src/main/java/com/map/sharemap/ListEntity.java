package com.map.sharemap;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ListEntity {
    @Id
    @Column(name = "list_id")   // PK 컬럼 이름 명시
    private String listId;

    @Column(name = "user_id")   // 컬럼 이름 명시
    private String userId;

    @ElementCollection
    @CollectionTable(
            name = "list_items",
            joinColumns = @JoinColumn(name = "list_id")
    )
    @Column(name = "item")
    private List<Long> items;
}
