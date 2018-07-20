package shiftworker.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author sangsik.kim
 */
@RestController
@RequiredArgsConstructor
public class HelloController {

    private final Environment environment;

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(environment.getActiveProfiles())
                .collect(Collectors.joining(","));
    }
}
