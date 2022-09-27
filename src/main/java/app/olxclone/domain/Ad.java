package app.olxclone.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document
public class Ad {
    @Id
    private String Id;
    private String title;
    private Category category;
    private Byte[][] images;
    private String description;
    private String location;
    private String contact_info;
    private String phone_number;
}
