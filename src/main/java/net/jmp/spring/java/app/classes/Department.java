package net.jmp.spring.java.app.classes;

/*
 * (#)Department.java   0.7.0   12/20/2024
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

import org.springframework.data.annotation.Id;

import java.sql.ResultSet;
import java.sql.SQLException;

/// The department entity.
///
/// @version    0.7.0
/// @since      0.7.0
public class Department {
    /// The department number.
    @Id
    private String number;

    /// The department name.
    private String name;

    /// The default constructor.
    public Department() {
        super();
    }

    /// A constructor.
    ///
    /// @param  number  java.lang.String
    /// @param  name    java.lang.String
    public Department(final String number, final String name) {
        this.number = number;
        this.name = name;
    }

    /// A constructor.
    ///
    /// @param  resultSet   java.sql.ResultSet
    /// @param  rowNum      int
    /// @throws             java.sql.SQLException   When a SQL exception occurs
    public Department(final ResultSet resultSet, final int rowNum) throws SQLException  {
        this.number = resultSet.getString("dept_no");
        this.name = resultSet.getString("dept_name");
    }

    /// Returns the department name.
    ///
    /// @return java.lang.String
    public String getName() {
        return this.name;
    }

    /// Sets the department name.
    ///
    /// @param  name    java.lang.String
    public void setName(final String name) {
        this.name = name;
    }

    /// Returns the department number.
    ///
    /// @return java.lang.String
    public String getNumber() {
        return this.number;
    }

    /// Sets the department number.
    ///
    /// @param  number  java.lang.String
    public void setNumber(final String number) {
        this.number = number;
    }

    /// The to-string method.
    ///
    /// @return java.lang.String
    @Override
    public String toString() {
        return "Department{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
