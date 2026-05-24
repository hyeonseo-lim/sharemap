package com.map.sharemap;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ListRepository extends JpaRepository<ListEntity, String> {
    Optional<ListEntity> findByUserId(String userId);
}


