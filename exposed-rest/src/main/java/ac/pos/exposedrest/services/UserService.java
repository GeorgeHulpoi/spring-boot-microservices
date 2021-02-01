package ac.pos.exposedrest.services;

import ac.pos.exposedrest.dtos.UserDto;
import ac.pos.exposedrest.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> GetAllUsers(RequestEntity<String> request) {
        ResponseEntity<List<UserDto>> response = restTemplate.exchange("http://localhost:3001/api/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {});

        List<User> users = response.getBody().stream().map(u -> new User(u.getId(), u.getName(), u.getRole())).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<?> GetUserById(Long id) {
        UserDto userDto = restTemplate.getForObject("http://localhost:3001/api/users/id/" + id, UserDto.class);
        return new ResponseEntity<>(new User(userDto.getId(), userDto.getName(), userDto.getRole()), HttpStatus.OK);
    }

    public ResponseEntity<?> GetUserByName(RequestEntity<String> request, String name) {
        ResponseEntity<List<UserDto>> response = restTemplate.exchange("http://localhost:3001/api/users/name/" + name,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {});

        List<User> users = response.getBody().stream().map(u -> new User(u.getId(), u.getName(), u.getRole())).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
