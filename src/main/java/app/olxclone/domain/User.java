package app.olxclone.domain;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    @Id
    private String Id = UUID.randomUUID().toString();
    private String username;
    private String password;
    private String email;
    private Set<Ad> ads = new HashSet<>();
}
