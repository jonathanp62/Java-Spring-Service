package net.jmp.spring.java.app;

/*
 * (#)TestEnumSource.java   0.6.0   11/20/2024
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

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.EnumSource;

/// The test class demonstrating the use of EnumSource.
///
/// @version    0.6.0
/// @since      0.6.0
@DisplayName("Testing enum source")
final class TestEnumSource {
    @DisplayName("Test month value")
    @ParameterizedTest
    @EnumSource(Month.class)
    void testMonthValue(final Month month) {
        final int monthNumber = month.getValue();

        assertThat(monthNumber).isIn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }
}
