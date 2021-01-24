package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyDetailsController {

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    private String details(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findApprovedUser(authentication.getName()));
        return "details";
    }

}
