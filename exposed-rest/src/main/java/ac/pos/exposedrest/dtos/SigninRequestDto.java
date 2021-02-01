package ac.pos.exposedrest.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SigninRequestDto {
    private String name;
    private String password;

    public SigninRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
