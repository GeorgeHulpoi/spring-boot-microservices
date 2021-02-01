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
@XmlRootElement(name="logout-response", namespace="api/auth")
@Getter
@Setter
@NoArgsConstructor
public class LogoutResponseDto implements Serializable {
    @XmlElement(name="ok", namespace="api/auth")
    private Boolean ok;
}
