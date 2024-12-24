package net.jmp.spring.java.app;

/*
 * (#)TestJpa.java  0.7.0   12/24/2024
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

import net.jmp.spring.java.app.entities.Employee;

import net.jmp.spring.java.app.services.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/// The test class for the JPA beans.
/// Note that because this is not a Spring Boot
/// application autowiring does not work.
///
/// @version    0.7.0
/// @since      0.7.0
@DisplayName("JPA and MySQL client")
@Tag("JPA")
final class TestJpa {
    private ApplicationContext context;
    private EmployeeService employeeService;

    @BeforeEach
    void beforeEach() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }

        if (this.employeeService == null) {
            this.employeeService = this.context.getBean(EmployeeService.class);
        }
    }

    @Test
    void testEmployeeServiceFindAll() {
        final List<Employee> employees = this.employeeService.findAll();

        employees.forEach(System.out::println);
    }
}
