package silvacb.alex.com.apiestacionamento.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import silvacb.alex.com.apiestacionamento.repository.UserRepository;


@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

}
