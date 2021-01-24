package hranj.marijan.springbootapp.repository;

import hranj.marijan.springbootapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByApprovedTrue();

    User findByUsernameAndApprovedTrue(String username);

    List<User> findAllByUsernameNotAndApprovedTrue(String username);

    User findByUsername(String username);

}
