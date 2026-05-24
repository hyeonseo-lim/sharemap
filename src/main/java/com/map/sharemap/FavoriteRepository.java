package com.map.sharemap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findByUsernameAndPlaceId(String username, Long placeId);

    List<Favorite> findByUsername(String username);
}