package silvacb.alex.com.apiestacionamento.web.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id ){
		User userId = userService.searchById(id);
		return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(userId));
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> findByAll(){
		List<User> userAll = userService.searchAll();
		return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(userAll));
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreateDTO createDto){
		User createUser = userService.save(UserMapper.toUser(createDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createUser));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO dto){
		userService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());

		return ResponseEntity.ok().body("Senha atualizada com sucesso.");
	}

}
