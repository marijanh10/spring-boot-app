package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.exeptions.UserAlreadyExistsException;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("username")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    private String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("userAlreadyExists", false);
        model.addAttribute("databaseError", false);
        return "registration";
    }

    @PostMapping("/registration")
    private String registrationPost(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("userAlreadyExists", false);
            model.addAttribute("databaseError", false);
            return "registration";
        }
        try {
            userService.saveUser(userDto);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("userAlreadyExists", true);
            return "registration";
        } catch (Exception e) {
            model.addAttribute("databaseError", true);
            return "registration";
        }
        model.addAttribute("username", userDto.getUsername());
        return "redirect:/phone-numbers";
    }

}
