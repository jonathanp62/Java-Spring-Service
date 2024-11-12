package net.jmp.spring.java.app;

/*
 * (#)StudentRepositoryImpl.java    0.2.0   11/11/2024
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

import org.springframework.data.redis.core.RedisTemplate;

/// A student repository implementation.
///
/// @version    0.2.0
/// @since      0.2.0
public class StudentRepositoryImpl implements StudentRepository {
    /// The Redis template.
    private final RedisTemplate<String, Student> redisTemplate;

    /// The constructor.
    ///
    /// @param  redisTemplate   org.springframework.data.redis.core.RedisTemplate
    public StudentRepositoryImpl(final RedisTemplate<String, Student> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /// Save a student.
    ///
    /// @param  <S>     The student type.
    /// @param  student S
    /// @return         S
    @Override
    public <S extends Student> S save(final S student) {
        this.redisTemplate.opsForValue().set(student.getId(), student);

        return student;
    }

    @Override
    public <S extends Student> Iterable<S> saveAll(Iterable<S> students) {
        throw new UnsupportedOperationException();
    }

    /// Find a student by identifier.
    ///
    /// @param  id  java.lang.String
    /// @return     java.util.Optional<net.jmp.spring.java.app.Student>
    @Override
    public Optional<Student> findById(final String id) {
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(id));
    }

    /// Return true if a student exists.
    ///
    /// @param  id  java.lang.String
    /// @return     boolean
    @Override
    public boolean existsById(final String id) {
        return this.redisTemplate.opsForValue().get(id) != null;
    }

    @Override
    public Iterable<Student> findAll() {
        return null;
    }

    @Override
    public Iterable<Student> findAllById(Iterable<String> identifiers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    /// Delete a student based on identifier.
    ///
    /// @param  id  java.lang.String
    @Override
    public void deleteById(final String id) {
        this.redisTemplate.delete(id);
    }

    /// Delete a student.
    ///
    /// @param  student net.jmp.spring.java.app.Student
    @Override
    public void delete(final Student student) {
        this.redisTemplate.delete(student.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> identifiers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Student> students) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
