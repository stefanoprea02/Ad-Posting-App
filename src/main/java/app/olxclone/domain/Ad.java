package app.olxclone.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Document
public class Ad {
    @Id
    private String Id = UUID.randomUUID().toString();
    private String title;
    private Category category;
    private Byte[][] images = new Byte[8][];
    private String description;
    private String location;
    private String contact_info;
    private String phone_number;
    private String price;
    private Boolean negotiable;
    private String state;
}
