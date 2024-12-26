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

import org.springframework.data.domain.Sort;

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

        assertThat(employees).hasSize(300_024);
    }

    @Test
    void testEmployeeServiceFindAllSorted() {
        final Sort sort = Sort.by("lastName", "firstName");
        final List<Employee> employees = this.employeeService.findAll(sort)
                .stream()
                .limit(10)
                .toList();

        assertAll(
                () -> assertThat(employees).hasSize(10),
                () -> assertThat(employees.getFirst().getFirstName()).isEqualTo("Abdelkader"),
                () -> assertThat(employees.get(1).getFirstName()).isEqualTo("Adhemar"),
                () -> assertThat(employees.get(2).getFirstName()).isEqualTo("Aemilian"),
                () -> assertThat(employees.get(3).getFirstName()).isEqualTo("Alagu"),
                () -> assertThat(employees.get(4).getFirstName()).isEqualTo("Aleksander"),
                () -> assertThat(employees.get(5).getFirstName()).isEqualTo("Alexius"),
                () -> assertThat(employees.get(6).getFirstName()).isEqualTo("Alois"),
                () -> assertThat(employees.get(7).getFirstName()).isEqualTo("Aluzio"),
                () -> assertThat(employees.get(8).getFirstName()).isEqualTo("Amabile"),
                () -> assertThat(employees.get(9).getFirstName()).isEqualTo("Anestis")
        );
    }

    @Test
    void testEmployeeServiceFindById() {
        final int employeeNumber = 100_860;
        final Employee employee = this.employeeService.findById(employeeNumber).orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(employee.getEmployeeNumber()).isEqualTo(employeeNumber),
                () -> assertThat(employee.getFirstName()).isEqualTo("Amabile")
        );

        assertThrows(
                RuntimeException.class, () -> this.employeeService.findById(0).orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @Test
    void testEmployeeServiceFindAllById() {
        final List<Integer> ids = List.of(
                258641, 258005, 455773, 436560, 266651, 487598, 216963, 15427, 100860, 107070, 0
        );

        final List<Integer> employees = this.employeeService.findAllById(ids)
                        .stream()
                        .map(Employee::getEmployeeNumber)
                        .toList();

        assertAll(
                () -> assertThat(employees).hasSize(10),
                () -> assertThat(employees).contains(258641),
                () -> assertThat(employees).contains(258005),
                () -> assertThat(employees).contains(455773),
                () -> assertThat(employees).contains(436560),
                () -> assertThat(employees).contains(266651),
                () -> assertThat(employees).contains(487598),
                () -> assertThat(employees).contains(216963),
                () -> assertThat(employees).contains(15427),
                () -> assertThat(employees).contains(100860),
                () -> assertThat(employees).contains(107070)
        );
    }

    @Test
    void testEmployeeServiceCount() {
        assertThat(this.employeeService.count()).isEqualTo(300_024);
    }

    @Test
    void testEmployeeServiceExists() {
        assertThat(this.employeeService.existsById(100_860)).isTrue();
        assertThat(this.employeeService.existsById(0)).isFalse();
    }
}
