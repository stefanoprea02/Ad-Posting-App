package app.olxclone.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredentialsRequest {
    private String username;
    private String password;
}
