package net.jmp.spring.java.app;

/*
 * (#)TestRedis.java    0.2.0   11/09/2024
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

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/// The test class for the Redis service bean.
/// Note that because this is not a Spring Boot
/// application autowiring does not work.
///
/// @version    0.2.0
/// @since      0.2.0
public final class TestRedis {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private ApplicationContext context;

    @Before
    public void before() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
    }

    @After
    public void after() {
        @SuppressWarnings("unchecked")
        final RedisTemplate<String, String> redisTemplate = this.context.getBean(RedisTemplate.class);
        final RedisStringService redisStringService = new RedisStringService(redisTemplate);

        if (!redisStringService.deleteValue("name")) {
            this.logger.warn("Object 'name' not deleted");
        }
    }

    @Test
    public void testRedisTemplate() {
        @SuppressWarnings("unchecked")
        final RedisTemplate<String, String> redisTemplate = this.context.getBean(RedisTemplate.class);
        final RedisStringService redisStringService = new RedisStringService(redisTemplate);

        redisStringService.setValue("name", "John Doe");

        final String result = redisStringService.getValue("name");

        assertEquals("John Doe", result);
    }
}
