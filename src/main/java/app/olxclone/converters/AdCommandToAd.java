package app.olxclone.converters;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import app.olxclone.services.CategoryService;
import com.mongodb.lang.Nullable;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class AdCommandToAd implements Converter<AdCommand, Ad> {
    private final CategoryService categoryService;

    public AdCommandToAd(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Synchronized
    @Nullable
    @Override
    public Ad convert(AdCommand source) {
        if(source == null){
            return null;
        }

        final Ad ad = new Ad();
        ad.setId(source.getId());
        ad.setTitle(source.getTitle());
        ad.setImages(source.getImage1(), source.getImage2(), source.getImage3(), source.getImage4(), source.getImage5(),
                source.getImage6(), source.getImage7(), source.getImage8());
        List<Category> categories = categoryService.getCategories().collectList().block();
        for(Category cat : categories){
            String catid1 = cat.getId();
            String catid2 = source.getCategoryId();
            log.error(catid1 + "da");
            log.error(catid2 + "da");
            log.error("");
            if(catid1.equals(catid2)){
                log.error("FDSAFASFGFADGFADGD");
                ad.setCategory(cat);
                break;
            }
        }
        //ad.setCategory(categoryService.findById(source.getCategoryId()).block());
        ad.setContact_info(source.getContact_info());
        ad.setLocation(source.getLocation());
        ad.setPhone_number(source.getPhone_number());
        ad.setDescription(source.getDescription());
        ad.setPrice(source.getPrice());
        ad.setState(source.getState());
        ad.setNegotiable(source.getNegotiable());

        return ad;
    }
}
