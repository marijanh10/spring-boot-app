package hranj.marijan.springbootapp.services.impl;

import hranj.marijan.springbootapp.services.ImageTransferService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageTransferServiceImpl implements ImageTransferService {

    private static final String RESOURCES_DIR = ImageTransferServiceImpl.class.getResource("/")
            .getPath();

    private static final String STATIC_IMAGES_PATH = "/static/images";

    @Override
    public String saveImage(MultipartFile image) {
        if (image != null) {
            String path;
            try {
                Path newFile = Paths.get(RESOURCES_DIR  + STATIC_IMAGES_PATH);
                Files.createDirectories(newFile);
                String imageName = image.getOriginalFilename().replace(" ", "_");
                String fileName = StringUtils.join(System.currentTimeMillis(), imageName);
                path = StringUtils.join(newFile.toAbsolutePath(), "/", fileName);
                Files.write(Paths.get(path), image.getBytes());
                path = "/images/" + fileName;
            } catch (IOException e) {
                log.error("IOException: while trying to store file", e);
                return StringUtils.EMPTY;
            }
            return path;
        }
        return StringUtils.EMPTY;
    }

    @Override
    public List<String> saveImages(List<MultipartFile> images) {
        return images.stream()
                .map(this::saveImage)
                .collect(Collectors.toList());
    }

}
