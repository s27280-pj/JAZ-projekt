package pl.bookstore.bookshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bookstore.bookshop.repository.UserRepository;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        pl.bookstore.bookshop.model.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));

        String password = new String(user.getPassword(), StandardCharsets.UTF_8);
        return new User(user.getUsername(),
                password,
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList(String.valueOf(user.getAuthority())));
    }
}
