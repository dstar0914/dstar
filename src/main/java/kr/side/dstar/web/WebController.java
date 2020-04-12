package kr.side.dstar.web;

import kr.side.dstar.config.auth.dto.SessionUser;
import kr.side.dstar.service.scrap.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class WebController {
    private final ScrapService scrapService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("scrap", scrapService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user!=null) {
            model.addAttribute("userName", user.getName());
        }

        return "main";
    }
}
