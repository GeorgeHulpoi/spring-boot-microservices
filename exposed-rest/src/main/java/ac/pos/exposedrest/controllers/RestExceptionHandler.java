package ac.pos.exposedrest.controllers;

import ac.pos.exposedrest.dtos.FaultResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Object> HandleRestClientException(HttpServerErrorException ex) throws SOAPException, IOException, JAXBException {
        HttpHeaders headers = ex.getResponseHeaders();
        if (headers == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        HttpHeaders replyHeaders = new HttpHeaders();
        replyHeaders.setContentType(headers.getContentType());

        if (Objects.requireNonNull(headers.getContentType()).isCompatibleWith(MediaType.TEXT_XML)) {
            byte[] body = ex.getResponseBodyAsByteArray();
            SOAPMessage message = MessageFactory.newInstance().createMessage(null,
                    new ByteArrayInputStream(body));
            Unmarshaller unmarshaller = JAXBContext.newInstance(FaultResponseDto.class).createUnmarshaller();
            FaultResponseDto faultResponse = (FaultResponseDto) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
            HttpStatus status = HttpStatus.valueOf(Integer.parseInt(faultResponse.getDetail().getStatusCode()));
            return new ResponseEntity<>(faultResponse.getDetail().getMessage(), replyHeaders, status);
        } else
        {
            return new ResponseEntity<>(ex.getResponseBodyAsString(), replyHeaders, ex.getStatusCode());
        }
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> HandleRestClientException(HttpClientErrorException ex) {
        return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getResponseHeaders(),
                ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> HandleException(Exception ex) {
        System.out.println(ex.getClass());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
