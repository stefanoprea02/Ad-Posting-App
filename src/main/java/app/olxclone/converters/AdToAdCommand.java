package app.olxclone.converters;

import app.olxclone.commands.AdCommand;
import app.olxclone.domain.Ad;
import com.mongodb.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class AdToAdCommand implements Converter<Ad, AdCommand> {

    private byte[] convertByteArrayToFilePart(FilePart file) throws IOException {
        File convFile = new File(file.filename());
        file.transferTo(convFile);
        return Files.readAllBytes(convFile.toPath());
    }

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
        adCommand.setCategoryId(source.getCategory());
        adCommand.setContact_info(source.getContact_info());
        adCommand.setLocation(source.getLocation());
        adCommand.setPhone_number(source.getPhone_number());
        adCommand.setDescription(source.getDescription());
        adCommand.setPrice(source.getPrice());
        adCommand.setState(source.getState());
        adCommand.setNegotiable(source.getNegotiable());

        return adCommand;
    }
}
