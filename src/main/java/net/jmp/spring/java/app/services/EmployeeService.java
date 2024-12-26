package net.jmp.spring.java.app.services;

/*
 * (#)EmployeeService.java  0.7.0   12/23/2024
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
import java.util.Optional;

import net.jmp.spring.java.app.entities.Employee;

import net.jmp.spring.java.app.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

/// The employee service.
///
/// @version    0.7.0
/// @since      0.7.0
@Service
public class EmployeeService {
    /// The repository.
    @Autowired
    EmployeeRepository employeeRepository;

    /// The default constructor.
    public EmployeeService() {
        super();
    }

    /// Finds all employees.
    ///
    /// @return java.util.List<net.jmp.spring.java.app.entities.Employee>
    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    /// Finds all employees applying the specified sort.
    ///
    /// @return java.util.List<net.jmp.spring.java.app.entities.Employee>
    public List<Employee> findAll(final Sort sort) {
        return this.employeeRepository.findAll(sort);
    }

    /// Finds an employee by identifier.
    ///
    /// @param  employeeNumber  int
    /// @return                 java.util.Optional<net.jmp.spring.java.app.entities.Employee>
    public Optional<Employee> findById(final int employeeNumber) {
        return this.employeeRepository.findById(employeeNumber);
    }

    /// Returns the number of employees in the repository.
    ///
    /// @return long
    public long count() {
        return this.employeeRepository.count();
    }

    /// Returns true if an employee exists in the repository.
    ///
    /// @param  employeeNumber  int
    /// @return                 boolean
    public boolean existsById(final int employeeNumber) {
        return this.employeeRepository.existsById(employeeNumber);
    }

    /// Finds all employees by identifier.
    ///
    /// @param  ids java.lang.Iterable<java.lang.Integer>
    /// @return     java.util.List<net.jmp.spring.java.app.entities.Employee>
    public List<Employee> findAllById(Iterable<Integer> ids) {
        return this.employeeRepository.findAllById(ids);
    }
}
