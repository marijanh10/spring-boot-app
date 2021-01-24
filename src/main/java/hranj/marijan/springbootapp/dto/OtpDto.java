package hranj.marijan.springbootapp.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class OtpDto {

    @Pattern(regexp = "^[0-9]*", message = "You have to specify a valid number")
    private String otp;

    @Max(value = 0, message = "You exceeded the maximum number of tries")
    private int triesCounter;

    public void increaseTriesCounter() {
        triesCounter++;
    }

}
