package ac.pos.exposedrest.models;

import ac.pos.exposedrest.dtos.UserResponseDto;
import com.fasterxml.jackson.annotation.JsonValue;

public class User {
    private Long id;
    private String name;
    private Role role;

    public User(Long id, String name, Role role) {
        setId(id);
        setName(name);
        setRole(role);
    }

    public User(Long id, String name, Byte role) {
        setId(id);
        setName(name);
        setRole(role);
    }

    public User(UserResponseDto data) {
        setId(data.getId());
        setName(data.getName());
        setRole(data.getRole());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = new String(name); }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public void setRole(Byte role) {
        if (role == 0) { this.role = Role.MANAGER; }
        else if (role == 1) { this.role = Role.EDITOR; }
        else if (role == 2) { this.role = Role.AUTHOR; }
        else { this.role = Role.NONE; }
    }

    public enum Role {
        MANAGER,
        EDITOR,
        AUTHOR,
        NONE;

        @JsonValue
        public int toValue() {
            return ordinal();
        }
    }
}
