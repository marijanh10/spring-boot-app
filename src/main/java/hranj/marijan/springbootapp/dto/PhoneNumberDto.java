package hranj.marijan.springbootapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PhoneNumberDto {

    @Pattern(regexp = "^\\+?(\\d+)", message = "Choose a value from the dropdown")
    private String countryCode;

    @Size(min = 6, max = 10, message = "Minimum number of digits is 6, max is 10")
    @Pattern(regexp = "^[0-9]*", message = "You have to specify a valid number")
    private String phoneNumber;

}
