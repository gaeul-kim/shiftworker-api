package shiftworker.community.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiftworker.community.domain.User;
import shiftworker.community.exception.DuplicatedUsernameException;
import shiftworker.community.exception.UnAuthenticationException;
import shiftworker.community.service.UserService;
import shiftworker.community.util.JwtHelper;

/**
 * @author sangsik.kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    final private UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public String join(@RequestBody UserDto userDto) throws DuplicatedUsernameException {
        return createUserJwt(userService.create(userDto.toUser()));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) throws UnAuthenticationException {
        return createUserJwt(userService.login(userDto.getUsername(), userDto.getPassword()));
    }

    private String createUserJwt(User user) {
        return JwtHelper.create("username", user.getUsername());
    }

    @Getter
    @Setter
    public static class UserDto {
        private String username;
        private String password;

        User toUser() {
            return new User(this.username, this.password);
        }
    }
}
