package net.jmp.spring.java.app.classes;

/*
 * (#)Person.java   0.6.0   11/23/2024
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

/// The person class used in the argument accessor tests.
///
/// @version    0.6.0
/// @since      0.6.0
public final class Person {
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public Person(final String firstName, final String middleName, final String lastName) {
        super();

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String fullName() {
        if (this.middleName == null || this.middleName.trim().isEmpty()) {
            return String.format("%s %s", this.firstName, this.lastName);
        } else {
            return String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
        }
    }
}
