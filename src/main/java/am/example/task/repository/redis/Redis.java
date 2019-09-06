package am.example.task.repository.redis;

import am.example.task.entity.UserEntity;
import am.example.task.util.JsonUtil;
import am.example.task.util.Validator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

@Repository
public class Redis {

  private final JedisPool jedisPool;
  private static Logger logger = LoggerFactory.getLogger(Redis.class);

  @Autowired
  public Redis(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  public Optional<UserEntity> addUser(String key, UserEntity value, int ttlSeconds) {
    try (Jedis redis = jedisPool.getResource()) {
      String jsonValue = JsonUtil.serialize(value);
      redis.set(key, jsonValue);
      redis.expire(key, ttlSeconds);
    } catch (Exception e) {
      logger.error("Error", e);
    }
    return Optional.of(value);
  }

  public Optional<UserEntity> getUser(String key) {
    try (Jedis redis = jedisPool.getResource()) {
      String infoJson = redis.get(key);
      if (!Validator.isEmpty(infoJson)) {
        return Optional.of(JsonUtil.deserialize(infoJson, new TypeReference<UserEntity>() {
        }));
      }
    } catch (Exception e) {
      logger.error("Error", e);
    }
    return Optional.empty();
  }
}
