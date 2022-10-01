package app.olxclone.controllers;

import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import app.olxclone.services.AdService;
import app.olxclone.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ImageController {
    private final AdService adService;
    private final CategoryService categoryService;

    public ImageController(AdService adService, CategoryService categoryService){
        this.adService = adService;
        this.categoryService = categoryService;
    }
    /*
    @GetMapping("/{description}/image")
    public void renderCategoryImage(@PathVariable String description, HttpServletResponse response) throws IOException{
        Category category = categoryService.findByDescription(description).block();

        if(category != null){
            byte[] image = category.getImage();
            if(image != null){
                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(image);
                IOUtils.copy(is, response.getOutputStream());
            }
        }else{
            System.out.println("NUU");
        }
    }

    @GetMapping("/{title}/firstImage")
    public void renderFirstImage(@PathVariable String title, HttpServletResponse response) throws IOException{
        Ad ad = adService.findByTitle(title).block();

        if(ad.getImages() != null) {
            byte[][] images = ad.getImages();
            byte[] firstImage = {};

            for(byte[] image : images){
                if(image != null && image.length != 0){
                    firstImage = image;
                    break;
                }
            }

            if(firstImage != null){
                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(firstImage);
                IOUtils.copy(is, response.getOutputStream());
            }
        }
    }

     */
}
