package app.olxclone.controllers;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import app.olxclone.services.AdService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ImageController {
    private final AdService adService;

    public ImageController(AdService adService){
        this.adService = adService;
    }

    @GetMapping("/{adId}/firstImage")
    public void renderFirstImage(@PathVariable String adId, HttpServletResponse response) throws IOException{
        List<Ad> ads = adService.getAds().collectList().block();
        Ad correctAd = new Ad();
        for(Ad ad : ads){
            if (ad.getId().equals(adId))
                correctAd = ad;
        }

        if(correctAd.getImages() != null) {
            Byte[][] images = correctAd.getImages();
            Byte[] firstImage = {};

            for(Byte[] image : images){
                if(image != null && image.length != 0){
                    firstImage = image;
                    break;
                }
            }

            if(firstImage != null){
                byte[] byteArray = new byte[firstImage.length];

                int i = 0;

                for (Byte wrappedByte : firstImage) {
                    byteArray[i++] = wrappedByte;
                }

                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(byteArray);
                IOUtils.copy(is, response.getOutputStream());
            }
        }
    }
}
