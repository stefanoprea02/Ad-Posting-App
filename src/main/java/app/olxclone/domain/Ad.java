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

    public void setImages(Byte[] image1, Byte[] image2,Byte[] image3, Byte[] image4,Byte[] image5,
                          Byte[] image6,Byte[] image7, Byte[] image8) {
        if(image1 != null)
            this.images[0] = image1;
        if(image2 != null)
            this.images[1] = image2;
        if(image3 != null)
            this.images[2] = image3;
        if(image4 != null)
            this.images[3] = image4;
        if(image5 != null)
            this.images[4] = image5;
        if(image6 != null)
            this.images[5] = image6;
        if(image7 != null)
            this.images[6] = image7;
        if(image8 != null)
            this.images[7] = image8;
    }
}
