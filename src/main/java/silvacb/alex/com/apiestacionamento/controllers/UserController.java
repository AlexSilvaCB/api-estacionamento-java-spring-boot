package silvacb.alex.com.apiestacionamento.controllers;

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

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id ){
		User userId = userService.searchById(id);
		return ResponseEntity.status(HttpStatus.OK).body(userId);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findByAll(){
		List<User> userAll = userService.searchAll();
		return ResponseEntity.status(HttpStatus.OK).body(userAll);
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user){
		User createUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){
		User updateUser = userService.editPassword(id, user.getPassword());
		return ResponseEntity.status(HttpStatus.OK).body(updateUser);
	}

}
