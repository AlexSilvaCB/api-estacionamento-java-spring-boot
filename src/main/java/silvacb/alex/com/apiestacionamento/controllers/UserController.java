package silvacb.alex.com.apiestacionamento.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import silvacb.alex.com.apiestacionamento.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private final UserService userService;

}
