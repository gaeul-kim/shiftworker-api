package shiftworker.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author sangsik.kim
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void name() {
        redisTemplate.opsForValue().set("name", "sangsik");
        assertThat(redisTemplate.opsForValue().get("name"), is("sangsik"));
    }

/*    @Test
    public void token_cache() {
        redisTemplate.opsForValue().set("name", "sangsik");
        assertThat(redisTemplate.opsForValue().get("name"), is("sangsik"));
    }*/
}
