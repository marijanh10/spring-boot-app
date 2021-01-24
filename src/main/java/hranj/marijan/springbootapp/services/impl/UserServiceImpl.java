package hranj.marijan.springbootapp.services.impl;

import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.exeptions.UserAlreadyExistsException;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.repository.UserRepository;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllApprovedUsers() {
        return userRepository.findAllByApprovedTrue();
    }

    @Override
    public List<User> findAllApprovedUsersExceptUser(String username) {
        return userRepository.findAllByUsernameNotAndApprovedTrue(username);
    }

    @Override
    public User findApprovedUser(String username) {
        return userRepository.findByUsernameAndApprovedTrue(username);
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(UserDto userDto) throws UserAlreadyExistsException {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException("An user with the username:" + userDto.getUsername() + " already exists!");
        }
        user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
