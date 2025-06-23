package am.itspace.authorbookrest.dto;

import am.itspace.authorbookrest.entity.Author;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveBookRequest {

        private int id;
        private String title;
        private double price;
        private int qty;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createdAt;
        private Author author;

}