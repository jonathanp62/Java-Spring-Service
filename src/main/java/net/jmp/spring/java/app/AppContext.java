package net.jmp.spring.java.app;

/*
 * (#)AppContext.java   0.5.0   11/17/2024
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

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/// The application context singleton.
///
/// @version    0.5.0
/// @since      0.5.0
public final class AppContext {
    /// The singleton instance.
    private static AppContext instance;

    /// The Spring application context.
    private final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    /// The default constructor.
    private AppContext() {
        super();
    }

    /// Return the singleton instance.
    ///
    /// @return net.jmp.spring.java.app.AppContext
    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }

        return instance;
    }

    /// Return the Spring application context.
    ///
    /// @return org.springframework.context.ApplicationContext
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}
