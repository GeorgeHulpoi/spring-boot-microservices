package ac.pos.exposedrest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> GetAllAuthors(RequestEntity<String> request) {
        return restTemplate.exchange("http://localhost:3001/api/authors", HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<?> GetAuthorById(RequestEntity<String> request, Long id) {
        return restTemplate.exchange("http://localhost:3001/api/authors/id/" + id, HttpMethod.GET, request, String.class);
    }

    public ResponseEntity<?> GetAuthorsByName(RequestEntity<String> request, String name) {
        return restTemplate.exchange("http://localhost:3001/api/authors/name/" + name, HttpMethod.GET, request, String.class);
    }
}
