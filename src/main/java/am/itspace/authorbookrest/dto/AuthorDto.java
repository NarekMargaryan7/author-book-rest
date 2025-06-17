package am.itspace.authorbookrest.dto;

import am.itspace.authorbookrest.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthorDto {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private Gender gender;
}
