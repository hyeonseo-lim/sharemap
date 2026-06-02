package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewSocketController {

    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;
    private final SimpMessagingTemplate template;

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

    @MessageMapping("/review/edit")
    public void edit(ReviewDto dto) {

        Review review = reviewRepository.findById(dto.getId()).orElseThrow();

        review.setStar(dto.getStar());
        review.setContent(dto.getContent());

        reviewRepository.save(review);

        broadcast(review.getPlace().getId());
    }

    @MessageMapping("/review/delete")
    public void delete(ReviewDto dto) {

        Review review = reviewRepository.findById(dto.getId()).orElseThrow();

        Long placeId = review.getPlace().getId();

        reviewRepository.delete(review);

        broadcast(placeId);
    }

    private void broadcast(Long placeId) {

        List<Review> list = reviewRepository.findByPlaceId(placeId);

        double avg = list.stream()
                .mapToInt(Review::getStar)
                .average()
                .orElse(0);

        Place place = placeService.findById(placeId);
        place.setRating(Math.round(avg * 10.0) / 10.0);
        placeService.save(place);

        template.convertAndSend("/topic/review/" + placeId, list);
        template.convertAndSend("/topic/rating/" + placeId, place.getRating());
    }
}