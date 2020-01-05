package pl.psk.salesManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String ShowHomePage() {
        return "home";
    }

    @GetMapping("/")
    public String Redirect() {
        return "home";
    }
}