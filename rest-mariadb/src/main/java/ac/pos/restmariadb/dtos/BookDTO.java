package ac.pos.restmariadb.dtos;

import ac.pos.restmariadb.models.Author;
import ac.pos.restmariadb.models.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String name;
    private Short releaseYear;
    private String genre;
    private String approvedBy;
    private Short pages;
    private String summary;
    private List<AuthorDTO> authors;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.releaseYear = book.getReleaseYear();
        this.genre = book.getGenre();
        this.approvedBy = book.getApprovedBy();
        this.pages = book.getPages();
        this.summary = book.getSummary();
        if (book.getAuthors() != null) {
            this.authors = book.getAuthors().stream().map(AuthorDTO::new).collect(Collectors.toList());
        } else {
            this.authors = new ArrayList<>();
        } 
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class AuthorDTO {
        private Long id;
        private String name;

        public AuthorDTO(Author author) {
            this.id = author.getId();
            this.name = author.getName();
        }
    }
}
