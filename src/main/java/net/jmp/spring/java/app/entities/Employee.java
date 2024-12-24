package net.jmp.spring.java.app.entities;

/*
 * (#)Employee.java 0.7.0   12/23/2024
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

import jakarta.persistence.*;

import java.time.LocalDate;

/// The employee entity class.
///
/// @version    0.7.0
/// @since      0.7.0
@Entity
@Table(name = "employees")
public class Employee {
    /// A gender enumeration.
    public enum Gender {
        /// Male.
        M,
        /// Female
        F
    }

    /// The employee number.
    @Id
    @Column(name = "emp_no")
    private int employeeNumber;

    /// The date of birth.
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /// The first name.
    @Column(name = "first_name")
    private String firstName;

    /// The last name.
    @Column(name = "last_name")
    private String lastName;

    /// The gender.
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /// The hire date.
    @Column(name = "hire_date")
    private LocalDate hireDate;

    /// The default constructor.
    public Employee() {
        super();
    }

    /// Gets the employee number.
    ///
    /// @return int
    public int getEmployeeNumber() {
        return this.employeeNumber;
    }

    /// Sets the employee number.
    ///
    /// @param  employeeNumber  int
    public void setEmployeeNumber(final int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /// Returns the date of birth.
    ///
    /// @return java.time.LocalDate
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /// Sets the date of birth.
    ///
    /// @param  birthDate  java.time.LocalDate
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /// Gets the first name.
    ///
    /// @return java.lang.String
    public String getFirstName() {
        return this.firstName;
    }

    /// Sets the first name.
    ///
    /// @param  firstName  java.lang.String
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /// Gets the last name.
    ///
    /// @return java.lang.String
    public String getLastName() {
        return this.lastName;
    }

    /// Sets the last name.
    ///
    /// @param  lastName    java.lang.String
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /// Gets the gender.
    ///
    /// @return net.jmp.spring.java.app.entities.Employee.Gender
    public Gender getGender() {
        return this.gender;
    }

    /// Sets the gender.
    ///
    /// @param  gender  net.jmp.spring.java.app.entities.Employee.Gender
    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    /// Returns the hire date.
    ///
    /// @return java.time.LocalDate
    public LocalDate getHireDate() {
        return this.hireDate;
    }

    /// Sets the hire date.
    ///
    /// @param  hireDate  java.time.LocalDate
    public void setHireDate(final LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    /// The to-string method.
    ///
    /// @return java.lang.String
    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + this.employeeNumber +
                ", birthDate=" + this.birthDate +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", gender=" + this.gender +
                ", hireDate=" + this.hireDate +
                '}';
    }
}
