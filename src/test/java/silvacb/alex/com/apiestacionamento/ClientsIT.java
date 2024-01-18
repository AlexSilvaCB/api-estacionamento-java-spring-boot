package silvacb.alex.com.apiestacionamento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import silvacb.alex.com.apiestacionamento.web.dto.ClientCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.ClientResponseDTO;
import silvacb.alex.com.apiestacionamento.web.dto.PageableDTO;
import silvacb.alex.com.apiestacionamento.web.exception.ErrorMessage;

@AutoConfigureWebTestClient(timeout = "PT36S")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientsIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void criarCliente_ComDadosValidos_RetornarClienteComStatus201() {
        ClientResponseDTO responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "91191064085"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Tobias Ferreira");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("91191064085");
    }

    @Test
    public void criarCliente_ComCpfJaCadastrado_RetornarClienteErrorMessageStatus409() {
        ErrorMessage responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "79074426050"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(409);
    }

    @Test
    public void criarCliente_ComDadosInvalidos_RetornarClienteErrorMessageStatus422() {
        ErrorMessage responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobi", "91191064085"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);

        responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("", "91191064085"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);

        responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "9119106408"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);

        responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "91191064086"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarCliente_ComPerfilAdm_RetornarClienteErrorMessageStatus403() {
        ErrorMessage responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "bia@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Bianca Silva", "79074426050"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarClientes_ComPaginacaoPeloAdmin_RetornarClientesComStatus200() {
        PageableDTO responseBody = testClient
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "bia@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/api/v1/clients?size=1&page=1")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "bia@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void buscarClientes_ComPaginacaoPeloCliente_RetornarErrorMessageComStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarCliente_ComDadosDoTokenDeCliente_RetornarClienteComStatus200() {
        ClientResponseDTO responseBody = testClient
                .get()
                .uri("/api/v1/clients/details")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("55352517047");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Roberto Gomes");
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(20);

    }

    @Test
    public void buscarCliente_ComDadosDoTokenDeAdministrador_RetornarErrorMessageComStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clients/details")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "bia@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }


}
