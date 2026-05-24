package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    // 회원 목록
    @GetMapping("/admin")
    public String adminPage(Model model) {

        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        return "admin";
    }

    // 회원 삭제
    @PostMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);

        return "redirect:/admin";
    }
}