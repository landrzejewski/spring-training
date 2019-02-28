package pl.training.bank.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder,
                                   @Value("${default.role}") String defaultRoleName) {
        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);
        userService.setDefaultRoleName(defaultRoleName);
        return userService;
    }

}
