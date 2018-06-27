package shiftworker.community.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import shiftworker.community.util.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author sangsik.kim
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional.ofNullable(request.getHeader("authorization")).ifPresent(JwtHelper::validation);
        return true;
    }
}
