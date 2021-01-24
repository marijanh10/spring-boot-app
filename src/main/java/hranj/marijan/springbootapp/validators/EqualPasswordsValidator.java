package hranj.marijan.springbootapp.validators;

import hranj.marijan.springbootapp.dto.UserDto;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, UserDto> {

    @Override
    public boolean isValid(UserDto user, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.equals(user.getPassword(), user.getRepeatedPassword());
    }

}
