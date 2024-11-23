package net.jmp.spring.java.app;

/*
 * (#)TestCsvSource.java    0.6.0   11/20/2024
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

import net.jmp.spring.java.app.classes.Person;
import net.jmp.spring.java.app.classes.PersonAggregator;
import net.jmp.spring.java.app.classes.SlashyDateConverter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import org.junit.jupiter.params.converter.ConvertWith;

import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

/// The test class demonstrating the use of CsvSource.
///
/// @version    0.6.0
/// @since      0.6.0
@DisplayName("Testing CSV source")
final class TestCsvSource {
    @DisplayName("Test to upper case")
    @ParameterizedTest
    @CsvSource({ "test,TEST", "tEst,TEST", "Java,JAVA" })
    void testToUpperCase(final String input, final String expected) {
        final String actualValue = input.toUpperCase();

        assertThat(actualValue).withFailMessage(() -> "'" + expected + "' is expected").isEqualTo(expected);
    }

    @DisplayName("Test to lower case")
    @ParameterizedTest
    @CsvSource(value = { "test:test", "tEst:test", "Java:java" }, delimiter = ':' )
    void testToLowerCase(final String input, final String expected) {
        final String actualValue = input.toLowerCase();

        assertThat(actualValue).withFailMessage(() -> "'" + expected + "' is expected").isEqualTo(expected);
    }

    @DisplayName("Test first letter to upper case")
    @ParameterizedTest
    @CsvFileSource(resources = "/test-input.csv", numLinesToSkip = 1)
    void testFirstLetterToUpperCase(final String input, final String expected) {
        final String actualValue = this.firstLettertoUpperCase(input);

        assertThat(actualValue).withFailMessage(() -> "'" + expected + "' is expected").isEqualTo(expected);
    }

    @DisplayName("Test argument converter")
    @ParameterizedTest
    @CsvSource( { "2018/12/25, 2018", "2019/02/11, 2019" } )
    void testArgumentConverter(@ConvertWith(SlashyDateConverter.class) final LocalDate input, final int expected) {
        assertThat(input.getYear()).withFailMessage(() -> "'" + expected + "' is expected").isEqualTo(expected);
    }

    @DisplayName("Test argument accessor")
    @ParameterizedTest
    @CsvSource(value = { "Isaac,,Newton,Isaac Newton", "Charles,Robert,Darwin,Charles Robert Darwin" } )
    void testArgumentAccessor(final ArgumentsAccessor argumentsAccessor) {
        final String firstName = argumentsAccessor.getString(0);
        final String middleName = (String) argumentsAccessor.get(1);
        final String lastName = argumentsAccessor.get(2, String.class);
        final String expectedFullName = argumentsAccessor.getString(3);

        final Person person = new Person(firstName, middleName, lastName);

        assertThat(person.fullName()).withFailMessage(() -> "'" + expectedFullName + "' is expected").isEqualTo(expectedFullName);
    }

    @DisplayName("Test argument aggregation")
    @ParameterizedTest
    @CsvSource(value = { "Virgil Fox,Virgil,,Fox", "Jonathan Martin Parker,Jonathan,Martin,Parker" } )
    void testArgumentAggregation(final String expectedFullName,
                              @AggregateWith(PersonAggregator.class) final Person person) {

        assertThat(person.fullName()).withFailMessage(() -> "'" + expectedFullName + "' is expected").isEqualTo(expectedFullName);
    }

    private String firstLettertoUpperCase(final String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
