package am.itspace.authorbookrest.dto;

import am.itspace.authorbookrest.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private int id;
    private String title;
    private double price;
    private int qty;
    private Author author;
}
