package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {

    private final PlaceService placeService;
    private final ReviewRepository reviewRepository;

    // =========================
    // 리뷰 작성 (실시간)
    // =========================
    @PostMapping("/{placeId}")
    public Map<String, Object> addReview(
            @PathVariable Long placeId,
            @RequestParam Integer star,
            @RequestParam String content,
            HttpSession session
    ) {

        Map<String, Object> res = new HashMap<>();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            res.put("error", "login required");
            return res;
        }

        Place place = placeService.findById(placeId);

        Review review = new Review();
        review.setStar(star);
        review.setContent(content);
        review.setUsername(user.getUsername());
        review.setNickname(user.getNickname());
        review.setPlace(place);

        reviewRepository.save(review);

        updatePlaceRating(placeId);

        res.put("id", review.getId());
        res.put("star", review.getStar());
        res.put("content", review.getContent());
        res.put("nickname", review.getNickname());

        return res;
    }

    // =========================
    // 공통: 별점 계산
    // =========================
    private void updatePlaceRating(Long placeId) {

        Place place = placeService.findById(placeId);

        double avg = reviewRepository.findByPlaceId(placeId)
                .stream()
                .mapToInt(Review::getStar)
                .average()
                .orElse(0);

        place.setRating(Math.round(avg * 10) / 10.0);

        placeService.save(place);
    }
}