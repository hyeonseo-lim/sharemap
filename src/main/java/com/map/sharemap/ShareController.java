package com.map.sharemap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
public class ShareController {

    private final FavoriteRepository favoriteRepository;

    @GetMapping("/my")
    public Map<String, Object> myUrl(HttpSession session) {

        User user = (User) session.getAttribute("user");

        Map<String, Object> res = new HashMap<>();

        if (user == null) {
            res.put("message", "로그인이 필요합니다");
            return res;
        }

        String key = user.getUsername();

        res.put("shareKey", key);
        res.put("url", "/share/" + key);

        return res;
    }

    @GetMapping("/{key}")
    public Map<String, Object> getShare(@PathVariable String key) {

        Map<String, Object> res = new HashMap<>();

        List<Long> ids = favoriteRepository.findByUsername(key)
                .stream()
                .map(Favorite::getPlaceId)
                .toList();

        // 🔥 여기 중요: null도 방어
        if (ids == null || ids.isEmpty()) {
            res.put("placeIds", List.of());
            res.put("message", "즐겨찾기 없음");
            return res;
        }

        res.put("placeIds", ids);
        return res;
    }
}