package app.olxclone.services;

import app.olxclone.domain.Category;
import app.olxclone.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{
    private final CategoryRepository categoryRepository;

    public ImageServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(String type, Long id, MultipartFile file) {
        try{
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            if(type == "category"){
                Category item = categoryRepository.findById(String.valueOf(id)).block();

                item.setImage(byteObjects);

                categoryRepository.save(item).block();
            }else if(type == "ad"){

            }else{
                return;
            }
        }catch (IOException e){
            log.error("Error ocurred" , e);

            e.printStackTrace();
        }
    }
}
