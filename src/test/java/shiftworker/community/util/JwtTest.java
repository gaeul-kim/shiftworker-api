package shiftworker.community.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import shiftworker.community.domain.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sangsik.kim
 */
public class JwtTest {

    @Test
    public void name() {
        String key = "DEFAULT_KEY";

        Map<String, Object> abc = new HashMap<>();
        abc.put("username", "sangsik");

        String jwts = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("user", new User("sangsik", "1234512312312312312"))
                .setExpiration(getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(jwts).toString());


    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
