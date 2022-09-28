package app.olxclone.commands;

import app.olxclone.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AdCommand {
    private String id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;
    @NotBlank
    private String categoryId;
    private Byte[] image1;
    private Byte[] image2;
    private Byte[] image3;
    private Byte[] image4;
    private Byte[] image5;
    private Byte[] image6;
    private Byte[] image7;
    private Byte[] image8;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    @NotBlank
    private String location;
    @NotBlank
    @Size(min = 3, max = 255)
    private String contact_info;
    @NotBlank
    private String phone_number;
    @NotBlank
    private String price;
    private Boolean negotiable;
    private String state;

    public void setImage1(Byte[] image1) {
        if(image1 != null)
            this.image1 = image1;
    }

    public void setImage2(Byte[] image2) {
        if(image2 != null)
            this.image2 = image2;
    }

    public void setImage3(Byte[] image3) {
        if(image3 != null)
            this.image3 = image3;
    }

    public void setImage4(Byte[] image4) {
        if(image4 != null)
            this.image4 = image4;
    }

    public void setImage5(Byte[] image5) {
        if(image5 != null)
            this.image5 = image5;
    }

    public void setImage6(Byte[] image6) {
        if(image6 != null)
            this.image6 = image6;
    }

    public void setImage7(Byte[] image7) {
        if(image7 != null)
            this.image7 = image7;
    }

    public void setImage8(Byte[] image8) {
        if(image8 != null)
            this.image8 = image8;
    }
}
