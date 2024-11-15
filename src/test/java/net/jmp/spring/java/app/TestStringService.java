package net.jmp.spring.java.app;

/*
 * (#)TestStringService.java    0.4.0   11/15/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
 * @since    0.4.0
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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/// The test class for the string service.
///
/// @version    0.4.0
/// @since      0.4.0
public final class TestStringService {
    private ApplicationContext context;

    @Before
    public void before() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
    }

    @Test
    public void testIsStringLegal() {
        final StringService stringService = this.context.getBean(StringService.class);

        assertTrue(stringService.isStringLegal("JohnDoe"));
        assertFalse(stringService.isStringLegal("John Doe"));
        assertTrue(stringService.isStringLegal("123456789abcedf0-_$"));
        assertFalse(stringService.isStringLegal("~`!@#%^&*()+={}[]\\|;:'\"<>,.?/\""));
    }

    @Test
    public void testRemoveIllegalCharacters() {
        final StringService stringService = this.context.getBean(StringService.class);

        final String result1 = stringService.removeIllegalCharacters("Key / Value");
        final String result2 = stringService.removeIllegalCharacters("Key-Value");
        final String result3 = stringService.removeIllegalCharacters("~`!@#%^&*()+={}[]\\|;:'\"<>,.?/");

        assertEquals("KeyValue", result1);
        assertEquals("Key-Value", result2);
        assertEquals("", result3);
    }

    @Test
    public void testSanitizeString() {
        final StringService stringService = this.context.getBean(StringService.class);

        assertEquals("JohnDoe", stringService.sanitize("John Doe"));

        final String result1 = stringService.sanitize("Key / Value");
        final String result2 = stringService.sanitize("Key-Value");
        final String result3 = stringService.sanitize("~`!@#%^&*()+={}[]\\|;:'\"<>,.?/");

        assertEquals("KeyValue", result1);
        assertEquals("Key-Value", result2);
        assertEquals("", result3);
    }
}
