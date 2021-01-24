package hranj.marijan.springbootapp.dto;

import hranj.marijan.springbootapp.validators.EqualPasswords;
import hranj.marijan.springbootapp.validators.StrongPassword;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@EqualPasswords(message = "The passwords must match each other")
public class UserDto {

    @Size(min = 4, message = "The username must be at least 4 characters long")
    private String username;

    @StrongPassword(message = "The password must have at least 6 characters, 1 number and 1 special character")
    private String password;

    private String repeatedPassword;

    private boolean approved;

}
