package net.jmp.spring.java.app.configs;

/*
 * (#)RedisConfig.java  0.7.0   12/23/2024
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

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import net.jmp.spring.java.app.classes.Student;

import net.jmp.spring.java.app.repositories.StudentRepository;
import net.jmp.spring.java.app.repositories.StudentRepositoryImpl;

import org.redisson.Redisson;

import org.redisson.api.RedissonClient;

import org.redisson.config.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/// The Spring Redis configuration.
///
/// @version    0.7.0
/// @since      0.7.0
@Configuration
public class RedisConfig {
    /// The default constructor.
    public RedisConfig() {
        super();
    }

    /// Create and return a Jedis connection factory.
    ///
    /// @return org.springframework.data.redis.connection.jedis.JedisConnectionFactory
    /// @since  0.2.0
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        String hostName = null;
        int port = 0;

        final var secretProperties = new Properties();

        try (final var fis = new FileInputStream(ConfigFile.CONFIG_FILE)) {
            secretProperties.load(fis);

            hostName = secretProperties.getProperty("redis.host");
            port = Integer.parseInt(secretProperties.getProperty("redis.port"));
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        assert hostName != null;
        assert port > 0;

        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    /// Create and return a Redis template. Both the key and value types are string.
    ///
    /// @return org.springframework.data.redis.core.RedisTemplate
    /// @since  0.2.0
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();

        template.setConnectionFactory(this.jedisConnectionFactory());

        return template;
    }

    /// Create and return a Redis student template.
    ///
    /// @return org.springframework.data.redis.core.RedisTemplate
    /// @since  0.2.0
    private RedisTemplate<String, Student> redisStudentTemplate() {
        final RedisTemplate<String, Student> template = new RedisTemplate<>();

        template.setConnectionFactory(this.jedisConnectionFactory());
        template.afterPropertiesSet();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));

        return template;
    }

    /// Create and return the student repository.
    ///
    /// @return net.jmp.spring.java.app.repositories.StudentRepository
    /// @since  0.2.0
    @Bean
    public StudentRepository studentRepository() {
        return new StudentRepositoryImpl(this.redisStudentTemplate());
    }


    ///  Create and return a Redisson client.
    ///
    /// @return org.redisson.api.RedissonClient
    /// @since  0.3.0
    @Bean
    public RedissonClient redissonClient() {
        final Config config = new Config();

        String hostName = null;
        int port = 0;

        final var secretProperties = new Properties();

        try (final var fis = new FileInputStream(ConfigFile.CONFIG_FILE)) {
            secretProperties.load(fis);

            hostName = secretProperties.getProperty("redis.host");
            port = Integer.parseInt(secretProperties.getProperty("redis.port"));
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        assert hostName != null;
        assert port > 0;

        config.useSingleServer()
                .setAddress("redis://" + hostName + ":" + port);

        return Redisson.create(config);
    }
}
