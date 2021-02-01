package ac.pos.restmariadb.controllers;

import ac.pos.restmariadb.models.Author;
import ac.pos.restmariadb.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public ResponseEntity<Object> GetAllAuthors() {
        return authorService.GetAllAuthors();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> GetAuthorById(@PathVariable("id") Long id) {
        return authorService.GetAuthorById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> GetAuthorByName(@PathVariable("name") String name) {
        return authorService.GetAuthorsByName(name);
    }

    @PostMapping("")
    public ResponseEntity<Object> CreateAuthor(@RequestBody Author author) {
        return authorService.CreateAuthor(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateAuthorById(@PathVariable("id") Long id, @RequestBody Author author) {
        return authorService.UpdateAuthorById(id, author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteAuthorById(@PathVariable("id") Long id) {
        return authorService.DeleteAuthorById(id);
    }
}
