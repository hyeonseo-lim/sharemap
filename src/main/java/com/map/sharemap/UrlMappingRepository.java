package com.map.sharemap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
    Optional<UrlMapping> findByUrlKey(String urlKey);

    // ListEntity로 UrlMapping 조회
    Optional<UrlMapping> findByList(ListEntity list);

    // 또는 userId로 조회하고 싶다면 이렇게도 가능
    Optional<UrlMapping> findByList_UserId(String userId);
}


