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
public class ManagerService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    public ResponseEntity<?> CreateUser(Map<String, Object> data, String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.MANAGER) {
            data.remove("id");
            String body = objectMapper.writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange("http://localhost:3001/api/users", HttpMethod.POST, entity, String.class);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> UpdateUser(Map<String, Object> data, Long id,
                                             String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.MANAGER) {
            data.remove("id");
            String body = objectMapper.writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange("http://localhost:3001/api/users/" + id, HttpMethod.PUT, entity, String.class);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> DeleteUser(Long id, String authorization, String cookie) throws SOAPException, JAXBException, IOException {
        String jwt = authService.ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User currentUser = new User(authService.RequestUser(jwt, cookie));

        if (currentUser.getRole() == User.Role.MANAGER) {
            restTemplate.delete("http://localhost:3001/api/users/" + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
