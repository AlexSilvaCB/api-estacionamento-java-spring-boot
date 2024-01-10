package silvacb.alex.com.apiestacionamento.web.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import silvacb.alex.com.apiestacionamento.entity.User;
import silvacb.alex.com.apiestacionamento.services.UserService;
import silvacb.alex.com.apiestacionamento.web.dto.UserCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.mapper.UserMapper;
import silvacb.alex.com.apiestacionamento.web.dto.mapper.UserPasswordDTO;
import silvacb.alex.com.apiestacionamento.web.dto.mapper.UserResponseDTO;
import silvacb.alex.com.apiestacionamento.web.exception.ErrorMessage;

@Tag(name = "Users",
		description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private final UserService userService;

	@Operation(summary = "Retorna uma lista de usuários.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN:CLIENT.",
			security = @SecurityRequirement(name = "security"),
			responses = {
			@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
				content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
			@ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)
    @GetMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findByAll(){
        List<User> userAll = userService.searchAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(userAll));
    }

	@Operation(summary = "Localizar usuário pelo Id.",description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN:CLIENT.",
			security = @SecurityRequirement(name = "security"),
			responses = {
			@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
			@ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENT') AND #id == authentication.principal.id)")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id ){
		User userId = userService.searchById(id);
		return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(userId));
	}

	@Operation(summary = "Criar um novo usuário.", description = "Recurso criar novo usuário.",
			responses = {
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
			@ApiResponse(responseCode = "409", description = "Usuário já cadastrado no sistema",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO createDto){
		User createUser = userService.save(UserMapper.toUser(createDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createUser));
	}

	@Operation(summary = "Atualizar senha.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN:CLIENT.",
			security = @SecurityRequirement(name = "security"),
			responses = {
			@ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Senha não confere",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)
	@PatchMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
	public ResponseEntity<String> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO dto){
		userService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());

		return ResponseEntity.ok().body("Senha atualizada com sucesso.");
	}

}
