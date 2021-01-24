package hranj.marijan.springbootapp.exeptions;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {

    public UserAlreadyExistsException(String explanation) {
        super(explanation);
    }

}
