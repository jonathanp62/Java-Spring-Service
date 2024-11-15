package net.jmp.spring.java.app.classes;

/*
 * (#)Student.java  0.5.0   11/15/2024
 * (#)Student.java  0.2.0   11/11/2024
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

import java.io.Serializable;

import java.util.Objects;

import org.springframework.data.redis.core.RedisHash;

/// A student class.
///
/// @version    0.5.0
/// @since      0.2.0
@RedisHash
public class Student implements Serializable {
    /// An enumeration of genders.
    public enum Gender {
        /// Female
        FEMALE,

        /// Male
        MALE
    }

    /// The identifier.
    private String id;

    /// The name
    private String name;

    /// The gender
    private Gender gender;

    /// The grade.
    private int grade;

    /// The default constructor.
    public Student() {
        super();
    }

    /// Get the identifier.
    ///
    /// @return java.lang.String
    public String getId() {
        return this.id;
    }

    /// Set the identifier.
    ///
    /// @param  id  java.lang.String
    public void setId(final String id) {
        this.id = id;
    }

    /// Get the name.
    ///
    /// @return java.lang.String
    public String getName() {
        return this.name;
    }

    /// Set the name.
    ///
    /// @param  name    java.lang.String
    public void setName(final String name) {
        this.name = name;
    }

    /// Get the gender.
    ///
    /// @return net.jmp.spring.java.app.classes.Student.Gender
    public Gender getGender() {
        return this.gender;
    }

    /// Set the gender.
    ///
    /// @param  gender  net.jmp.spring.java.app.classes.Student.Gender
    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    /// Get the grade.
    ///
    /// @return int
    public int getGrade() {
        return this.grade;
    }

    /// Set the grade.
    ///
    /// @param  grade   int
    public void setGrade(final int grade) {
        this.grade = grade;
    }

    /// The equals method.
    ///
    /// @param  o   java.lang.Object
    /// @return     boolean
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Student student = (Student) o;

        return this.grade == student.grade &&
                Objects.equals(this.id, student.id) &&
                Objects.equals(this.name, student.name) &&
                this.gender == student.gender;
    }

    /// The hash-code method.
    ///
    /// @return int
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.gender, this.grade);
    }

    /// The to-string method.
    ///
    /// @return java.lang.String
    @Override
    public String toString() {
        return "Student{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", gender=" + this.gender +
                ", grade=" + this.grade +
                '}';
    }
}
