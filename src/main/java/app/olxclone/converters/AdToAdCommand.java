package app.olxclone.converters;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import app.olxclone.domain.Category;
import com.mongodb.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;

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
        adCommand.setImages(source.getImages());
        adCommand.setCategory(source.getCategory());
        adCommand.setContact_info(source.getContact_info());
        adCommand.setLocation(source.getLocation());
        adCommand.setPhone_number(source.getPhone_number());
        adCommand.setDescription(source.getDescription());

        return adCommand;
    }
}
