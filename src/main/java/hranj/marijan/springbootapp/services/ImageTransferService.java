package hranj.marijan.springbootapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageTransferService {

    String saveImage(MultipartFile image);

    List<String> saveImages(List<MultipartFile> images);

}
