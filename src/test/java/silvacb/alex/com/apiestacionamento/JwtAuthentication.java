package silvacb.alex.com.apiestacionamento;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import silvacb.alex.com.apiestacionamento.jwt.JwtToken;
import silvacb.alex.com.apiestacionamento.web.dto.UserCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.mapper.UserLoginDTO;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeadersAuthorization(WebTestClient client, String username, String password){
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UserLoginDTO(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();

        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
