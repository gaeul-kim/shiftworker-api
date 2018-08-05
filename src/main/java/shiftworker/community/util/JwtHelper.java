package shiftworker.community.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import shiftworker.community.exception.UnAuthenticationException;

import java.util.Calendar;
import java.util.Date;

/**
 * @author sangsik.kim
 */
@Slf4j
public class JwtHelper {
    private static final String SECRET = "DEFAULT_KEY";
    private static final int DEFAULT_EXPIRATION_DATE = 150;

    private JwtHelper() {
    }

    public static <T> String create(String key, T data) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim(key, data)
                .setExpiration(getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String validation(String token) {
        try {
            getClaims(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthenticationException();
        }
        return token;
    }

    public static String getByKey(String jwt, String key) {
        try {
            return getClaims(jwt)
                    .getBody()
                    .get(key)
                    .toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthenticationException();
        }
    }

    private static Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthenticationException();
        }
    }

    private static Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, DEFAULT_EXPIRATION_DATE);
        return calendar.getTime();
    }
}
