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
    // 장소 상세 페이지
    // =========================
    @GetMapping("/place/{id}")
    public String place(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {

        Place place = placeService.findById(id);

        model.addAttribute("place", place);

        // 🔥 중요: JS fetch로만 쓰면 이건 없어도 됨 (혼선 방지)
        model.addAttribute("loginUser", session.getAttribute("user"));

        return "place";
    }
}