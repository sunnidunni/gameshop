package cn.cie.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis operations wrapper using protostuff for serialization and deserialization
 * For object insertion, requires the Class type of the object to be passed
 */
@Component
public class RedisUtil<T> implements InitializingBean {

    private JedisPool jedisPool;

    private static final String REDIS_URL = "redis://localhost:6379/6";

    public static final String EVERYDAY = "everyday";

    public static final String KINDS = "kinds";

    public static final String NEWESTGAME = "newestgame";

    public static final String PRE_UP_GAMES = "preupgames";

    /**
     * Store a single data entry
     *
     * @param key
     * @param value
     * @return
     */
    public String put(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Store a data entry with expiration time
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public String putEx(String key, String value, int timeout) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, timeout, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Get value by key
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Store an object
     *
     * @param key
     * @param value
     * @return
     */
    public String putObject(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, JSON.toJSONString(value));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Store an object with expiration time
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public String putObjectEx(String key, T value, int timeout) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, timeout, JSON.toJSONString(value));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Get object by key
     *
     * @param key
     * @return
     */
    public T getObject(String key, Class clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return (T) JSON.parseObject(jedis.get(key), clazz);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Delete data by key
     *
     * @param key
     * @return
     */
    public long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Dequeue an element from the queue head, blocks for timeout seconds if empty and returns null
     * If timeout is 0, blocks indefinitely until element is available
     *
     * @param timeout blocking time in seconds
     * @param key
     * @param clazz
     * @return
     */
    public T blpopObject(int timeout, String key, Class clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> list = jedis.blpop(timeout, key);
            return (T) JSON.parseObject(list.get(0), clazz);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Dequeue an element from the left side of queue
     *
     * @param key
     * @param clazz
     * @return
     */
    public T lpopObject(String key, Class clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return (T) JSON.parseObject(jedis.lpop(key), clazz);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Add data to the end of the list
     *
     * @param key
     * @param values
     * @return
     */
    public long rpushObject(String key, Class clazz, Object... values) {
        if (values == null || values.length == 0) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String[] jsonStrs = new String[values.length];
            int index = 0;
            for (Object value : values) {
                jsonStrs[index] = JSON.toJSONString(value);
                ++index;
            }
            return jedis.rpush(key, jsonStrs);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Add data to the end of list that expires at specific time
     *
     * @param key
     * @param time   unix timestamp
     * @param values
     * @return
     */
    public long rpushObjectExAtTime(String key, Class clazz, long time, Object... values) {
        if (values.length == 0) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String[] jsonStrs = new String[values.length];
            int index = 0;
            for (Object value : values) {
                jsonStrs[index] = JSON.toJSONString(value);
                ++index;
            }
            long res = jedis.rpush(key, jsonStrs);
            jedis.expireAt(key.getBytes(), time);      // manually set expiration time
            return res;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Add data to the end of list with periodic deletion
     *
     * @param key
     * @param clazz
     * @param timeout
     * @param values
     * @return
     */
    public long rpushObjectEx(String key, Class clazz, int timeout, Object... values) {
        if (values.length == 0) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String[] jsonStrs = new String[values.length];
            int index = 0;
            for (Object value : values) {
                jsonStrs[index] = JSON.toJSONString(value);
                ++index;
            }
            long res = jedis.rpush(key, jsonStrs);
            jedis.expire(key, timeout);
            return res;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Get all data in the list
     *
     * @param key
     * @return
     */
    public List<T> lall(String key, Class clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 0 represents first element, -1 represents last element
            List<String> list = jedis.lrange(key, 0, -1);
            List<T> res = new ArrayList<T>();
            if (list == null || list.size() == 0) {
                return res;
            }
            for (String str : list) {
                res.add((T) JSON.parseObject(str, clazz));
            }
            return res;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * This method is automatically called after Spring injection
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        System.out.println("Creating Redis connection");
        jedisPool = new JedisPool(REDIS_URL);
        System.out.println("jedisPool:"+jedisPool.toString());
        System.out.println("Redis connection created successfully");
    }
}
