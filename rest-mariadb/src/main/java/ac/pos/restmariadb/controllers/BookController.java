package ac.pos.restmariadb.controllers;

import ac.pos.restmariadb.models.Book;
import ac.pos.restmariadb.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<Object> GetAllBooks() {
        return bookService.GetAllBooks();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> GetBookById(@PathVariable("id") Long id) {
        return bookService.GetBookById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> GetBooksByName(@PathVariable("name") String name) {
        return bookService.GetBooksByName(name);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> GetBooksByGenre(@PathVariable("genre") String genre) {
        return bookService.GetBooksByGenre(genre);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<Object> GetBooksByGenre(@PathVariable("year") Short year) {
        return bookService.GetBooksByReleaseYear(year);
    }

    @PostMapping("")
    public ResponseEntity<Object> CreateBook(@RequestBody Book book) {
        return bookService.CreateBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateBookById(@PathVariable("id") Long id, @RequestBody Book book) {
        return bookService.UpdateBookById(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteBookById(@PathVariable("id") Long id) {
        return bookService.DeleteBookById(id);
    }
}
