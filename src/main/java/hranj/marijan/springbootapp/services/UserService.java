package hranj.marijan.springbootapp.services;

import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.exeptions.UserAlreadyExistsException;
import hranj.marijan.springbootapp.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllApprovedUsers();

    List<User> findAllApprovedUsersExceptUser(String username);

    User findApprovedUser(String username);

    User findUser(String username);

    void saveUser(UserDto userDto) throws UserAlreadyExistsException;

    void saveUser(User user);

}
