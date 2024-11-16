package net.jmp.spring.java.app.demos;

/*
 * (#)StringDemo.java   0.5.0   11/15/2024
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

import net.jmp.spring.java.app.Main;

import net.jmp.spring.java.app.services.StringService;

import net.jmp.util.extra.demo.Demo;
import net.jmp.util.extra.demo.DemoClass;
import net.jmp.util.extra.demo.DemoVersion;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The string service demonstration.
///
/// @version    0.5.0
/// @since      0.5.0
@DemoClass
@DemoVersion(0.5)
public final class StringDemo implements Demo {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The default constructor.
    public StringDemo() {
        super();
    }

    /// The demo method.
    @Override
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final StringService stringService = Main.APPLICATION_CONTEXT.getBean(StringService.class);

        final String result1 = stringService.sanitize("Key / Value");
        final String result2 = stringService.sanitize("Key-Value");
        final String result3 = stringService.sanitize("~`!@#%^&*()+={}[]\\|;:'\"<>,.?/");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Sanitized Key / Value -> '" + result1 + "'");
            this.logger.info("Sanitized Key-Value -> '" + result2 + "'");
            this.logger.info("Sanitized ~`!@#%^&*()+={}[]\\|;:'\"<>,.?/ -> '" + result3 + "'");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
