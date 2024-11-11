package net.jmp.spring.java.app;

/*
 * (#)Main.java 0.2.0   11/09/2024
 * (#)Main.java 0.1.0   11/04/2024
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

/// The main application class.
///
/// @version    0.2.0
/// @since      0.1.0
final class Main implements Runnable {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The command line arguments.
    private final String[] arguments;

    /// A constructor that takes the
    /// command line arguments from
    /// the bootstrap class.
    ///
    /// @param   args    java.lang.String[]
    Main(final String[] args) {
        super();

        this.arguments = Objects.requireNonNull(args);
    }

    /// The run method.
    @Override
    public void run() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        this.sayHello(context);
        this.useMongoTemplate(context);
        this.useMongoRepository(context);
        this.useRedisTemplate(context);
        this.useRedisRepository(context);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Say hello.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    private void sayHello(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        final HelloWorldService helloWorldService = context.getBean(HelloWorldService.class);

        this.logger.info(helloWorldService.getHelloWorld());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Find all the documents in the demo collection
    /// of the training database in MongoDB using the
    /// MongoTemplate.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    private void useMongoTemplate(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        final MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);
        final List<Demo> documents = mongoTemplate.findAll(Demo.class);

        documents.forEach(o -> this.logger.info(o.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Find documents in the demo collection
    /// of the training database in MongoDB using the
    /// custom queries in the DemoRepository.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    private void useMongoRepository(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        final DemoRepository repository = context.getBean(DemoRepository.class);
        final List<Demo> documentsByPrice = repository.findByPrice(17);

        documentsByPrice.forEach(o -> this.logger.info(o.toString()));

        final List<Demo> documentsByQuantity = repository.findByQuantity(234);

        documentsByQuantity.forEach(o -> this.logger.info(o.toString()));

        final Optional<Demo> documentById = repository.findById("672a33a932aa022e27e36664");

        documentById.ifPresent(o -> this.logger.info(o.toString()));

        final Optional<Demo> documentByProdId = repository.findByProdId(102);

        documentByProdId.ifPresent(o -> this.logger.info(o.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Use the Redis template to store and fetch objects
    /// from both the string and user services.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    /// @since          0.2.0
    private void useRedisTemplate(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        this.redisStringService(context);
        this.redisUserService(context);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Work with the Redis string service to store and fetch objects.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    /// @since          0.2.0
    private void redisStringService(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        @SuppressWarnings("unchecked")
        final RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);
        final RedisStringService redisStringService = new RedisStringService(redisTemplate);

        redisStringService.setValue("name", "John Doe");

        if (this.logger.isInfoEnabled()) {
            this.logger.info(redisStringService.getValue("name"));
        }

        if (redisStringService.deleteValue("name")) {
            this.logger.info("Object 'name' deleted");
        } else {
            this.logger.warn("Object 'name' not deleted");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
   }

    /// Work with the Redis user service to store and fetch objects.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    /// @since          0.2.0
    private void redisUserService(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        @SuppressWarnings("unchecked")
        final RedisTemplate<String, User> redisUserTemplate = context.getBean(RedisTemplate.class);
        final RedisUserService redisUserService = new RedisUserService(redisUserTemplate);

        final User user = new User();

        user.setId("123456789abcedf0");
        user.setUserName("John Doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("secret");

        redisUserService.setUser(user);

        final User john = redisUserService.getUser("123456789abcedf0");

        assert john != null;

        if (this.logger.isInfoEnabled()) {
            this.logger.info(john.toString());
        }

        if (redisUserService.deleteUser("123456789abcedf0")) {
            this.logger.info("User '123456789abcedf0' deleted");
        } else {
            this.logger.warn("User '123456789abcedf0' not deleted");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Use the Redis repository to store and fetch objects
    /// from the student repository.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    /// @since          0.2.0
    private void useRedisRepository(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        final StudentRepository repository = context.getBean(StudentRepository.class);
        final StudentService studentService = new StudentService(repository);

        final Student student = new Student();

        student.setId("identifier");
        student.setGender(Student.Gender.FEMALE);
        student.setName("Bella");
        student.setGrade(100);

        final Student result = studentService.save(student.getId(), student);

        assert result != null;
        assert result.equals(student);
        assert studentService.existsById(result.getId());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}

