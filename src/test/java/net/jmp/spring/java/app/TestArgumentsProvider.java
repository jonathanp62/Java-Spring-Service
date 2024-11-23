package net.jmp.spring.java.app;

/*
 * (#)TestArgumentsProvider.java    0.6.0   11/23/2024
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

import java.util.stream.Stream;

import net.jmp.spring.java.app.annotations.VariableSource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.extension.ExtensionContext;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/// The test class demonstrating the use of a custom argument provider.
/// This class must be public so that the public static field 'arguments'
/// is accessible to the variable arguments provider.
///
/// @version    0.6.0
/// @since      0.6.0
@DisplayName("Testing custom arguments provider and variable source")
public final class TestArgumentsProvider {
    public static Stream<Arguments> arguments = Stream.of(
            Arguments.of(6),
            Arguments.of(7),
            Arguments.of(8),
            Arguments.of(9),
            Arguments.of(10)
    );

    @DisplayName("Test custom arguments provider")
    @ParameterizedTest
    @ArgumentsSource(NumericArgumentsProvider.class)
    void testArgumentsProvider(final int number) {
        assertThat(number).withFailMessage(() -> "'" + number + "' is expected").isBetween(1, 5);
    }

    @DisplayName("Test variable source")
    @ParameterizedTest
    @VariableSource("arguments")
    void testVariableSource(final int number) {
        assertThat(number).withFailMessage(() -> "'" + number + "' is expected").isBetween(6, 10);
    }

    static class NumericArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(1),
                    Arguments.of(2),
                    Arguments.of(3),
                    Arguments.of(4),
                    Arguments.of(5)
            );
        }
    }
}
