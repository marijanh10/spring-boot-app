package hranj.marijan.springbootapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicPathsController {

    @GetMapping("/")
    private String home() {
        return "home";
    }

    @GetMapping("/signin")
    private String login() {
        return "signin";
    }

    @GetMapping("/new-phone-number-form")
    private String newPhoneNumber() {
        return "another_phone_number_form";
    }

}
