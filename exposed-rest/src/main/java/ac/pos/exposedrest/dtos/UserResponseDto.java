package ac.pos.exposedrest.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="user-response", namespace="api/auth")
@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name="id", namespace="api/auth")
    private Long id;

    @XmlElement(name="name", namespace="api/auth")
    private String name;

    @XmlElement(name="role", namespace="api/auth")
    private Byte role;
}