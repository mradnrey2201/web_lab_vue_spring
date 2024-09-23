package springvuesite.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @RequestMapping(value = "/**/{[path:[^\\\\.]*}")
    public String redirectApi() {
        return "forward:/";
    }
}
