package com.map.sharemap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    // =========================
    // 로그인 페이지
    // =========================
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // =========================
    // 로그인 처리 (핵심)
    // =========================
    @PostMapping("/login")
    public String loginProcess(String username,
                               String password,
                               HttpSession session) {

        Optional<User> optionalUser =
                userRepository.findByUsername(username);

        // ❗ 유저 없음
        if (optionalUser.isEmpty()) {
            return "login";
        }

        User user = optionalUser.get();

        // ❗ 비밀번호 틀림
        if (!user.getPassword().equals(password)) {
            return "login";
        }

        // 🔥 핵심 (이게 있어야 즐겨찾기 됨)
        session.setAttribute("user", user);

        return "redirect:/";
    }
}