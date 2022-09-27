package app.olxclone.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document
public class Category {
    @Id
    private String Id;
    private String description;
    private Byte[] image;

    public String getDescriptionToUrl(){
        String newDescription = description.toLowerCase();

        newDescription = newDescription.replace(" ", "-");
        newDescription = newDescription.replace(",", "");

        return newDescription;
    }
}
