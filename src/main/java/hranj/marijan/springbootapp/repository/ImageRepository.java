package hranj.marijan.springbootapp.repository;

import hranj.marijan.springbootapp.model.Image;
import hranj.marijan.springbootapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
