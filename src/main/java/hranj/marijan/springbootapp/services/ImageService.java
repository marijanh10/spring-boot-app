package hranj.marijan.springbootapp.services;

import hranj.marijan.springbootapp.model.Image;

import java.util.Optional;

public interface ImageService {

    void saveImage(Image image);

    void deleteImage(Image image);

    Optional<Image> findById(int id);

}
