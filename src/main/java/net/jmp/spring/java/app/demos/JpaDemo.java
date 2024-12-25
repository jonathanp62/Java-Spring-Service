package net.jmp.spring.java.app.demos;

/*
 * (#)JpaDemo.java  0.7.0   12/25/2024
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

import net.jmp.spring.java.app.AppContext;

import net.jmp.spring.java.app.entities.Employee;

import net.jmp.spring.java.app.services.EmployeeService;

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
public final class JpaDemo implements Demo {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The default constructor.
    public JpaDemo() {
        super();
    }

    /// The demo method.
    @Override
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final ApplicationContext context = AppContext.getInstance().getApplicationContext();

        this.employeeService(context);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// Uses the JPA employee service to fetch and log employee objects.
    ///
    /// @param  context org.springframework.context.ApplicationContext
    private void employeeService(final ApplicationContext context) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(context));
        }

        if (this.logger.isTraceEnabled()) {
            final EmployeeService employeeService = context.getBean(EmployeeService.class);
            final List<Employee> employees = employeeService.findAll();

            this.logger.info("Number of employees: {}", employees.size());

            employees.stream()
                    .limit(10)
                    .forEach(employee -> this.logger.info(employee.toString()));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
