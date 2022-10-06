package app.olxclone.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Ad {
    @Id
    @Field("_id")
    private String id = UUID.randomUUID().toString();
    @NotBlank(message = "Must not be blank")
    @Size(min = 3, max = 255, message = "Must be between 3 and 255 characters long")
    private String title;
    @NotBlank(message = "Must not be blank")
    private String categoryId;
    private String username;
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
    private LocalDate date = LocalDate.now();
}
