package ac.pos.exposedrest.services;

import ac.pos.exposedrest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;


@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Jaxb2Marshaller jaxb2Marshaller;

    public ResponseEntity<Object> Signin(SigninRequestDto data) throws SOAPException, IOException, JAXBException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        String body = FormatSignidRawBody(data.getName(), data.getPassword());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:3003/api/auth", HttpMethod.POST, entity, String.class);
        if (response.getBody() != null) {
            SOAPMessage message = MessageFactory.newInstance().createMessage(null,
                    new ByteArrayInputStream(response.getBody().getBytes()));
            Unmarshaller unmarshaller = JAXBContext.newInstance(SigninResponseDto.class).createUnmarshaller();
            Map<String, Object> authResponse = (Map<String, Object>) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> Logout(String authorization, String cookie) throws SOAPException, IOException, JAXBException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        String jwt = ExtractJwtFromAuthorization(authorization);

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String body = FormatLogoutRawBody(cookie, jwt);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:3003/api/auth", HttpMethod.POST, entity, String.class);
        if (response.getBody() != null) {
            SOAPMessage message = MessageFactory.newInstance().createMessage(null,
                    new ByteArrayInputStream(response.getBody().getBytes()));

            Unmarshaller unmarshaller = JAXBContext.newInstance(LogoutResponseDto.class).createUnmarshaller();
            LogoutResponseDto logoutResponse = (LogoutResponseDto) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
            return new ResponseEntity<>(logoutResponse, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public UserResponseDto RequestUser(String jwt, String cookie) throws SOAPException, IOException, JAXBException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        String body = FormatUserRequestRawBody(cookie, jwt);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:3003/api/auth", HttpMethod.POST, entity, String.class);
        if (response.getBody() != null) {
            SOAPMessage message = MessageFactory.newInstance().createMessage(null,
                    new ByteArrayInputStream(response.getBody().getBytes()));

            Unmarshaller unmarshaller = JAXBContext.newInstance(UserResponseDto.class).createUnmarshaller();
            return (UserResponseDto) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
        } else return null;
    }

    public String ExtractJwtFromAuthorization(String authorization) {
        String[] auth_data = authorization.split(" ", 2);
        return (auth_data.length < 2) ? null : auth_data[1];
    }

    private String FormatUserRequestRawBody(String cookie, String jwt) {
        return String.format("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:us=\"api/auth\">" +
                    "<soapenv:Header/>" +
                    "<soapenv:Body>" +
                        "<us:user-request>" +
                            "<us:cookie>%s</us:cookie>" +
                            "<us:token>%s</us:token>" +
                         "</us:user-request>" +
                    "</soapenv:Body>" +
                "</soapenv:Envelope>", cookie, jwt);
    }

    private String FormatSignidRawBody(String name, String password) {
        return String.format("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:us=\"api/auth\">" +
                    "<soapenv:Header/>" +
                    "<soapenv:Body>" +
                        "<us:signin-request>" +
                            "<us:name>%s</us:name>" +
                            "<us:password>%s</us:password>" +
                        "</us:signin-request>" +
                    "</soapenv:Body>" +
                "</soapenv:Envelope>", name, password);
    }

    private String FormatLogoutRawBody(String cookie, String jwt) {
        return String.format("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:us=\"api/auth\">" +
                    "<soapenv:Header/>" +
                    "<soapenv:Body>" +
                         "<us:logout-request>" +
                            "<us:cookie>%s</us:cookie>" +
                            "<us:token>%s</us:token>" +
                        "</us:logout-request>" +
                    "</soapenv:Body>" +
                "</soapenv:Envelope>", cookie, jwt);
    }
}
