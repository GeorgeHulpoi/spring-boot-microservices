package ac.pos.exposedrest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> GetAllBooks(RequestEntity<String> request) {
        return restTemplate.exchange("http://localhost:3001/api/books", HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<?> GetBookById(RequestEntity<String> request, Long id) {
        return restTemplate.exchange("http://localhost:3001/api/books/id/" + id, HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<?> GetBooksByName(RequestEntity<String> request, String name) {
        return restTemplate.exchange("http://localhost:3001/api/books/name/" + name, HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<?> GetBooksByGenre(RequestEntity<String> request, String genre) {
        return restTemplate.exchange("http://localhost:3001/api/books/genre/" + genre, HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<?> GetBooksByReleaseYear(RequestEntity<String> request, Short year) {
        return restTemplate.exchange("http://localhost:3001/api/books/year/" + year, HttpMethod.GET, request, String.class);
    }
}
