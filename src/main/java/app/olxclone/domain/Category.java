package app.olxclone.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Document
public class Category {
    @Id
    private String Id = UUID.randomUUID().toString();
    private String description;
    private Byte[] image;

    public String getDescriptionToUrl(){
        String newDescription = description.toLowerCase();

        newDescription = newDescription.replace(" ", "-");
        newDescription = newDescription.replace(",", "");

        return newDescription;
    }
}
