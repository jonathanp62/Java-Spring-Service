package net.jmp.spring.java.app;

/*
 * (#)RedisStringService.java   0.2.0   11/09/2024
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

import org.springframework.stereotype.Service;

/// The Redis string service.
///
/// @version    0.2.0
/// @since      0.2.0
@Service
public class RedisStringService {
    /// The Redis template.
    private final RedisTemplate<String, String> redisTemplate;

    /// The constructor.
    ///
    /// @param  redisTemplate   org.springframework.data.redis.core.RedisTemplate
    public RedisStringService(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /// Set the value.
    ///
    /// @param  key     java.lang.String
    /// @param  value   java.lang.String
    public void setValue(final String key, final String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    /// Get the value.
    ///
    /// @param  key java.lang.String
    /// @return     java.lang.String
    public String getValue(final String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    /// Delete the value.
    ///
    /// @param  key java.lang.String
    /// @return     boolean
    public boolean deleteValue(final String key) {
        return Boolean.TRUE.equals(this.redisTemplate.delete(key));
    }
}
