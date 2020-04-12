package kr.side.dstar.web;

import kr.side.dstar.service.scrap.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WebController {
    private final ScrapService scrapService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("scrap", scrapService.findAllDesc());

        return "main";
    }
}
