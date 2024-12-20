package net.jmp.spring.java.app.repositories;

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

import java.util.Optional;

import net.jmp.spring.java.app.classes.Department;

import org.springframework.jdbc.core.JdbcTemplate;

/// The department repository implementation.
///
/// @version    0.7.0
/// @since      0.7.0
public class DepartmentRepositoryImpl implements DepartmentRepository {
    /// The JDBC template.
    private final JdbcTemplate jdbcTemplate;

    /// Constructor.
    ///
    /// @param  jdbcTemplate    org.springframework.jdbc.core.JdbcTemplate
    public DepartmentRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /// Save a department.
    ///
    /// @param  entity  S
    /// @return         S
    @Override
    public <S extends Department> S save(final S entity) {
        return null;
    }

    /// Save an iterable of departments.
    ///
    /// @param  entities    java.lang.Iterable<S>
    /// @return             java.lang.Iterable<S>
    @Override
    public <S extends Department> Iterable<S> saveAll(final Iterable<S> entities) {
        return null;
    }

    /// Find a department by identifier.
    ///
    /// @param  s   java.lang.String
    /// @return     java.util.Optional<net.jmp.spring.java.app.classes.Department>
    @Override
    public Optional<Department> findById(final String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    /// Return all departments.
    ///
    /// @return java.lang.Iterable<net.jmp.spring.java.app.classes.Department>
    @Override
    public Iterable<Department> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM departments ORDER BY dept_no", Department::new);
    }

    /// Return all departments with the given identifiers.
    ///
    /// @param  strings java.lang.Iterable<java.lang.String>
    /// @return         java.lang.Iterable<net.jmp.spring.java.app.classes.Department>
    @Override
    public Iterable<Department> findAllById(final Iterable<String> strings) {
        return null;
    }

    /// Return the number of departments in the repository.
    ///
    /// @return java.lang.long
    @Override
    public long count() {
        return 0;
    }

    /// Delete a department by identifier.
    ///
    /// @param  s   java.lang.String
    @Override
    public void deleteById(final String s) {

    }

    /// Delete a department.
    ///
    /// @param  entity  net.jmp.spring.java.app.classes.Department
    @Override
    public void delete(final Department entity) {

    }

    /// Delete all departments with the given identifiers.
    ///
    /// @param  strings java.lang.Iterable<? extends java.lang.String>
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }

    /// Delete all departments.
    ///
    /// @param  entities    java.lang.Iterable<? extends net.jmp.spring.java.app.classes.Department>
    @Override
    public void deleteAll(final Iterable<? extends Department> entities) {

    }

    /// Delete all departments.
    @Override
    public void deleteAll() {

    }
}
