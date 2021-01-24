package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.dto.OtpDto;
import hranj.marijan.springbootapp.model.PhoneNumber;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Controller
public class VerifyOtpController {

    @Value("#{new Integer('${hranj.marijan.springbootapp.otp-valid-minutes}')}")
    private Integer otpValidMinutes;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    private String verify(Model model) {
        addModelAttributes(model, new OtpDto(), false, true);
        return "verify";
    }

    @PostMapping("/verify")
    private String verifyPost(@Valid OtpDto otpDto, BindingResult bindingResult, Model model,
                              @SessionAttribute(value = "username", required = false) String username,
                              @SessionAttribute(value = "phoneNumber", required = false) String phoneNumber) {
        if (username == null || phoneNumber == null) {
            return "redirect:/registration";
        }
        if (bindingResult.hasErrors()) {
            addModelAttributes(model, otpDto, false, true);
            return "verify";
        }
        User user = userService.findUser(username);
        PhoneNumber number = getNumberFromUser(phoneNumber, user);
        if (isActiveOtp(number)) {
            if (Arrays.equals(number.getOtp(), otpDto.getOtp().toCharArray())) {
                user.setApproved(true);
                number.setApproved(true);
                userService.saveUser(user);
                return "redirect:/new-phone-number-form";
            }
            otpDto.increaseTriesCounter();
            addModelAttributes(model, otpDto, true, true);
            return "verify";
        }
        addModelAttributes(model, otpDto, false, false);
        return "verify";
    }

    private void addModelAttributes(Model model, OtpDto otpDto, boolean wrongOtp, boolean activeOtp) {
        model.addAttribute("otpDto", otpDto);
        model.addAttribute("wrongOtp", wrongOtp);
        model.addAttribute("activeOtp", activeOtp);
    }

    private PhoneNumber getNumberFromUser(String phoneNumber, User user) {
        return user.getPhoneNumbers()
                .stream()
                .filter(number -> phoneNumber.equals(number.getNumber()))
                .findFirst()
                .orElse(null);
    }

    private boolean isActiveOtp(PhoneNumber number) {
        return System.currentTimeMillis() < number.getTimeOtpAdded().getTime() + TimeUnit.MINUTES.toMillis(otpValidMinutes);
    }

}
