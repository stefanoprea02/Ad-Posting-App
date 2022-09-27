package app.olxclone.converters;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import com.mongodb.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;

public class AdCommandToAd implements Converter<AdCommand, Ad> {
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
        ad.setImages(source.getImages());
        ad.setCategory(source.getCategory());
        ad.setContact_info(source.getContact_info());
        ad.setLocation(source.getLocation());
        ad.setPhone_number(source.getPhone_number());
        ad.setDescription(source.getDescription());

        return ad;
    }
}
