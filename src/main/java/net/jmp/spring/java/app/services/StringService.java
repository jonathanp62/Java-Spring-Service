package net.jmp.spring.java.app.services;

/*
 * (#)StringService.java    0.6.0   11/19/2024
 * (#)StringService.java    0.4.0   11/15/2024
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

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/// The string service.
///
/// @version    0.6.0
/// @since      0.4.0
@Service
public class StringService {
    /// The find illegal characters regular expression.
    private static final String FIND_ILLEGAL_CHARACTERS = "^[a-zA-Z0-9_\\-$]*$";

    /// The remove illegal characters regular expression.
    private static final String REMOVE_ILLEGAL_CHARACTERS = "[^a-zA-Z0-9_\\-$]";

    /// The regex pattern used in conjunction with finding illegal characters.
    private static final Pattern ILLEGAL_REGEX_PATTERN = Pattern.compile(FIND_ILLEGAL_CHARACTERS);

    /// The default constructor.
    public StringService() {
        super();
    }

    ///  Return true if the string contains illegal characters.
    ///
    /// @param  input   java.lang.String
    /// @return         boolean
    public boolean isStringLegal(final String input) {
        if (input == null || input.isEmpty()) {
            return false;
        } else {
            return ILLEGAL_REGEX_PATTERN.matcher(input).find();
        }
    }

    /// Return the string with illegal characters removed.
    ///
    /// @param  input   java.lang.String
    /// @return         java.lang.String
    public String removeIllegalCharacters(final String input) {
        if (input == null) {
            return null;
        } else if (input.isEmpty()) {
            return input;
        } else {
            return input.replaceAll(REMOVE_ILLEGAL_CHARACTERS, "");
        }
    }

    /// Return a sanitized string.
    ///
    /// @param  input   java.lang.String
    /// @return         java.lang.String
    public String sanitize(final String input) {
        if (!this.isStringLegal(input)) {
            return this.removeIllegalCharacters(input);
        } else {
            return input;
        }
    }
}
