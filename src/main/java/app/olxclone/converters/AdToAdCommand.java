package app.olxclone.converters;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import com.mongodb.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdToAdCommand implements Converter<Ad, AdCommand> {
    @Synchronized
    @Nullable
    @Override
    public AdCommand convert(Ad source) {
        if(source == null){
            return null;
        }

        final AdCommand adCommand = new AdCommand();
        adCommand.setId(source.getId());
        adCommand.setTitle(source.getTitle());
        adCommand.setImage1(source.getImages()[0]);
        adCommand.setImage2(source.getImages()[1]);
        adCommand.setImage3(source.getImages()[2]);
        adCommand.setImage4(source.getImages()[3]);
        adCommand.setImage5(source.getImages()[4]);
        adCommand.setImage6(source.getImages()[5]);
        adCommand.setImage7(source.getImages()[6]);
        adCommand.setImage8(source.getImages()[7]);
        adCommand.setCategoryId(source.getCategory().getId());
        adCommand.setContact_info(source.getContact_info());
        adCommand.setLocation(source.getLocation());
        adCommand.setPhone_number(source.getPhone_number());
        adCommand.setDescription(source.getDescription());

        return adCommand;
    }
}
