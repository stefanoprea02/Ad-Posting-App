package app.olxclone.commands;

import app.olxclone.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AdCommand {
    private String id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;
    private Category category;
    private Byte[][] images;
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
}
