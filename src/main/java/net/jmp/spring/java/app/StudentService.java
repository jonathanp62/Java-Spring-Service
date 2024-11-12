package net.jmp.spring.java.app;

/*
 * (#)StudentService.java   0.2.0   11/11/2024
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

import org.springframework.stereotype.Service;

/// A student service.
///
/// @version    0.2.0
/// @since      0.2.0
@Service
public class StudentService {
    /// The repository.
    private StudentRepository studentRepository;

    /// The constructor.
    ///
    /// @param  studentRepository   net.jmp.spring.java.app.StudentRepository
    public StudentService(final StudentRepository studentRepository) {
        super();

        this.studentRepository = studentRepository;
    }

    /// Save the student.
    ///
    /// @param  student net.jmp.spring.java.app.Student
    /// @return         net.jmp.spring.java.app.Student
    public Student save(final Student student) {
        return this.studentRepository.save(student);
    }

    /// Find a student by identifier.
    ///
    /// @param  id  java.lang.String
    /// @return     java.util.Optional<net.jmp.spring.java.app.Student>
    public Optional<Student> findById(final String id) {
        return this.studentRepository.findById(id);
    }

    /// Save the student.
    ///
    /// @param  id  java.lang.String
    /// @return     boolean
    public boolean existsById(final String id) {
        return this.studentRepository.existsById(id);
    }

    /// Delete a student based on identifier.
    ///
    /// @param  id  java.lang.String
    public void deleteById(final String id) {
        this.studentRepository.deleteById(id);
    }

    /// Delete a student.
    ///
    /// @param  student net.jmp.spring.java.app.Student
    public void delete(final Student student) {
        this.studentRepository.delete(student);
    }
}
