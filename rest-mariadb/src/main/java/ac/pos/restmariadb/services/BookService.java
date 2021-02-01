package ac.pos.restmariadb.services;

import ac.pos.restmariadb.dtos.BookDTO;
import ac.pos.restmariadb.models.Author;
import ac.pos.restmariadb.models.Book;
import ac.pos.restmariadb.repositories.AuthorRepository;
import ac.pos.restmariadb.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public ResponseEntity<Object> GetAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        List<BookDTO> result = StreamSupport.stream(books.spliterator(), false)
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> GetBookById(Long id) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            return new ResponseEntity<>(new BookDTO(bookData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> GetBooksByName(String name)  {
        List<BookDTO> result = bookRepository.findByName(name)
                .stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> GetBooksByGenre(String genre)  {
        List<BookDTO> result = bookRepository.findByGenre(genre)
                .stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> GetBooksByReleaseYear(Short year)  {
        List<BookDTO> result = bookRepository.findByReleaseYear(year)
                .stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> UpdateBookById(Long id, Book book) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            Book realBook = bookData.get();

            if (book.getName() != null) {
                realBook.setName(book.getName());
            }

            if (book.getReleaseYear() != null) {
                realBook.setReleaseYear(book.getReleaseYear());
            }

            if (book.getGenre() != null) {
                realBook.setGenre(book.getGenre());
            }

            if (book.getApprovedBy() != null) {
                realBook.setApprovedBy(book.getApprovedBy());
            }

            if (book.getPages() != null) {
                realBook.setPages(book.getPages());
            }

            if (book.getSummary() != null) {
                realBook.setSummary(book.getSummary());
            }

            if (book.getAuthors() != null) {
                realBook.setAuthors(book.getAuthors().stream().map(author -> {
                    Optional<Author> dbAuthor = Optional.empty();

                    if (author.getName() != null) {
                        List<Author> list = authorRepository.findByName(author.getName());
                        if (!list.isEmpty())
                        {
                            dbAuthor = Optional.of(list.get(0));
                        }
                    } else {
                        dbAuthor = authorRepository.findById(author.getId());
                    }

                    return dbAuthor.orElse(author);
                }).collect(Collectors.toSet()));
            }
            return new ResponseEntity<>(new BookDTO(bookRepository.save(realBook)), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> CreateBook(Book book) {
        Set<Author> authors = book.getAuthors();

        if (authors != null) {
            book.setAuthors(authors.stream().map(author -> {
                Optional<Author> dbAuthor = Optional.empty();

                if (author.getName() != null) {
                    List<Author> list = authorRepository.findByName(author.getName());
                    if (!list.isEmpty()) {
                        dbAuthor = Optional.of(list.get(0));
                    }
                } else {
                    dbAuthor = authorRepository.findById(author.getId());
                }

                return dbAuthor.orElse(author);
            }).collect(Collectors.toSet()));
        }

        BookDTO created = new BookDTO(bookRepository.save(book));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> DeleteBookById(Long id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
