package ac.pos.restmariadb.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "authors", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    Set<Book> books;

    public Author(String name) {
        this.name = name;
    }
}
