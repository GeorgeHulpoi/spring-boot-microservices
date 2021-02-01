package ac.pos.exposedrest.controllers;

import ac.pos.exposedrest.dtos.SigninRequestDto;
import ac.pos.exposedrest.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<Object> Signin(@RequestBody SigninRequestDto data) throws SOAPException, JAXBException, IOException {
        return authService.Signin(data);
    }

    @GetMapping("logout")
    public ResponseEntity<Object> Logout(@RequestHeader("Authorization") String authorization,
                                         @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return authService.Logout(authorization, cookie);
    }
}
