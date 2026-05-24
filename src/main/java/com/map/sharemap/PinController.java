/*
 * 충북대 공유지도 - 핀 컨트롤러 클래스
 * 작성자: 장래정
 * 설명: 핀(Pin) 관련 API를 처리하는 컨트롤러 클래스
 * @GetMapping: 모든 핀 정보를 반환하는 API 엔드포인트
 * @PostMapping: 새로운 핀을 추가하는 API 엔드포인트
 * @DeleteMapping: 특정 핀을 삭제하는 API 엔드포인트
 * @CrossOrigin: CORS 설정을 통해 모든 출처에서의 요청을 허용
 * @RequiredArgsConstructor: Lombok 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성하여 의존성 주입을 간편하게 처리
 * PinRepository를 주입받아 데이터베이스와의 상호작용을 처리
 * 각 API 엔드포인트는 HTTP 요청을 처리하고, 핀 데이터를 반환하거나 수정하는 역할을 수행
 * 예시:
 * GET /api/pins: 모든 핀 정보를 JSON 형식으로 반환
 * POST /api/pins: 요청 본문에 포함된 핀 정보를 데이터베이스에 저장하고, 저장된 핀 정보를 반환
 * DELETE /api/pins/{id}: URL 경로에 포함된 핀 ID를 사용하여 해당 핀을 데이터베이스에서 삭제
 * CORS 설정을 통해 프론트엔드 애플리케이션이 다른 도메인에서 API에 접근할 수 있도록 허용
 * Lombok의 @RequiredArgsConstructor를 사용하여 PinRepository에 대한 생성자를 자동으로 생성하여 코드 간결성 향상
 * 각 API 엔드포인트는 핀 데이터를 처리하는 로직을 포함하여, 핀 정보를 효율적으로 관리할 수 있도록 설계
 */

package com.map.sharemap;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/*import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
*/

@RestController
@RequestMapping("/api/pins")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PinController {

    private final PinRepository pinRepository;

    // 1. 현재 로그인한 유저의 핀들만 조회
    @GetMapping
    public ResponseEntity<?> getPins(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String username = user.getUsername();

        // 전체 조회가 아닌, 로그인한 유저의 아이디로 필터링된 핀 목록 리턴
        List<Pin> userPins = pinRepository.findByUsername(username);
        return ResponseEntity.ok(userPins);
    }

    // 2. 새로운 핀을 저장할 때 현재 로그인한 유저의 아이디를 함께 저장
    @PostMapping
    public ResponseEntity<?> addPin(@RequestBody Pin pin, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        pin.setUsername(user.getUsername());

        Pin savedPin = pinRepository.save(pin);
        return ResponseEntity.ok(savedPin);
    }

    // 3. 핀 삭제 (선택 사항: 본인 핀인지 확인 후 삭제하도록 보완 가능)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePin(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        pinRepository.findById(id).ifPresent(pin -> {
            if (pin.getUsername().equals(username)) {
                pinRepository.deleteById(id);
            }
        });

        return ResponseEntity.ok().build();
    }
}