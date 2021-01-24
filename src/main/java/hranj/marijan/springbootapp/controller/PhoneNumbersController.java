package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.dto.PhoneNumberDto;
import hranj.marijan.springbootapp.model.PhoneNumber;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.SmsSenderService;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes("phoneNumber")
public class PhoneNumbersController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsSenderService smsSenderService;

    @GetMapping("/phone-numbers")
    private String phoneNumbers(Model model) {
        model.addAttribute("phoneNumberDto", new PhoneNumberDto());
        model.addAttribute("smsError", false);
        return "phone_numbers";
    }

    @PostMapping("/add-phone-number")
    private String addPhoneNumber(@Valid PhoneNumberDto phoneNumberDto,
                                  BindingResult bindingResult, Model model,
                                  @SessionAttribute(value = "username", required = false) String username) {
        if (username == null) {
            return "redirect:/registration";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("phoneNumberDto", phoneNumberDto);
            model.addAttribute("smsError", false);
            return "phone_numbers";
        }
        User user = userService.findUser(username);
        PhoneNumber phoneNumber = new PhoneNumber(phoneNumberDto, user);
        boolean smsSentSuccessfully = smsSenderService
                .sendSms("My Spring Boot App", phoneNumber.getNumber(), phoneNumber.getOtp());
        if (!smsSentSuccessfully) {
            model.addAttribute("phoneNumberDto", phoneNumberDto);
            model.addAttribute("smsError", true);
            return "phone_numbers";
        }
        user.addPhoneNumber(phoneNumber);
        userService.saveUser(user);
        model.addAttribute("phoneNumber", phoneNumberDto.getCountryCode() + phoneNumberDto.getPhoneNumber());
        return "redirect:/verify";
    }

}
