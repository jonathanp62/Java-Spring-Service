package net.jmp.spring.java.app;

/*
 * (#)TestMethodSource.java 0.6.0   11/21/2024
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

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/// The test class demonstrating the use of MethodSource.
///
/// @version    0.6.0
/// @since      0.6.0
@DisplayName("Testing method source")
final class TestMethodSource {
    @DisplayName("Test is blank")
    @ParameterizedTest
    @MethodSource("provideStringsForIsNullOrBlankTest")
    void testNullOrBlankStrings(final String input, final boolean expected) {
        assertThat(Strings.isBlank(input)).withFailMessage(() -> "'" + expected + "' is expected").isEqualTo(expected);
    }

    @DisplayName("Test only is blank")
    @ParameterizedTest
    @MethodSource("provideStringsForOnlyIsNullOrBlankTest")
    void testOnlyNullOrBlankStrings(final String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    private static Stream<Arguments> provideStringsForIsNullOrBlankTest() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of(" ", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }

    private static List<String> provideStringsForOnlyIsNullOrBlankTest() {
        final List<String> list = new ArrayList<>();

        list.add(null);
        list.add("");
        list.add(" ");
        list.add("  ");

        return list;
    }

    static class Strings {
        static boolean isBlank(final String input) {
            return input == null || input.trim().isEmpty();
        }
    }
}
