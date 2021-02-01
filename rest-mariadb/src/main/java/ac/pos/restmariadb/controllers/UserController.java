package ac.pos.restmariadb.controllers;

import ac.pos.restmariadb.models.User;
import ac.pos.restmariadb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Object> GetAllUsers() {
        return userService.GetAllUsers();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> GetUserById(@PathVariable("id") Long id) {
        return userService.GetUserById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> GetUserByName(@PathVariable("name") String name) {
        return userService.GetUserByName(name);
    }

    @PostMapping("")
    public ResponseEntity<Object> CreateUser(@RequestBody User user) {
        return userService.CreateUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.UpdateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteUserById(@PathVariable("id") Long id) {
        return userService.DeleteUserById(id);
    }
}
