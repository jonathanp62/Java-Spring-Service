package net.jmp.spring.java.app;

/*
 * (#)TestRedis.java    0.6.0   11/18/2024
 * (#)TestRedis.java    0.5.0   11/15/2024
 * (#)TestRedis.java    0.3.0   11/13/2024
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

import java.util.Optional;

import net.jmp.spring.java.app.classes.Student;
import net.jmp.spring.java.app.classes.User;

import net.jmp.spring.java.app.repositories.StudentRepository;

import net.jmp.spring.java.app.services.RedisStringService;
import net.jmp.spring.java.app.services.RedisUserService;
import net.jmp.spring.java.app.services.StudentService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.data.redis.core.RedisTemplate;

/// The test class for the Redis service bean.
/// Note that because this is not a Spring Boot
/// application autowiring does not work.
///
/// @version    0.6.0
/// @since      0.2.0
@DisplayName("Redis template and Redisson client")
@Tag("Redis")
final class TestRedis {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private ApplicationContext context;

    @BeforeEach
    void beforeEach() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
    }

    @AfterEach
    void afterEach() {
        @SuppressWarnings("unchecked")
        final RedisTemplate<String, String> redisTemplate = this.context.getBean(RedisTemplate.class);
        final RedisStringService redisStringService = new RedisStringService(redisTemplate);

        if (!redisStringService.deleteValue("name")) {
            this.logger.warn("Object 'name' not deleted");
        }
    }

    @DisplayName("Redis template")
    @Nested
    class TestRedisTemplate {
        @DisplayName("Test string service")
        @Test
        void testRedisStringService() {
            @SuppressWarnings("unchecked")
            final RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);
            final RedisStringService redisStringService = new RedisStringService(redisTemplate);

            redisStringService.setValue("name", "John Doe");

            final String result = redisStringService.getValue("name");

            assertEquals("John Doe", result);
            assertThat(result).isEqualTo("John Doe");
        }

        @DisplayName("Test user service")
        @Test
        void testRedisUserService() {
            @SuppressWarnings("unchecked")
            final RedisTemplate<String, User> redisTemplate = context.getBean(RedisTemplate.class);
            final RedisUserService redisUserService = new RedisUserService(redisTemplate);

            final User user = new User();

            user.setId("123456789abcedf0");
            user.setUserName("John Doe");
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setPassword("secret");

            redisUserService.setUser(user);

            final User result = redisUserService.getUser("123456789abcedf0");

            assertEquals(user, result);
            assertThat(result).isEqualTo(user);

            if (!redisUserService.deleteUser(user.getId())) {
                logger.warn("Object '{}' not deleted", user.getId());
            }
        }

        @DisplayName("Test student service")
        @Test
        void testRedisStudentService() {
            final StudentRepository repository = context.getBean(StudentRepository.class);
            final StudentService studentService = new StudentService(repository);

            final Student student = new Student();

            student.setId("identifier");
            student.setGender(Student.Gender.FEMALE);
            student.setName("Oriole");
            student.setGrade(100);

            final Student result = studentService.save(student);

            assertNotNull(result);
            assertEquals(result, student);
            assertTrue(studentService.existsById(result.getId()));

            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result).isEqualTo(student),
                    () -> assertThat(studentService.existsById(result.getId())).isTrue()
            );

            final Optional<Student> fetched = studentService.findById(student.getId());

            assertTrue(fetched.isPresent());
            assertThat(fetched.isPresent()).isTrue();

            studentService.delete(student);

            assertFalse(studentService.existsById(result.getId()));
            assertThat(studentService.existsById(result.getId())).isFalse();
        }
    }

    @DisplayName("Test Redisson client")
    @Test
    void testRedisson() {
        final RedissonClient client = this.context.getBean(RedissonClient.class);

        try {
            final RBucket<String> bucket = client.getBucket("my-bucket");

            bucket.set("my-bucket-value");

            final String result = bucket.get();

            assertNotNull(result);
            assertEquals("my-bucket-value", result);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals("my-bucket-value", result)
            );

            if (!bucket.delete()) {
                this.logger.warn("Bucket 'my-bucket-value' was not deleted");
            }
        } finally {
            client.shutdown();
        }
    }
}
