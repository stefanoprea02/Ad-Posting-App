package app.olxclone.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(String type, Long id, MultipartFile file);
}
