package app.olxclone.commands;

import app.olxclone.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;

@Getter
@Setter
@NoArgsConstructor
public class AdCommand {
    private String id;
    @NotBlank(message = "Must not be blank")
    @Size(min = 3, max = 255, message = "Must be between 3 and 255 characters long")
    private String title;
    @NotBlank(message = "Must not be blank")
    private String categoryId;
    private String[] images = new String[8];
    @NotBlank(message = "Must not be blank")
    @Size(min = 3, max = 255, message = "Must be between 3 and 255 characters long")
    private String description;
    @NotBlank(message = "Must not be blank")
    private String location;
    @NotBlank(message = "Must not be blank")
    @Size(min = 3, max = 255, message = "Must be between 3 and 255 characters long")
    private String contact_info;
    @NotBlank(message = "Must not be blank")
    private String phone_number;
    @NotBlank(message = "Must not be blank")
    @Digits(integer = 6, fraction = 0, message = "Must be a number between 0 and 1000000")
    private String price;
    private Boolean negotiable;
    private String state;
}
