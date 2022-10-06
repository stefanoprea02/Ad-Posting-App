package app.olxclone.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.data.annotation.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Authority implements GrantedAuthority {
    @Id
    @Field("_id")
    private String id = UUID.randomUUID().toString();
    private String authority;
    private String user;

    public Authority(String authority, String id){
        this.authority = authority;
        user = id;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
