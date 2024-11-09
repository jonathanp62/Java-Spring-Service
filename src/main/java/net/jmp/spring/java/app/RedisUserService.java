package net.jmp.spring.java.app;

/*
 * (#)RedisUserService.java 0.2.0   11/09/2024
 *
 * @author   Jonathan Parker
 *
 * MIT License
 *
 * Copyright (c) 2024 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import org.springframework.stereotype.Service;

/// The Redis user service.
///
/// @version    0.2.0
/// @since      0.2.0
@Service
public class RedisUserService {
    /// The Redis template.
    private final RedisTemplate<String, User> redisTemplate;

    /// The constructor.
    ///
    /// @param  redisTemplate   org.springframework.data.redis.core.RedisTemplate
    public RedisUserService(final RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /// Set the user.
    ///
    /// @param  user    net.jmp.spring.java.app.User
    public void setUser(final User user) {
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));

        this.redisTemplate.opsForValue().set(user.getId(), user);
    }

    /// Get the user.
    ///
    /// @param  id  java.lang.String
    /// @return     net.jmp.spring.java.app.User
    public User getUser(final String id) {
        return this.redisTemplate.opsForValue().get(id);
    }

    /// Delete the user.
    ///
    /// @param  id  java.lang.String
    /// @return     boolean
    public boolean deleteUser(final String id) {
        return Boolean.TRUE.equals(this.redisTemplate.delete(id));
    }
}
