package net.jmp.spring.java.app.demos;

/*
 * (#)JdbcTemplateDemo.java 0.7.0   12/20/2024
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

import net.jmp.spring.java.app.AppContext;

import net.jmp.spring.java.app.repositories.DepartmentRepository;

import net.jmp.spring.java.app.services.DepartmentService;

import net.jmp.util.extra.demo.Demo;
import net.jmp.util.extra.demo.DemoClass;
import net.jmp.util.extra.demo.DemoVersion;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;

/// The JDBC template demonstration.
///
/// @version    0.7.0
/// @since      0.7.0
@DemoClass
@DemoVersion(0.7)
public final class JdbcTemplateDemo implements Demo {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The default constructor.
    public JdbcTemplateDemo() {
        super();
    }

    /// The demo method.
    @Override
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final ApplicationContext context = AppContext.getInstance().getApplicationContext();

        this.departmentService(context);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Work with the JDBC department service to store and fetch objects.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    private void departmentService(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        final DepartmentRepository departmentRepository = context.getBean(DepartmentRepository.class);
        final DepartmentService departmentService = new DepartmentService(departmentRepository);

        departmentService.findAll().forEach(department -> this.logger.info(department.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
