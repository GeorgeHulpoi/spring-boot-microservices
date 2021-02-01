package ac.pos.exposedrest.controllers;

import ac.pos.exposedrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> GetAllUsers(RequestEntity<String> request) {
        return userService.GetAllUsers(request);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> GetUserById(@PathVariable("id") Long id) {
        return userService.GetUserById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> GetUserByName(RequestEntity<String> request,
                                           @PathVariable("name") String name) {
        return userService.GetUserByName(request, name);
    }
}
