package ac.pos.restmariadb.services;

import ac.pos.restmariadb.dtos.UserDTO;
import ac.pos.restmariadb.models.Author;
import ac.pos.restmariadb.models.User;
import ac.pos.restmariadb.repositories.AuthorRepository;
import ac.pos.restmariadb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity<Object> GetAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<UserDTO> result = StreamSupport.stream(users.spliterator(), false)
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> GetUserById(Long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(new UserDTO(userData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> GetUserByName(String name) {
        Optional<User> userData = userRepository.findByName(name);

        if (userData.isPresent()) {
            return new ResponseEntity<>(new UserDTO(userData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> CreateUser(User user) {
        if (user.getAuthor() != null) {
            Author author = user.getAuthor();
            Optional<Author> dbAuthor = Optional.empty();

            if (author.getName() != null) {
                List<Author> list = authorRepository.findByName(author.getName());
                if (!list.isEmpty()) {
                    dbAuthor = Optional.of(list.get(0));
                }
            } else {
                dbAuthor = authorRepository.findById(author.getId());
            }

            user.setAuthor(dbAuthor.orElse(author));
        }

        UserDTO created = new UserDTO(userRepository.save(user));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> UpdateUserById(Long id, User user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User realUser = userData.get();

            if (user.getName() != null) {
                realUser.setName(user.getName());
            }

            if (user.getPassword() != null) {
                realUser.setPassword(user.getPassword());
            }

            if (user.getRole() != null) {
                realUser.setRole(user.getRole());
            }

            if (user.getAuthor() != null) {
                Optional<Author> dbAuthor = Optional.empty();

                if (user.getAuthor().getName() != null) {
                    List<Author> list = authorRepository.findByName(user.getAuthor().getName());
                    if (!list.isEmpty())
                    {
                        dbAuthor = Optional.of(list.get(0));
                    }
                } else {
                    dbAuthor = authorRepository.findById(user.getAuthor().getId());
                }

                realUser.setAuthor(dbAuthor.orElse(user.getAuthor()));
            }

            return new ResponseEntity<>(new UserDTO(userRepository.save(realUser)), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> DeleteUserById(Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
