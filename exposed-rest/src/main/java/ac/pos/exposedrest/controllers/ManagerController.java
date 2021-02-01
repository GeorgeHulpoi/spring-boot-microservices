package ac.pos.exposedrest.controllers;

import ac.pos.exposedrest.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("user")
    public ResponseEntity<?> CreateUser(@RequestBody Map<String, Object> data,
                                             @RequestHeader("Authorization") String authorization,
                                             @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return managerService.CreateUser(data, authorization, cookie);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<?> UpdateUser(@RequestBody Map<String, Object> data,
                                             @PathVariable("id") Long id,
                                             @RequestHeader("Authorization") String authorization,
                                             @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return managerService.UpdateUser(data, id, authorization, cookie);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Object> DeleteUser(@PathVariable("id") Long id,
                                             @RequestHeader("Authorization") String authorization,
                                             @CookieValue(value="token_value") String cookie) throws SOAPException, JAXBException, IOException {
        return managerService.DeleteUser(id, authorization, cookie);
    }
}
