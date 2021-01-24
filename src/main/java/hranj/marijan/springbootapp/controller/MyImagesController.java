package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.model.Image;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.ImageTransferService;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class MyImagesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageTransferService imageTransferService;

    @GetMapping("/show-my-images")
    private String myImages(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findApprovedUser(authentication.getName()));
        return "my_images";
    }

    @PostMapping("/upload-image")
    private String uploadImage(@RequestParam List<MultipartFile> images, Authentication authentication) {
        User user = userService.findApprovedUser(authentication.getName());
        imageTransferService.saveImages(images)
                .stream()
                .map(path -> new Image(path, user))
                .forEach(user::addMyImage);
        userService.saveUser(user);
        return "redirect:/show-my-images";
    }

}
