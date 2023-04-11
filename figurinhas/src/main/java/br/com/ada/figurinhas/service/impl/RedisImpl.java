package br.com.ada.figurinhas.service.impl;

import org.springframework.stereotype.Component;

import br.com.ada.figurinhas.service.Redis;
import redis.clients.jedis.Jedis;

@Component
public class RedisImpl implements Redis{

  Jedis jedis = new Jedis("us1-star-beetle-37456.upstash.io", 37456);
  
  public void save(String key, String value){
    jedis.auth("f9bbe914aba548429ac9100dc6a0105c");
    jedis.set(key, value);
  }

  public String get(String key){
    jedis.auth("f9bbe914aba548429ac9100dc6a0105c");
    return jedis.get(key);
  }
}
