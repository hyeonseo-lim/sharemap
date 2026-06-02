package com.map.sharemap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    // (선택) 폼 로그인도 지원
    // =========================
    @PostMapping("/login")
    public String loginProcess(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            return "redirect:/login?error";
        }

        session.setAttribute("user", user.get());

        return "redirect:/";
    }
}