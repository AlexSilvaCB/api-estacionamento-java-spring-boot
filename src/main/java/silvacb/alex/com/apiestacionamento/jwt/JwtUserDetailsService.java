package silvacb.alex.com.apiestacionamento.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import silvacb.alex.com.apiestacionamento.entity.User;
import silvacb.alex.com.apiestacionamento.services.UserService;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.searchUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username){
        User.Role role = userService.searchRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
