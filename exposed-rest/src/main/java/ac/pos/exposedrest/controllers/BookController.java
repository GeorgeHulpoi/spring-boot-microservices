package ac.pos.exposedrest.controllers;

import ac.pos.exposedrest.services.BookService;
import ac.pos.exposedrest.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/book")
public class BookController {

    @Autowired
    private EditorService editorService;

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<?> GetAllBooks(RequestEntity<String> request) {
        return bookService.GetAllBooks(request);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> GetBookById(RequestEntity<String> request,
                                              @PathVariable("id") Long id) {
        return bookService.GetBookById(request, id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> GetBooksByName(RequestEntity<String> request,
                                            @PathVariable("name") String name) {
        return bookService.GetBooksByName(request, name);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<?> GetBooksByGenre(RequestEntity<String> request,
                                             @PathVariable("genre") String genre) {
        return bookService.GetBooksByGenre(request, genre);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<?> GetBooksByGenre(RequestEntity<String> request,
                                             @PathVariable("year") Short year) {
        return bookService.GetBooksByReleaseYear(request, year);
    }

    @PostMapping("")
    public ResponseEntity<?> CreateBook(@RequestBody Map<String, Object> data,
                                        @RequestHeader("Authorization") String authorization,
                                        @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return editorService.CreateBook(data, authorization, cookie);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> UpdateBookById(@RequestBody Map<String, Object> data,
                                            @PathVariable("id") Long id,
                                            @RequestHeader("Authorization") String authorization,
                                            @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return editorService.UpdateBookById(data, id, authorization, cookie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> DeleteBookById(@PathVariable("id") Long id,
                                            @RequestHeader("Authorization") String authorization,
                                            @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return editorService.DeleteBookById(id, authorization, cookie);
    }
}
