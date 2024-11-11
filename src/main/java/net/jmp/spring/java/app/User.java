package net.jmp.spring.java.app;

/*
 * (#)User.java 0.2.0   11/09/2024
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

import java.util.Objects;

/// A user class.
///
/// @version    0.2.0
/// @since      0.2.0
public final class User {
    /// The user identifier.
    private String id;

    /// The user name.
    private String userName;

    /// The first name.
    private String firstName;

    /// The last name.
    private String lastName;

    /// The password.
    private String password;

    /// The default constructor.
    User() {
        super();
    }

    /// Get the user identifier.
    ///
    /// @return java.lang.String
    public String getId() {
        return this.id;
    }

    /// Set the user identifier.
    ///
    /// @param  id  java.lang.String
    public void setId(final String id) {
        this.id = id;
    }

    /// Get the user name.
    ///
    /// @return java.lang.String
    public String getUserName() {
        return this.userName;
    }

    /// Set the user name.
    ///
    /// @param  userName    java.lang.String
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /// Get the first name.
    ///
    /// @return java.lang.String
    public String getFirstName() {
        return this.firstName;
    }

    /// Set the first name.
    ///
    /// @param  firstName   java.lang.String
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /// Get the last name.
    ///
    /// @return java.lang.String
    public String getLastName() {
        return this.lastName;
    }

    /// Set the last name.
    ///
    /// @param  lastName    java.lang.String
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /// Get the password.
    ///
    /// @return java.lang.String
    public String getPassword() {
        return this.password;
    }

    /// Set the password.
    ///
    /// @param  password    java.lang.String
    public void setPassword(final String password) {
        this.password = password;
    }

    /// The equals method.
    ///
    /// @param  o   java.lang.Object
    /// @return     boolean
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.userName, user.userName) &&
                Objects.equals(this.firstName, user.firstName) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.password, user.password);
    }

    /// The hash-code method.
    ///
    /// @return int
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.userName, this.firstName, this.lastName, this.password);
    }

    /// The to-string method.
    ///
    /// @return java.lang.String
    @Override
    public String toString() {
        return "User{" +
                "id='" + this.id + '\'' +
                ", userName='" + this.userName + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", password='" + this.password + '\'' +
                '}';
    }
}
