package silvacb.alex.com.apiestacionamento.services;

import java.util.List;

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
	
	@Transactional(readOnly = true)
	public List<User> searchAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User searchById(Long id) {
		return userRepository.findById(id).orElseThrow(
				()-> new RuntimeException("Usuário não encontrado."));
	}

	@Transactional
	public void editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword))
		{
			throw new RuntimeException ("Nova senha não confere com confirmação de senha.");
		}

		User updatePassword = searchById(id);
		if (!updatePassword.getPassword().equals(currentPassword))
		{
			throw new RuntimeException ("Senha não confere.");
		}

		updatePassword.setPassword(newPassword);
	}
}
