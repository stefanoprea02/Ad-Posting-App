package app.olxclone.converters;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import app.olxclone.services.CategoryService;
import com.mongodb.lang.Nullable;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Async
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
        ad.setImages(source.getImages());
        ad.setTitle(source.getTitle());
        ad.setCategory(source.getCategoryId());
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
