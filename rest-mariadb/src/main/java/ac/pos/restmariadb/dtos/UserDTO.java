package ac.pos.restmariadb.dtos;

import ac.pos.restmariadb.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private Byte role;
    //private AuthorDTO author;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();

//        if (user.getAuthor() != null) {
//            this.author = new AuthorDTO(user.getAuthor());
//        }
    }
}
