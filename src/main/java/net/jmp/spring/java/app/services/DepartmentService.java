package net.jmp.spring.java.app.services;

/*
 * (#)DepartmentRepositoryImpl.java 0.7.0   12/20/2024
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.jmp.spring.java.app.classes.Department;

import net.jmp.spring.java.app.repositories.DepartmentRepository;

import org.springframework.stereotype.Service;

/// The department service.
///
/// @version    0.7.0
/// @since      0.7.0
@Service
public class DepartmentService {
    /// The repository.
    private final DepartmentRepository departmentRepository;

    /// The constructor.
    ///
    /// @param  departmentRepository    net.jmp.spring.java.app.repositories.DepartmentRepository
    public DepartmentService(final DepartmentRepository departmentRepository) {
        super();

        this.departmentRepository = departmentRepository;
    }

    /// Get all departments.
    ///
    /// @return java.lang.Iterable<net.jmp.spring.java.app.classes.Department>
    public Iterable<Department> findAll() {
        return this.departmentRepository.findAll();
    }

    /// Fetch all departments.
    ///
    /// @return java.util.List<net.jmp.spring.java.app.classes.Department>
    public List<Department> fetchAll() {
        final List<Department> departments = new ArrayList<>();

        this.findAll().forEach(departments::add);

        return departments;
    }

    /// Return the number of departments in the repository.
    ///
    /// @return long
    public long count() {
        return this.departmentRepository.count();
    }

    /// Return true if a department exists by identifier.
    ///
    /// @param  id  java.lang.String
    /// @return     boolean
    public boolean existsById(final String id) {
        return this.departmentRepository.existsById(id);
    }

    /// Find a department by identifier.
    ///
    /// @param  id  java.lang.String
    /// @return     java.util.Optional<net.jmp.spring.java.app.classes.Department>
    public Optional<Department> findById(final String id) {
        return this.departmentRepository.findById(id);
    }

    /// Find a department by name.
    ///
    /// @param  name    java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.java.app.classes.Department>
    public Optional<Department> findByName(final String name) {
        return this.departmentRepository.findByName(name);
    }

    /// Save the department.
    ///
    /// @param  department  net.jmp.spring.java.app.classes.Department
    /// @return             net.jmp.spring.java.app.classes.Department
    public Department save(final Department department) {
        return this.departmentRepository.save(department);
    }
}
