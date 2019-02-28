package pl.training.bank.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${baseUrl}/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private UserService userService;
    @NonNull
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody UserTo userTo) {
        User user = userMapper.map(userTo);
        userService.addUser(user);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "active", method = RequestMethod.GET)
    public UserTo getActiveUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userMapper.map(user);
    }

}
