package zeroflyer.qrecode.testfordb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.sql.SQLOutput;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestForDB {

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Test
    public void insertTest() {
        //given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        System.out.println(value);
        assertThat(value).isEqualTo("hello");
    }
}
