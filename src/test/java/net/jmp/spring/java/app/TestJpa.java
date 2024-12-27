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

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import net.jmp.spring.java.app.entities.Employee;

import net.jmp.spring.java.app.services.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.springframework.context.ApplicationContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private static ApplicationContext context;
    private static EmployeeService employeeService;

    @BeforeAll
    static void beforeAll() {
        if (context == null) {
            context = AppContext.getInstance().getApplicationContext();
        }

        if (employeeService == null) {
            employeeService = context.getBean(EmployeeService.class);
        }
    }

    @AfterEach
    void afterEach() {
        employeeService.deleteById(999_998);
        employeeService.deleteById(999_999);
    }

    @AfterAll
    static void afterAll() {
        employeeService = null;
        context = null;
    }

    @Test
    void testEmployeeServiceFindAll() {
        final List<Employee> employees = employeeService.findAll();

        assertThat(employees).hasSize(300_024);
    }

    @Test
    void testEmployeeServiceFindAllSorted() {
        final Sort sort = Sort.by("lastName", "firstName");
        final List<Employee> employees = employeeService.findAll(sort)
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
        final Employee employee = employeeService.findById(employeeNumber).orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(employee.getEmployeeNumber()).isEqualTo(employeeNumber),
                () -> assertThat(employee.getFirstName()).isEqualTo("Amabile")
        );

        assertThrows(
                RuntimeException.class, () -> employeeService.findById(0).orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @Test
    void testEmployeeServiceFindAllByLastName() {
        final String lastName = "Aamodt";
        final List<Employee> employees = new ArrayList<>(256);

        employeeService.findAllByLastName(lastName).forEach(employees::add);

        assertThat(employees).hasSize(205);
    }

    @Test
    void testEmployeeServiceFindAllById() {
        final List<Integer> ids = List.of(
                258641, 258005, 455773, 436560, 266651, 487598, 216963, 15427, 100860, 107070, 0
        );

        final List<Integer> employees = employeeService.findAllById(ids)
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
    void testEmployeeServiceFindAllPaginated() {
        final Sort sort = Sort.by("lastName", "firstName");
        final Pageable pageable = PageRequest.of(0, 10, sort);
        final Page<Employee> page = employeeService.findAll(pageable);
        final List<Integer> employeeIds = page
                .getContent()
                .stream()
                .map(Employee::getEmployeeNumber)
                .toList();

        assertAll(
                () -> assertThat(employeeIds).hasSize(10),
                () -> assertThat(employeeIds).contains(258641),
                () -> assertThat(employeeIds).contains(258005),
                () -> assertThat(employeeIds).contains(455773),
                () -> assertThat(employeeIds).contains(436560),
                () -> assertThat(employeeIds).contains(266651),
                () -> assertThat(employeeIds).contains(487598),
                () -> assertThat(employeeIds).contains(216963),
                () -> assertThat(employeeIds).contains(15427),
                () -> assertThat(employeeIds).contains(100860),
                () -> assertThat(employeeIds).contains(107070)
        );
    }

    @Test
    void testEmployeeServiceCount() {
        assertThat(employeeService.count()).isEqualTo(300_024);
    }

    @Test
    void testEmployeeServiceExists() {
        assertThat(employeeService.existsById(100_860)).isTrue();
        assertThat(employeeService.existsById(0)).isFalse();
    }

    @Test
    void testEmployeeServiceSaveInsert() {
        final Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmployeeNumber(999_999);
        employee.setHireDate(LocalDate.of(2020, 1, 1));
        employee.setBirthDate(LocalDate.of(1962, 2, 5));
        employee.setGender(Employee.Gender.M);

        final Employee _ = employeeService.save(employee);
        final Employee fetched = employeeService.findById(999_999).orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(fetched).isNotNull(),
                () -> assertThat(fetched.getEmployeeNumber()).isEqualTo(999_999),
                () -> assertThat(fetched.getFirstName()).isEqualTo("John"),
                () -> assertThat(fetched.getLastName()).isEqualTo("Doe"),
                () -> assertThat(fetched.getHireDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(fetched.getBirthDate()).isEqualTo(LocalDate.of(1962, 2, 5)),
                () -> assertThat(fetched.getGender()).isEqualTo(Employee.Gender.M)
        );
    }

    @Test
    void testEmployeeServiceSaveUpdate() {
        final Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmployeeNumber(999_999);
        employee.setHireDate(LocalDate.of(2020, 1, 1));
        employee.setBirthDate(LocalDate.of(1962, 2, 5));
        employee.setGender(Employee.Gender.M);

        final Employee saved = employeeService.save(employee);
        final Employee fetchedAfterInsert = employeeService.findById(999_999).orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(fetchedAfterInsert).isNotNull(),
                () -> assertThat(fetchedAfterInsert.getEmployeeNumber()).isEqualTo(999_999),
                () -> assertThat(fetchedAfterInsert.getFirstName()).isEqualTo("John"),
                () -> assertThat(fetchedAfterInsert.getLastName()).isEqualTo("Doe"),
                () -> assertThat(fetchedAfterInsert.getHireDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(fetchedAfterInsert.getBirthDate()).isEqualTo(LocalDate.of(1962, 2, 5)),
                () -> assertThat(fetchedAfterInsert.getGender()).isEqualTo(Employee.Gender.M)
        );

        saved.setGender(Employee.Gender.F);
        saved.setFirstName("Jane");

        final Employee _ = employeeService.save(saved);

        final Employee fetchedAfterUpdate = employeeService.findById(999_999).orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(fetchedAfterUpdate).isNotNull(),
                () -> assertThat(fetchedAfterUpdate.getEmployeeNumber()).isEqualTo(999_999),
                () -> assertThat(fetchedAfterUpdate.getFirstName()).isEqualTo("Jane"),
                () -> assertThat(fetchedAfterUpdate.getLastName()).isEqualTo("Doe"),
                () -> assertThat(fetchedAfterUpdate.getHireDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(fetchedAfterUpdate.getBirthDate()).isEqualTo(LocalDate.of(1962, 2, 5)),
                () -> assertThat(fetchedAfterUpdate.getGender()).isEqualTo(Employee.Gender.F)
        );
    }

    @Test
    void testEmployeeServiceSaveAll() {
        final Employee employee1 = new Employee();

        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmployeeNumber(999_999);
        employee1.setHireDate(LocalDate.of(2020, 1, 1));
        employee1.setBirthDate(LocalDate.of(1962, 2, 5));
        employee1.setGender(Employee.Gender.M);

        final Employee employee2 = new Employee();

        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmployeeNumber(999_998);
        employee2.setHireDate(LocalDate.of(2020, 1, 1));
        employee2.setBirthDate(LocalDate.of(1962, 2, 5));
        employee2.setGender(Employee.Gender.F);

        final List<Employee> employees = List.of(employee1, employee2);
        final List<Employee> savedEmployees = new ArrayList<>();

        employeeService.saveAll(employees).forEach(savedEmployees::add);

        assertAll(
                () -> assertThat(savedEmployees).isNotNull(),
                () -> assertThat(savedEmployees).hasSize(2)
        );
    }

    @Test
    void testEmployeeServiceDelete() {
        final Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmployeeNumber(999_999);
        employee.setHireDate(LocalDate.of(2020, 1, 1));
        employee.setBirthDate(LocalDate.of(1962, 2, 5));
        employee.setGender(Employee.Gender.M);

        final Employee saved = employeeService.save(employee);

        assertAll(
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getEmployeeNumber()).isEqualTo(999_999),
                () -> assertThat(saved.getFirstName()).isEqualTo("John"),
                () -> assertThat(saved.getLastName()).isEqualTo("Doe"),
                () -> assertThat(saved.getHireDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(saved.getBirthDate()).isEqualTo(LocalDate.of(1962, 2, 5)),
                () -> assertThat(saved.getGender()).isEqualTo(Employee.Gender.M)
        );

        employeeService.delete(saved);

        assertThat(employeeService.existsById(999_999)).isFalse();
    }

    @Test
    void testEmployeeServiceDeleteById() {
        final Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmployeeNumber(999_999);
        employee.setHireDate(LocalDate.of(2020, 1, 1));
        employee.setBirthDate(LocalDate.of(1962, 2, 5));
        employee.setGender(Employee.Gender.M);

        final Employee saved = employeeService.save(employee);

        assertAll(
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getEmployeeNumber()).isEqualTo(999_999),
                () -> assertThat(saved.getFirstName()).isEqualTo("John"),
                () -> assertThat(saved.getLastName()).isEqualTo("Doe"),
                () -> assertThat(saved.getHireDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(saved.getBirthDate()).isEqualTo(LocalDate.of(1962, 2, 5)),
                () -> assertThat(saved.getGender()).isEqualTo(Employee.Gender.M)
        );

        employeeService.deleteById(999_999);

        assertThat(employeeService.existsById(999_999)).isFalse();
    }

    @Test
    void testEmployeeServiceDeleteAll() {
        final Employee employee1 = new Employee();

        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmployeeNumber(999_999);
        employee1.setHireDate(LocalDate.of(2020, 1, 1));
        employee1.setBirthDate(LocalDate.of(1962, 2, 5));
        employee1.setGender(Employee.Gender.M);

        final Employee employee2 = new Employee();

        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmployeeNumber(999_998);
        employee2.setHireDate(LocalDate.of(2020, 1, 1));
        employee2.setBirthDate(LocalDate.of(1962, 2, 5));
        employee2.setGender(Employee.Gender.F);

        final List<Employee> employees = List.of(employee1, employee2);
        final List<Employee> savedEmployees = new ArrayList<>();

        employeeService.saveAll(employees).forEach(savedEmployees::add);

        assertAll(
                () -> assertThat(savedEmployees).isNotNull(),
                () -> assertThat(savedEmployees).hasSize(2)
        );

        employeeService.deleteAll(employees);

        final long count = employeeService.count();

        assertThat(count).isEqualTo(300_024);
    }

    @Test
    void testEmployeeServiceDeleteAllById() {
        final Employee employee1 = new Employee();

        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmployeeNumber(999_999);
        employee1.setHireDate(LocalDate.of(2020, 1, 1));
        employee1.setBirthDate(LocalDate.of(1962, 2, 5));
        employee1.setGender(Employee.Gender.M);

        final Employee employee2 = new Employee();

        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmployeeNumber(999_998);
        employee2.setHireDate(LocalDate.of(2020, 1, 1));
        employee2.setBirthDate(LocalDate.of(1962, 2, 5));
        employee2.setGender(Employee.Gender.F);

        final List<Employee> employees = List.of(employee1, employee2);
        final List<Employee> savedEmployees = new ArrayList<>();

        employeeService.saveAll(employees).forEach(savedEmployees::add);

        assertAll(
                () -> assertThat(savedEmployees).isNotNull(),
                () -> assertThat(savedEmployees).hasSize(2)
        );

        employeeService.deleteAllById(List.of(999_999, 999_998));

        final long count = employeeService.count();

        assertThat(count).isEqualTo(300_024);
    }
}
