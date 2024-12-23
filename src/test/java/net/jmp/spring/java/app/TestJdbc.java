package net.jmp.spring.java.app;

/*
 * (#)TestJdbc.java 0.7.0   121/21/2024
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

import net.jmp.spring.java.app.classes.Department;

import net.jmp.spring.java.app.repositories.DepartmentRepository;

import net.jmp.spring.java.app.services.DepartmentService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/// The test class for the JDBC beans.
/// Note that because this is not a Spring Boot
/// application autowiring does not work.
///
/// @version    0.7.0
/// @since      0.7.0
@DisplayName("JDBC template and MySQL client")
@Tag("JDBC")
final class TestJdbc {
    private ApplicationContext context;
    private DepartmentService departmentService;

    @BeforeEach
    void beforeEach() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }

        if (this.departmentService == null) {
            final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

            this.departmentService = new DepartmentService(departmentRepository);
        }
    }

    @AfterEach
    void afterEach() {
        this.departmentService.delete(new Department("d997", "Motor Pool"));
        this.departmentService.delete(new Department("d998", "Food Services"));
        this.departmentService.deleteById("d999");
    }

    @Test
    void testDepartmentServiceFindAll() {
        final List<Department> departments = new ArrayList<>();

        this.departmentService.findAll().forEach(departments::add);

        assertAll(
                () -> assertThat(departments).isNotNull(),
                () -> assertThat(departments).hasSize(9)
        );
    }

    @Test
    void testDepartmentServiceFetchAll() {
        final List<Department> departments = this.departmentService.fetchAll();

        assertAll(
                () -> assertThat(departments).isNotNull(),
                () -> assertThat(departments).hasSize(9)
        );
    }

    @Test
    void testDepartmentServiceCount() {
        final long count = this.departmentService.count();

        assertThat(count).isEqualTo(9);
    }

    @Test
    void testDepartmentServiceExistsById() {
        boolean exists = this.departmentService.existsById("not-found");

        assertThat(exists).isFalse();

        exists = this.departmentService.existsById("d001");

        assertThat(exists).isTrue();
    }

    @Test
    void testDepartmentServiceFindById() {
        Optional<Department> result = this.departmentService.findById("not-found");

        assertThat(result).isNotPresent();

        result = this.departmentService.findById("d001");

        assertThat(result).isPresent();
    }

    @Test
    void testDepartmentServiceFindByName() {
        Optional<Department> result = this.departmentService.findByName("not-found");

        assertThat(result).isNotPresent();

        result = this.departmentService.findByName("Research");

        assertThat(result).isPresent();
    }

    @Test
    void testDepartmentServiceSaveInsert() {
        final Department newDepartment = new Department("d999", "Engineering");
        final Department _ = this.departmentService.save(newDepartment);
        final Department fetched = this.departmentService.findById("d999").orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(fetched).isNotNull(),
                () -> assertThat(fetched.getNumber()).isNotNull(),
                () -> assertThat(fetched.getNumber()).isEqualTo("d999"),
                () -> assertThat(fetched.getName()).isNotNull(),
                () -> assertThat(fetched.getName()).isEqualTo("Engineering")
        );
    }

    @Test
    void testDepartmentServiceSaveUpdate() {
        final Department newDepartment = new Department("d999", "Engineering");
        final Department saved = this.departmentService.save(newDepartment);
        final Department fetchedAfterInsert = this.departmentService.findById("d999").orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(fetchedAfterInsert).isNotNull(),
                () -> assertThat(fetchedAfterInsert.getNumber()).isNotNull(),
                () -> assertThat(fetchedAfterInsert.getNumber()).isEqualTo("d999"),
                () -> assertThat(fetchedAfterInsert.getName()).isNotNull(),
                () -> assertThat(fetchedAfterInsert.getName()).isEqualTo("Engineering")
        );

        saved.setName("Engineering Services");

        final Department _ = departmentService.save(saved);
        final Department fetchedAfterUpdate = this.departmentService.findById("d999").orElseThrow(() -> new RuntimeException("Not found"));

        assertAll(
                () -> assertThat(fetchedAfterUpdate).isNotNull(),
                () -> assertThat(fetchedAfterUpdate.getNumber()).isNotNull(),
                () -> assertThat(fetchedAfterUpdate.getNumber()).isEqualTo("d999"),
                () -> assertThat(fetchedAfterUpdate.getName()).isNotNull(),
                () -> assertThat(fetchedAfterUpdate.getName()).isEqualTo("Engineering Services")
        );
    }

    @Test
    void testDepartmentServiceSaveAll() {
        final List<Department> newDepartments = List.of(
                new Department("d999", "Engineering"),
                new Department("d998", "Food Services"),
                new Department("d997", "Motor Pool")
        );

        final List<Department> savedDepartments = new ArrayList<>();

        this.departmentService.saveAll(newDepartments).forEach(savedDepartments::add);

        assertAll(
                () -> assertThat(savedDepartments).isNotNull(),
                () -> assertThat(savedDepartments).hasSize(3)
        );
    }

    @Test
    void testDepartmentServiceDelete() {
        final Department newDepartment = new Department("d998", "Food Services");
        final Department saved = this.departmentService.save(newDepartment);

        assertAll(
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(this.departmentService.existsById("d998")).isTrue()
        );

        this.departmentService.delete(saved);

        assertThat(this.departmentService.existsById("d998")).isFalse();
    }

    @Test
    void testDepartmentServiceDeleteById() {
        final Department newDepartment = new Department("d998", "Food Services");
        final Department saved = this.departmentService.save(newDepartment);

        assertAll(
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(this.departmentService.existsById("d998")).isTrue()
        );

        this.departmentService.deleteById("d998");

        assertThat(this.departmentService.existsById("d998")).isFalse();
    }
}
