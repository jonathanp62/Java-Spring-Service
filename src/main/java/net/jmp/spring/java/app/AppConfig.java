package net.jmp.spring.java.app;

/*
 * (#)AppConfig.java    0.1.0   11/04/2024
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

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/// The Spring application configuration.
///
/// @version    0.1.0
/// @since      0.1.0
@Configuration
@EnableMongoRepositories("net.jmp.spring.java.app")
public class AppConfig {
    /// The default constructor.
    public AppConfig() {
        super();
    }

    /// The hello world service.
    ///
    /// @return net.jmp.spring.java.app.HelloWorldService
    @Bean
    public HelloWorldService helloWorldService() {
        return new HelloWorldServiceImpl();
    }

    /// Create a MongoDB client.
    ///
    /// @return com.mongodb.client.MongoClient
    @Bean
    public MongoClient mongoClient() {
        String mongoDbUri = "mongodb+srv://{uri.userid}:{uri.password}@{uri.domain}/?retryWrites=true&w=majority";

        final var secretProperties = new Properties();

        try (final var fis = new FileInputStream("config/secrets.properties")) {
            secretProperties.load(fis);

            mongoDbUri = mongoDbUri.replace("{uri.userid}", secretProperties.getProperty("mongodb.uri.userid"));
            mongoDbUri = mongoDbUri.replace("{uri.password}", secretProperties.getProperty("mongodb.uri.password"));
            mongoDbUri = mongoDbUri.replace("{uri.domain}", secretProperties.getProperty("mongodb.uri.domain"));
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        return MongoClients.create(mongoDbUri);
    }

    /// Create a MongoDB template.
    ///
    /// @return org.springframework.data.mongodb.core.MongoTemplate
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(this.mongoClient(), "training");
    }
}
