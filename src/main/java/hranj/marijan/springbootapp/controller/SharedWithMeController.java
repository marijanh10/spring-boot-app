package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SharedWithMeController {

    @Autowired
    private UserService userService;

    @GetMapping("/shared-with-me")
    private String sharedWithMe(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findApprovedUser(authentication.getName()));
        return "shared_with_me";
    }

}
