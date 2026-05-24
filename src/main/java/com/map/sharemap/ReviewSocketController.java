package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewSocketController {

    private final SimpMessagingTemplate template;
    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;

    // =========================
    // 리뷰 추가
    // =========================
    @MessageMapping("/review/add")
    public void add(ReviewDto dto) {

        Place place = placeService.findById(dto.getPlaceId());

        Review review = new Review();
        review.setStar(dto.getStar());
        review.setContent(dto.getContent());
        review.setUsername(dto.getUsername());
        review.setNickname(dto.getNickname());
        review.setPlace(place);

        reviewRepository.save(review);

        broadcast(place.getId());
    }

    // =========================
    // 리뷰 수정
    // =========================
    @MessageMapping("/review/edit")
    public void edit(ReviewDto dto) {

        Review review = reviewRepository.findById(dto.getId())
                .orElseThrow();

        review.setStar(dto.getStar());
        review.setContent(dto.getContent());

        reviewRepository.save(review);

        broadcast(review.getPlace().getId());
    }

    // =========================
    // 리뷰 삭제
    // =========================
    @MessageMapping("/review/delete")
    public void delete(ReviewDto dto) {

        Review review = reviewRepository.findById(dto.getId())
                .orElseThrow();

        Long placeId = review.getPlace().getId();

        reviewRepository.delete(review);

        broadcast(placeId);
    }

    // =========================
    // 공통 브로드캐스트
    // =========================
    private void broadcast(Long placeId) {

        List<Review> reviews = reviewRepository.findByPlaceId(placeId);

        double avg = reviews.stream()
                .mapToInt(Review::getStar)
                .average()
                .orElse(0);

        Place place = placeService.findById(placeId);
        place.setRating(Math.round(avg * 10.0) / 10.0);
        placeService.save(place);

        template.convertAndSend("/topic/review/" + placeId, reviews);
    }
}