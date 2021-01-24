package hranj.marijan.springbootapp.services.impl;

import hranj.marijan.springbootapp.model.Image;
import hranj.marijan.springbootapp.repository.ImageRepository;
import hranj.marijan.springbootapp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    @Override
    public Optional<Image> findById(int id) {
        return imageRepository.findById(id);
    }

}
