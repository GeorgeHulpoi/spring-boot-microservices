package ac.pos.exposedrest.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="signin-response", namespace="api/auth")
@Getter
@Setter
@NoArgsConstructor
public class SigninResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name="cookie", namespace="api/auth")
    private String cookie;

    @XmlElement(name="token", namespace="api/auth")
    private String token;
}