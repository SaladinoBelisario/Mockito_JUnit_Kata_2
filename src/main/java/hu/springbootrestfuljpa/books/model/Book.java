package hu.springbootrestfuljpa.books.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    private String author;

    private Integer release;

    private String title;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

}
