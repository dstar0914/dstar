package kr.side.dstar.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WebController {

    @GetMapping("/")
    public String main() {
        return "/main.html";
    }
}
