package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final ReviewRepository reviewRepository;

    // =========================
    // 장소 상세 페이지 (조회만)
    // =========================
    @GetMapping("/place/{id}")
    public String place(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {

        Place place = placeService.findById(id);

        model.addAttribute("place", place);
        model.addAttribute("reviews", reviewRepository.findByPlaceId(id));
        model.addAttribute("loginUser", session.getAttribute("user"));

        return "place";
    }

    // =========================
    // 별점 업데이트 공통 메서드
    // (WebSocket Controller에서 호출용)
    // =========================
    public void updatePlaceRating(Long placeId) {

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