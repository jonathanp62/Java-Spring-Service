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

    @BeforeEach
    void beforeEach() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
    }

    @Test
    void testDepartmentServiceFinalAll() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);
        final List<Department> departments = new ArrayList<>();

        departmentService.findAll().forEach(departments::add);

        assertThat(departments).isNotNull();
        assertThat(departments.size()).isEqualTo(9);
    }

    @Test
    void testDepartmentServiceFetchAll() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);
        final List<Department> departments = departmentService.fetchAll();

        assertThat(departments).isNotNull();
        assertThat(departments.size()).isEqualTo(9);
    }

    @Test
    void testDepartmentServiceCount() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);
        final long count = departmentService.count();

        assertThat(count).isEqualTo(9);
    }

    @Test
    void testDepartmentServiceExistsById() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);

        boolean exists = departmentService.existsById("not-found");

        assertThat(exists).isFalse();

        exists = departmentService.existsById("d001");

        assertThat(exists).isTrue();
    }

    @Test
    void testDepartmentServiceFindById() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);

        Optional<Department> result = departmentService.findById("not-found");

        assertThat(result).isNotPresent();

        result = departmentService.findById("d001");

        assertThat(result).isPresent();
    }

    @Test
    void testDepartmentServiceFindByName() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);

        Optional<Department> result = departmentService.findByName("not-found");

        assertThat(result).isNotPresent();

        result = departmentService.findByName("Research");

        assertThat(result).isPresent();
    }

    @Test
    void testDepartmentServiceSave() {
        final DepartmentRepository departmentRepository = this.context.getBean(DepartmentRepository.class);

        assertThat(departmentRepository).isNotNull();

        final DepartmentService departmentService = new DepartmentService(departmentRepository);
        final Department newDdepartment = new Department("d999", "Engineering");
        final Department saved = departmentService.save(newDdepartment);

        assertThat(saved).isNotNull();
        assertThat(saved.getNumber()).isEqualTo("d999");
        assertThat(saved.getName()).isEqualTo("Engineering");
    }
}
