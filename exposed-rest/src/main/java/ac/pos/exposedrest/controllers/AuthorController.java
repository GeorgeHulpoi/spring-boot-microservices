package ac.pos.exposedrest.controllers;

import ac.pos.exposedrest.services.AuthorService;
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
@RequestMapping("api/author")
public class AuthorController {

    @Autowired
    private EditorService editorService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public ResponseEntity<?> GetAllAuthors(RequestEntity<String> request) {
        return authorService.GetAllAuthors(request);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> GetAuthorById(RequestEntity<String> request,
                                           @PathVariable("id") Long id) {
        return authorService.GetAuthorById(request, id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> GetAuthorsByName(RequestEntity<String> request,
                                             @PathVariable("name") String name) {
        return authorService.GetAuthorsByName(request, name);
    }

    @PostMapping("")
    public ResponseEntity<?> CreateAuthor(@RequestBody Map<String, Object> data,
                                          @RequestHeader("Authorization") String authorization,
                                          @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return editorService.CreateAuthor(data, authorization, cookie);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> UpdateAuthorById(@RequestBody Map<String, Object> data,
                                              @PathVariable("id") Long id,
                                              @RequestHeader("Authorization") String authorization,
                                              @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return editorService.UpdateAuthorById(data, id, authorization, cookie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> DeleteAuthorById(@PathVariable("id") Long id,
                                              @RequestHeader("Authorization") String authorization,
                                              @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return editorService.DeleteAuthorById(id, authorization, cookie);
    }
}
