package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.model.Image;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.ImageService;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class ImageDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/image-details/{imageId}")
    private String myImages(@PathVariable int imageId, Model model, Authentication authentication) {
        imageService.findById(imageId)
                .ifPresent(img -> {
                    model.addAttribute("image", img);
                    model.addAttribute("usersWhoDontHavePermissionOnImage", getUsersWhoDontHavePermissionOnImage(img, authentication.getName()));
                });

        return "image_details";
    }

    @PostMapping("/share-image-with-user")
    private String shareImage(@RequestParam String username, @RequestParam int imageId) {
        User user = userService.findApprovedUser(username);
        Optional<Image> image = imageService.findById(imageId);
        if (image.isPresent()) {
            user.addImageSharedWithMe(image.get());
            userService.saveUser(user);
        }

        return "redirect:/image-details/" + imageId;
    }

    @PostMapping("/remove-image-permission-from-user")
    private String deletePermission(@RequestParam String username, @RequestParam int imageId) {
        User user = userService.findApprovedUser(username);
        Optional<Image> image = imageService.findById(imageId);
        if (image.isPresent()) {
            user.removeImageSharedWithMe(image.get());
            userService.saveUser(user);
        }

        return "redirect:/image-details/" + imageId;
    }

    @PostMapping("/delete-image")
    private String deleteImage(@RequestParam int imageId) {
        Optional<Image> image = imageService.findById(imageId);
        if (image.isPresent()) {
            User user = image.get().getUser();
            user.removeMyImage(image.get());
            userService.saveUser(user);
            image.get().getUsersWithPermissionToSeeImage()
                    .forEach(usr -> removeSharedImageFromUser(usr, image.get()));
            imageService.deleteImage(image.get());
        }
        return "redirect:/show-my-images";
    }

    private void removeSharedImageFromUser(User user, Image image) {
        user.removeImageSharedWithMe(image);
        userService.saveUser(user);
    }

    private List<User> getUsersWhoDontHavePermissionOnImage(Image image, String currentUser) {
        List<User> allUsers = userService.findAllApprovedUsersExceptUser(currentUser);
        allUsers.removeAll(image.getUsersWithPermissionToSeeImage());
        return allUsers;
    }

}
