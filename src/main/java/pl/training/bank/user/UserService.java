package pl.training.bank.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @NonNull
    private UserRepository userRepository;
    @NonNull
    private RoleRepository roleRepository;
    @NonNull
    private PasswordEncoder passwordEncoder;
    @Setter
    private String defaultRoleName;

    public void addUser(User user) {
        Role role = getDefaultRole();
        user.setRoles(Set.of(role));
        encodePassword(user);
        userRepository.saveAndFlush(user);
    }

    private void encodePassword(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
    }

    private Role getDefaultRole() {
        return roleRepository.getByName(defaultRoleName)
                .orElseGet(this::createDefaultRole);
    }

    private Role createDefaultRole() {
        Role role = new Role(defaultRoleName);
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.getByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", login)));
    }

}
