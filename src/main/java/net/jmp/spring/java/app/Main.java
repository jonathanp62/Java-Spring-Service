package net.jmp.spring.java.app;

/*
 * (#)Main.java 0.5.0   11/15/2024
 * (#)Main.java 0.3.0   11/13/2024
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

import com.google.gson.Gson;

import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.function.Consumer;

import net.jmp.util.extra.demo.*;

import static net.jmp.util.logging.LoggerUtils.*;

import net.jmp.spring.java.app.classes.Config;
import net.jmp.spring.java.app.classes.DemoDocument;
import net.jmp.spring.java.app.classes.Student;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.data.redis.core.RedisTemplate;

/// The main application class.
///
/// @version    0.5.0
/// @since      0.1.0
public final class Main implements Runnable {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The command line arguments.
    private final String[] arguments;

    /// The application context.
    public static final ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(AppConfig.class);

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

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.logger.debug("Entering shutdown hook");

            final RedissonClient client = APPLICATION_CONTEXT.getBean(RedissonClient.class);

            if (!client.isShutdown()) {
                client.shutdown();
            }

            this.logger.debug("Exiting shutdown hook");
        }));

        this.greeting();

        try {
            this.runDemos(this.loadConfiguration());
        } catch (final Exception e) {
            this.logger.error(catching(e));
        }

        this.useMongoRepository(APPLICATION_CONTEXT);
        this.useRedisTemplate(APPLICATION_CONTEXT);
        this.useRedisRepository(APPLICATION_CONTEXT);
        this.useRedisson(APPLICATION_CONTEXT);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Log the greeting.
    private void greeting() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.arguments.length == 0) {
            if (this.logger.isDebugEnabled()) { // Covers trace, too
                this.logger.debug("{} {}", Name.NAME_STRING, Version.VERSION_STRING);
            } else if (this.logger.isInfoEnabled() || this.logger.isWarnEnabled()) {
                this.logger.info("{} {}", Name.NAME_STRING, Version.VERSION_STRING);
            } else {    // Error or off
                System.out.format("%s %s%n", Name.NAME_STRING, Version.VERSION_STRING);
            }
        } else {
            if (this.logger.isDebugEnabled()) { // Covers trace, too
                this.logger.debug("{} {}: {}", Name.NAME_STRING, Version.VERSION_STRING, this.arguments);
            } else if (this.logger.isInfoEnabled() || this.logger.isWarnEnabled()) {
                this.logger.info("{} {}: {}", Name.NAME_STRING, Version.VERSION_STRING, this.arguments);
            } else {    // Error or off
                System.out.format("%s %s: %s%n", Name.NAME_STRING, Version.VERSION_STRING, Arrays.toString(this.arguments));
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Load the application configuration
    ///
    /// @return net.jmp.spring.java.app.classes.Config
    /// @throws java.io.IOException When an I/O error occurs reading the configuration file
    /// @since  0.5.0
    private Config loadConfiguration() throws IOException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        Config config = null;

        final String appConfigFileName = System.getProperty("app.configurationFile", "config/config.json");
        final Gson gson = new Gson();

        try (final JsonReader reader = new JsonReader(new FileReader(appConfigFileName))) {
            config = gson.fromJson(reader, Config.class);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(config));
        }

        return config;
    }

    /// Run the demonstration classes.
    ///
    /// @param  config  net.jmp.spring.java.app.classes.Config
    private void runDemos(final Config config) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(config));
        }

        final Consumer<String> demoRunner = className -> {
            try {
                final double version = DemoUtils.getDemoClassVersion(className);

                if (version > 0) {
                    if (config.getVersion() >= version) {
                        DemoUtils.runDemoClassDemo(className);
                    }
                } else {
                    DemoUtils.runDemoClassDemo(className);
                }
            } catch (final DemoUtilException due) {
                this.logger.error(catching(due));
            }
        };

        config.getDemosAsStream()
                .map(demo -> config.getPackageName() + "." + demo)
                .forEach(demoRunner);

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

        final DemoDocumentRepository repository = context.getBean(DemoDocumentRepository.class);
        final List<DemoDocument> documentsByPrice = repository.findByPrice(17);

        documentsByPrice.forEach(document -> this.logger.info(document.toString()));

        final List<DemoDocument> documentsByQuantity = repository.findByQuantity(234);

        documentsByQuantity.forEach(document -> this.logger.info(document.toString()));

        final Optional<DemoDocument> documentById = repository.findById("672a33a932aa022e27e36664");

        documentById.ifPresent(document -> this.logger.info(document.toString()));

        final Optional<DemoDocument> documentByProdId = repository.findByProdId(102);

        documentByProdId.ifPresent(document -> this.logger.info(document.toString()));

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
        student.setName("Kristina");
        student.setGrade(100);

        final Student result = studentService.save(student);

        assert result != null;
        assert result.equals(student);
        assert studentService.existsById(result.getId());

        final Optional<Student> fetched = studentService.findById(student.getId());

        assert fetched.isPresent();

        if (this.logger.isInfoEnabled()) {
            this.logger.info(fetched.toString());
        }

        studentService.delete(student);

        assert !studentService.existsById(result.getId());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Demonstrate the use of the Redisson client.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    /// @since          0.3.0
    private void useRedisson(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        final RedissonClient client = context.getBean(RedissonClient.class);

        try {
            final RBucket<String> bucket = client.getBucket("my-bucket");

            bucket.set("my-bucket-value");

            final String result = bucket.get();

            assert result != null;
            assert result.equals("my-bucket-value");

            if (this.logger.isInfoEnabled()) {
                this.logger.info(result);
            }

            if (!bucket.delete()) {
                this.logger.warn("Bucket 'my-bucket-value' was not deleted");
            }
        } finally {
            client.shutdown();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}

