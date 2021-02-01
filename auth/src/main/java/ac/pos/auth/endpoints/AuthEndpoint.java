package ac.pos.auth.endpoints;

import ac.pos.auth.exceptions.ServiceException;
import ac.pos.auth.services.AuthService;
import api.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapFaultException;

import java.io.File;
import java.io.IOException;

@Endpoint
public class AuthEndpoint {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    private static final String NAMESPACE_URI = "api/auth";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "signin-request")
    @ResponsePayload
    public SigninResponse Authenticate(@RequestPayload SigninRequest request) throws IOException {
        SigninResponse response = new SigninResponse();

        try {
            User user = restTemplate.getForObject("http://localhost:3001/api/users/name/" + request.getName(), User.class);

            if (user != null && user.getPassword().equals(request.getPassword())) {
                File file = authService.CreateRandomKeyFile();
                String token = authService.GenerateToken(file, user);
                Key key = new Key();
                key.setId(user.getId());
                key.setToken(token);

                authService.WriteDataToKeyFile(file, key);
                response.setCookie(file.getName());
                response.setToken(token);
            } else {
                throw new ServiceException("Unauthorized", "401");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SoapFaultException(ex.toString());
        }

        return response;
    }

    @PayloadRoot(namespace=NAMESPACE_URI, localPart="logout-request")
    @ResponsePayload
    public LogoutResponse Logout(@RequestPayload LogoutRequest request) throws IOException {
        String cookie = request.getCookie();
        String token = request.getToken();

        File file = authService.GetKeyFile(cookie);

        LogoutResponse response = new LogoutResponse();
        if (authService.isAuthorized(cookie, token)) {
            response.setOk(file.delete());
        } else throw new ServiceException("Unauthorized", "401");
        return response;
    }

    @PayloadRoot(namespace=NAMESPACE_URI, localPart="user-request")
    @ResponsePayload
    public UserResponse GetUser(@RequestPayload UserRequest request) throws IOException {
        String cookie = request.getCookie();
        String token = request.getToken();

        UserResponse response = new UserResponse();

        if (authService.isAuthorized(cookie, token)) {
            try {
                Key key = authService.ReadDataFromKeyFile(cookie);
                User user = restTemplate.getForObject("http://localhost:3001/api/users/id/" + key.getId(), User.class);
                response.setId(key.getId());
                response.setName(user.getName());
                response.setRole(user.getRole());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new SoapFaultException(ex.toString());
            }
        } else throw new ServiceException("Unauthorized", "401");

        return response;
    }
}
