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
@XmlRootElement(name="Fault", namespace="http://schemas.xmlsoap.org/soap/envelope/")
@Getter
@Setter
@NoArgsConstructor
public class FaultResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name="faultcode")
    private String code;

    @XmlElement(name="faultstring")
    private String string;

    @XmlElement(name="detail")
    private FaultDetail detail;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class FaultDetail implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlElement(name="statusCode")
        private String statusCode;

        @XmlElement(name="message")
        private String message;
    }
}

