package ac.pos.exposedrest.services;

import ac.pos.exposedrest.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.Map;

@Service
public class EditorService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    public ResponseEntity<?> CreateBook(Map<String, Object> data, String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.EDITOR) {
            data.put("approvedBy", currentUser.getName());
            ClearBookData(data);
            String body = objectMapper.writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange("http://localhost:3001/api/books", HttpMethod.POST, entity, String.class);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> UpdateBookById(Map<String, Object> data, Long id,
                                            String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.EDITOR) {
            data.put("approvedBy", currentUser.getName());
            ClearBookData(data);
            String body = objectMapper.writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange("http://localhost:3001/api/books/" + id, HttpMethod.PUT, entity, String.class);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> DeleteBookById(Long id, String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.EDITOR) {
            restTemplate.delete("http://localhost:3001/api/books/" + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> CreateAuthor(Map<String, Object> data, String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.EDITOR) {
            data.remove("books");
            String body = objectMapper.writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange("http://localhost:3001/api/authors", HttpMethod.POST, entity, String.class);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> UpdateAuthorById(Map<String, Object> data, Long id,
                                              String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.EDITOR) {
            data.remove("id");
            data.remove("books");
            String body = objectMapper.writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange("http://localhost:3001/api/authors/" + id, HttpMethod.PUT, entity, String.class);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> DeleteAuthorById(Long id, String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.EDITOR) {
            restTemplate.delete("http://localhost:3001/api/authors/" + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private void ClearBookData(Map<String, Object> data) {
        Object authors = data.get("authors");
        if (authors instanceof Iterable<?>) {
            for (Object author : (Iterable<?>) authors) {
                if (author instanceof Map<?,?>) {
                    ((Map<?, ?>) author).remove("books");
                }
            }
        }
    }
}
