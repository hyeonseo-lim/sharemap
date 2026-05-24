package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 전체 장소 조회
    public List<Place> findAll() {

        return placeRepository.findAll();

    }

    // 장소 1개 조회
    public Place findById(Long id) {

        return placeRepository.findById(id)
                .orElseThrow();

    }

    // 장소 저장
    public void save(Place place) {

        placeRepository.save(place);

    }
}