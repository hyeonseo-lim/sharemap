package com.map.sharemap;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/api/list")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ListController {

    private final ListRepository listRepository;
    private final UrlMappingRepository urlMappingRepository;

    // 1. URL 키로 리스트 조회
    @GetMapping("/{urlKey}")
    public Object getList(@PathVariable String urlKey) {
        return urlMappingRepository.findByUrlKey(urlKey)
                .map(mapping -> listRepository.findById(mapping.getList().getListId())
                        .orElseThrow(() -> new RuntimeException("리스트가 존재하지 않습니다")))
                .orElse((ListEntity) Map.of("message", "잘못된 URL 키입니다"));
    }

    // 2. 아이템 추가
    @PostMapping("/{urlKey}/add")
    public Object addItem(@PathVariable String urlKey, @RequestBody Long item) {
        return urlMappingRepository.findByUrlKey(urlKey)
                .map(mapping -> {
                    ListEntity list = listRepository.findById(mapping.getList().getListId())
                            .orElseThrow(() -> new RuntimeException("리스트가 존재하지 않습니다"));
                    list.getItems().add(item);
                    listRepository.save(list);
                    return list;
                })
                .orElse((ListEntity) Map.of("message", "잘못된 URL 키입니다"));
    }

    // 3. 아이템 삭제
    @DeleteMapping("/{urlKey}/remove")
    public Object removeItem(@PathVariable String urlKey, @RequestBody String item) {
        return urlMappingRepository.findByUrlKey(urlKey)
                .map(mapping -> {
                    ListEntity list = listRepository.findById(mapping.getList().getListId())
                            .orElseThrow(() -> new RuntimeException("리스트가 존재하지 않습니다"));
                    list.getItems().remove(item);
                    listRepository.save(list);
                    return list;
                })
                .orElse((ListEntity) Map.of("message", "잘못된 URL 키입니다"));
    }

    // 4. 내 URL 확인 (로그인한 유저의 리스트 URL 반환)
    @GetMapping("/my")
    public Map<String, String> getMyListUrl(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Map.of("message", "로그인 필요");
        }

        return listRepository.findByUserId(user.getUsername())
                .map(list -> {
                    UrlMapping mapping = (UrlMapping) urlMappingRepository.findByList(list)
                            .orElseThrow(() -> new RuntimeException("URL 매핑 없음"));
                    return Map.of("listUrl", "https://myapp.com/list/" + mapping.getUrlKey());
                })
                .orElse(Map.of("message", "리스트 없음"));
    }
}
