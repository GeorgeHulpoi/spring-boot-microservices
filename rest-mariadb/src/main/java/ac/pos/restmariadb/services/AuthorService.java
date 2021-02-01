package ac.pos.restmariadb.services;

import ac.pos.restmariadb.dtos.AuthorDTO;
import ac.pos.restmariadb.models.Author;
import ac.pos.restmariadb.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public ResponseEntity<Object> GetAllAuthors()  {
        Iterable<Author> authors = authorRepository.findAll();
        List<AuthorDTO> result = StreamSupport.stream(authors.spliterator(), false)
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> GetAuthorById(Long id) {
        Optional<Author> authorData = authorRepository.findById(id);

        if (authorData.isPresent()) {
            return new ResponseEntity<>(new AuthorDTO(authorData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> GetAuthorsByName(String name) {
        List<Author> authors = authorRepository.findByName(name);
        List<AuthorDTO> result = authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> UpdateAuthorById(Long id, Author author) {
        Optional<Author> authorData = authorRepository.findById(id);

        if (authorData.isPresent()) {
            Author realAuthor = authorData.get();

            if (author.getName() != null) {
                realAuthor.setName(author.getName());
            }

            return new ResponseEntity<>(new AuthorDTO(authorRepository.save(realAuthor)), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> CreateAuthor(Author author) {
        Author created = authorRepository.save(author);
        return new ResponseEntity<>(new AuthorDTO(created), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> DeleteAuthorById(Long id) {
        authorRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
