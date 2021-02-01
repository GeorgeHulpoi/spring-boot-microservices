package ac.pos.restmariadb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "books"})
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable=false, length=256)
    private String name;

    @Column(name="release_year", nullable=false)
    private Short releaseYear;

    @Column(name="genre", nullable=false, length=128)
    private String genre;

    @Column(name="approved_by", nullable=false, length=256)
    private String approvedBy;

    @Column(name="pages", nullable=false)
    private Short pages;

    @Column(name="summary", nullable=false, length=256, columnDefinition = "MEDIUMTEXT")
    @Type(type = "org.hibernate.type.TextType")
    private String summary;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(
        name = "books_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    Set<Author> authors;

    public Book(String name, Short releaseYear, String genre, String approvedBy, Short pages, String summary) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.approvedBy = approvedBy;
        this.pages = pages;
        this.summary = summary;
    }
}
