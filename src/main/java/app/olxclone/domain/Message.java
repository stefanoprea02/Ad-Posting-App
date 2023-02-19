package app.olxclone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Message {
    @Id
    @Field("_id")
    private String id = UUID.randomUUID().toString();
    @NotBlank(message = "Must not be blank")
    private String sender;
    @NotBlank(message = "Must not be blank")
    private String receiver;
    private LocalDateTime date = LocalDateTime.now();
    @NotBlank(message = "Must not be blank")
    private String content;
    private Boolean isSent = false;
}