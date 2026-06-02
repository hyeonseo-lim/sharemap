package com.map.sharemap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    // =========================
    // 로그인
    // =========================
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body,
                        HttpSession session) {

        String username = body.get("username");
        String password = body.get("password");

        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> {
                    session.setAttribute("user", u);
                    return "로그인 성공";
                })
                .orElse("로그인 실패");
    }

    // =========================
    // 회원가입
    // =========================
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "이미 존재하는 아이디입니다.";
        }

        userRepository.save(user);
        return "회원가입 성공";
    }

    // =========================
    // 로그인 상태 확인
    // =========================
    @GetMapping("/me")
    public Map<String, Object> me(HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return Map.of("login", false);
        }

        return Map.of(
                "login", true,
                "username", user.getUsername(),
                "nickname", user.getNickname()
        );
    }

    // =========================
    // 로그아웃
    // =========================
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "ok";
    }
}