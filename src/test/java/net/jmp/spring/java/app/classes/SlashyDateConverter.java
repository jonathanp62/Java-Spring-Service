package net.jmp.spring.java.app.classes;

/*
 * (#)VariableArgumentsProvider.java    0.6.0   11/23/2024
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

import java.time.LocalDate;

import org.junit.jupiter.api.extension.ParameterContext;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

/// The class that provides conversion from a date
/// with slashes (01/02/2024) to a local date object.
///
/// @version    0.6.0
/// @since      0.6.0
public final class SlashyDateConverter implements ArgumentConverter {
    @Override
    public Object convert(final Object source, final ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new IllegalArgumentException(
                    "The argument should be a string: " + source);
        }

        try {
            final String[] parts = ((String) source).split("/");

            final int year = Integer.parseInt(parts[0]);
            final int month = Integer.parseInt(parts[1]);
            final int day = Integer.parseInt(parts[2]);

            return LocalDate.of(year, month, day);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Failed to convert", e);
        }
    }
}
