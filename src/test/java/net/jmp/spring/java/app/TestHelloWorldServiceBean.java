package net.jmp.spring.java.app;

/*
 * (#)TestHelloWorldServiceBean.java    0.6.0   11/18/2024
 * (#)TestHelloWorldServiceBean.java    0.1.0   11/04/2024
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

import net.jmp.spring.java.app.services.HelloWorldService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContext;

/// The test class for the hello world service bean.
/// Note that because this is not a Spring Boot
/// application autowiring does not work.
///
/// @version    0.6.0
/// @since      0.1.0
@DisplayName("Hello World Service Bean")
@Tag("Service")
final class TestHelloWorldServiceBean {
    private HelloWorldService helloWorldService;

    @BeforeEach
    void beforeEach() {
        if (this.helloWorldService == null) {
            final ApplicationContext context = AppContext.getInstance().getApplicationContext();

            this.helloWorldService = context.getBean(HelloWorldService.class);
        }
    }

    @DisplayName("Test hello world service bean")
    @Test
    void testGetHelloWorld() {
        final String result = this.helloWorldService.getHelloWorld();

        assertEquals("Hello, World!!", result);
        assertEquals("Hello, World!!", result, () -> "'Hello, World!!' is expected");
    }
}
