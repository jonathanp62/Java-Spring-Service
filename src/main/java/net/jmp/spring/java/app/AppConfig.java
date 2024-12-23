package net.jmp.spring.java.app;

/*
 * (#)AppConfig.java    0.7.0   12/20/2024
 * (#)AppConfig.java    0.5.0   11/15/2024
 * (#)AppConfig.java    0.4.0   11/15/2024
 * (#)AppConfig.java    0.3.0   11/13/2024
 * (#)AppConfig.java    0.2.0   11/09/2024
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

import net.jmp.spring.java.app.configs.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/// The Spring application configuration.
///
/// @version    0.7.0
/// @since      0.1.0
@Configuration
@Import({ JdbcConfig.class, JpaConfig.class, MongoConfig.class, RedisConfig.class, ServicesConfig.class })
@EnableMongoRepositories("net.jmp.spring.java.app")
public class AppConfig {
    /// The default constructor.
    public AppConfig() {
        super();
    }
}
