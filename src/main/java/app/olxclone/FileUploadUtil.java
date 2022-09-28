package app.olxclone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtil {
    public static void saveFile(String uploadDir, String[] fileName, MultipartFile[] multipartFile) throws IOException{
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        int i = 0;
        for(MultipartFile file : multipartFile){
            try(InputStream inputStream = file.getInputStream()){
                if(fileName[i] != null) {
                    Path filePath = uploadPath.resolve(fileName[i++]);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }catch (IOException ioException){
                throw new IOException("Could not save image file: " + fileName, ioException);
            }
        }
    }
}
