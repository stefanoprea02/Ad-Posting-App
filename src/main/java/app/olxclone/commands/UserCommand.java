package app.olxclone.commands;

import app.olxclone.domain.Ad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {
    @Id
    private String id = UUID.randomUUID().toString();
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 20)
    private String password;
    @NotBlank
    @Email
    @Size(max = 40)
    private String email;
    private Set<Ad> ads = new HashSet<>();
}
