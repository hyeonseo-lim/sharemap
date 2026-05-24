package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FavoritePageController {

    private final FavoriteRepository favoriteRepository;
    private final PlaceRepository placeRepository;

    @GetMapping("/list/{username}")
    public String userFavoriteList(@PathVariable String username, Model model) {

        // 1. username으로 즐겨찾기 ID 목록 가져오기
        List<Long> favoriteIds = favoriteRepository.findByUsername(username)
                .stream()
                .map(Favorite::getPlaceId)
                .toList();

        // 2. 전체 장소 중에서 필터링
        List<Place> places = placeRepository.findAll()
                .stream()
                .filter(p -> favoriteIds.contains(p.getId()))
                .toList();

        // 3. 화면으로 전달
        model.addAttribute("places", places);
        model.addAttribute("targetUser", username);

        return "favorite-list";
    }
}