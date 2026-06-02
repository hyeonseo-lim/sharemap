package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {

    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;

    // =========================
    // 1. 기존 리뷰 DB 조회 (필수)
    // =========================
    @GetMapping("/{placeId}")
    public List<Review> getReviews(@PathVariable Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    // =========================
    // 2. 리뷰 작성 (REST 방식 - fallback용)
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

        // 로그인 체크
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

        // 별점 업데이트
        updatePlaceRating(placeId);

        res.put("id", review.getId());
        res.put("star", review.getStar());
        res.put("content", review.getContent());
        res.put("nickname", review.getNickname());
        res.put("username", review.getUsername());

        return res;
    }

    // =========================
    // 3. 별점 계산 (공통 로직)
    // =========================
    private void updatePlaceRating(Long placeId) {

        Place place = placeService.findById(placeId);

        double avg = reviewRepository.findByPlaceId(placeId)
                .stream()
                .mapToInt(Review::getStar)
                .average()
                .orElse(0);

        place.setRating(Math.round(avg * 10.0) / 10.0);

        placeService.save(place);
    }
}