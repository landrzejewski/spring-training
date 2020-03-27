package pl.training.bank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserTransferObject userTransferObject) {
        User user = userMapper.toUser(userTransferObject);
        userService.addUser(user);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("active")
    public UserTransferObject getActiveUser(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return userMapper.toUserTransferObject(user);
    }

}
