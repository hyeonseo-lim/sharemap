package com.map.sharemap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    // =========================
    // 추가 / 삭제 토글
    // =========================
    @PostMapping("/{placeId}")
    public String toggle(@PathVariable Long placeId, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "로그인 필요";
        }

        String username = user.getUsername().trim();

        // 🔥 Repository가 Favorite 반환이면 이게 정답
        Favorite exist =
                favoriteRepository.findByUsernameAndPlaceId(username, placeId);

        if (exist != null) {
            favoriteRepository.delete(exist);
            return "즐겨찾기 해제";
        }

        Favorite favorite = new Favorite();
        favorite.setUsername(username);
        favorite.setPlaceId(placeId);

        favoriteRepository.save(favorite);

        return "즐겨찾기 추가";
    }

    // =========================
    // 내 즐겨찾기 목록
    // =========================
    @GetMapping
    public List<Long> getMy(HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return List.of();
        }

        return favoriteRepository.findByUsername(user.getUsername().trim())
                .stream()
                .map(Favorite::getPlaceId)
                .toList();
    }
}