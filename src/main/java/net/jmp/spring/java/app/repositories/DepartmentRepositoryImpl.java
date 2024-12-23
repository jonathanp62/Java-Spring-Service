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

import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

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
    /// @param  entity  S extends net.jmp.spring.java.app.classes.Department
    /// @return         S extends net.jmp.spring.java.app.classes.Department
    @Override
    public <S extends Department> S save(final S entity) {
        final int rowsAffected = this.jdbcTemplate.update("INSERT INTO departments (dept_no, dept_name) VALUES (?, ?) ON DUPLICATE KEY UPDATE dept_name = ?", entity.getNumber(), entity.getName(), entity.getName());

        // If the number of rows affected is 1 then it is an insert, if it is 2 then it is an update

        if (rowsAffected == 1 || rowsAffected == 2) {
            return entity;
        } else {
            throw new RuntimeException("Failed to save department: " + entity);
        }
    }

    /// Save an iterable of departments.
    ///
    /// @param  entities    java.lang.Iterable<S extends net.jmp.spring.java.app.classes.Department>
    /// @return             java.lang.Iterable<S extends net.jmp.spring.java.app.classes.Department>
    @Override
    public <S extends Department> Iterable<S> saveAll(final Iterable<S> entities) {
        entities.forEach(this::save);

        return entities;
    }

    /// Find a department by identifier.
    ///
    /// @param  s   java.lang.String
    /// @return     java.util.Optional<net.jmp.spring.java.app.classes.Department>
    @Override
    public Optional<Department> findById(final String s) {
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("dept_no", s);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM departments WHERE dept_no = :dept_no", namedParameters, Department::new));
        } catch (final EmptyResultDataAccessException _) {
            return Optional.empty();
        }
    }

    /// Returns a department by name if found.
    ///
    /// @param  name    java.lang.String
    /// @return         java.util.Optional<net.jmp.spring.java.app.classes.Department>
    @Override
    public Optional<Department> findByName(final String name) {
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("dept_name", name);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM departments WHERE dept_name = :dept_name", namedParameters, Department::new));
        } catch (final EmptyResultDataAccessException _) {
            return Optional.empty();
        }
    }

    /// Return true if a department exists.
    ///
    /// @param  s   java.lang.String
    /// @return     boolean
    @Override
    public boolean existsById(final String s) {
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("dept_no", s);

        final Integer count = namedParameterJdbcTemplate.queryForObject("SELECT COUNT(*) FROM departments WHERE dept_no = :dept_no", namedParameters, Integer.class);

        if (count == null) {
            return false;
        } else {
            return count > 0;
        }
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
        final Long count = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM departments", Long.class);

        return (count == null) ? 0 : count;
    }

    /// Delete a department by identifier.
    ///
    /// @param  s   java.lang.String
    @Override
    public void deleteById(final String s) {
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("dept_no", s);
        final int _ = namedParameterJdbcTemplate.update("DELETE FROM departments WHERE dept_no = :dept_no", namedParameters);
    }

    /// Delete a department.
    ///
    /// @param  department  net.jmp.spring.java.app.classes.Department
    @Override
    public void delete(final Department department) {
        final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("dept_no", department.getNumber());
        final int _ = namedParameterJdbcTemplate.update("DELETE FROM departments WHERE dept_no = :dept_no", namedParameters);
    }

    /// Delete all departments with the given identifiers.
    ///
    /// @param  strings java.lang.Iterable<? extends java.lang.String>
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
        strings.forEach(this::deleteById);
    }

    /// Delete all departments.
    ///
    /// @param  entities    java.lang.Iterable<? extends net.jmp.spring.java.app.classes.Department>
    @Override
    public void deleteAll(final Iterable<? extends Department> entities) {
        entities.forEach(this::delete);
    }

    /// Delete all departments.
    @Override
    public void deleteAll() {
        final int _ = this.jdbcTemplate.update("DELETE FROM departments");
    }
}
