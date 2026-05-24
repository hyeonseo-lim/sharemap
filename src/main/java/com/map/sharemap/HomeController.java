package com.map.sharemap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PlaceService placeService;

    @GetMapping("/")
    public String home(Model model) {
        List<Place> places = placeService.findAll();
        model.addAttribute("places", places);
        return "index";
    }
}
