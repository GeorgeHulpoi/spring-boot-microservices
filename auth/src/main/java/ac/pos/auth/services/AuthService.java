package ac.pos.auth.services;

import ac.pos.auth.exceptions.ServiceException;
import api.auth.Key;
import api.auth.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${keys.path}")
    private String keysPath;

    public File CreateRandomKeyFile() throws IOException {
        UUID uuid = UUID.randomUUID();
        File file = this.GetKeyFile(uuid.toString() + ".key");
        if (file.createNewFile()) {
            return file;
        } else {
            return this.CreateRandomKeyFile();
        }
    }

    public File GetKeyFile(String path) {
        File file = new File(this.keysPath + path);
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        return file;
    }

    public void WriteDataToKeyFile(File file, Key key) throws IOException {
        this.objectMapper.writeValue(file, key);
    }

    public Key ReadDataFromKeyFile(String filename) throws IOException {
        File file = this.GetKeyFile(filename);
        if (file.canRead()) {
            return this.objectMapper.readValue(file, Key.class);
        }
        return null;
    }

    public String GenerateToken(File file, User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        HashMap<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "");
        headerClaims.put("typ", "JWT");

        JWTCreator.Builder builder =  JWT.create().withHeader(headerClaims)
                .withClaim("iss", "api/auth")
                .withClaim("sub", String.valueOf(user.getId()))
                .withClaim("exp", String.valueOf(now.getTime()))
                .withClaim("jti", file.getName())
                .withClaim("role", String.valueOf(user.getRole()));

        String jwt = builder.sign(Algorithm.HMAC256("(14o/%D1Xr2hS?f_cu{nmBe6gA85F7"));
        return jwt;
    }

    public boolean isAuthorized(String cookieValue, String tokenValue) throws IOException {
        File file = this.GetKeyFile(cookieValue);

        if (!file.exists()) {
            throw new ServiceException("The key file doesn't exists", "400");
        }

        Key key = this.ReadDataFromKeyFile(cookieValue);
        String token = key.getToken();

        return token.equals(tokenValue);
    }
}
