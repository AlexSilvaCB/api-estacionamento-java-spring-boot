package silvacb.alex.com.apiestacionamento.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import silvacb.alex.com.apiestacionamento.entity.User;
import silvacb.alex.com.apiestacionamento.repository.UserRepository;


@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional
	public User save(User user) {	
		return userRepository.save(user);
	}

}
