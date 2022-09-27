package app.olxclone.domain;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class User {
    @Id
    private String Id;
    private String username;
    private String password;
    private String email;
    private Set<Ad> ads = new HashSet<>();
}
