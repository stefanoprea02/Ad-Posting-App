package app.olxclone.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Field("_id")
    private String id = UUID.randomUUID().toString();
    @NotBlank(message = "Must not be blank")
    @Size(min = 3, max = 20, message = "Must be between 3 and 20 characters long")
    private String username;
    @NotBlank(message = "Must not be blank")
    @Size(min = 3, max = 20, message = "Must be between 3 and 20 characters long")
    private String password;
    @NotBlank(message = "Must not be blank")
    @Email(message = "Must be a valid email")
    @Size(max = 40)
    private String email;
    private Set<String> ads = new HashSet<>();
    private Set<String> favorites = new HashSet<>();
    private LocalDate date = LocalDate.now();
    private LocalDateTime lastOnline = LocalDateTime.now();
    private List<Role> roles;
    private boolean active = true;
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
