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

import java.lang.reflect.Field;

import java.util.stream.Stream;

import net.jmp.spring.java.app.annotations.VariableSource;

import org.junit.jupiter.api.extension.ExtensionContext;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import org.junit.jupiter.params.support.AnnotationConsumer;

/// The class that provides variable arguments annotated with @VariableSource.
///
/// @version    0.6.0
/// @since      0.6.0
public final class VariableArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<VariableSource> {
    /// The name of the variable.
    private String variableName;

    @Override
    public void accept(final VariableSource variableSource) {
        this.variableName = variableSource.value();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return context.getTestClass()
                .map(this::getField)
                .map(this::getValue)
                .orElseThrow(() -> new IllegalArgumentException("Failed to load test arguments"));
    }

    private Field getField(Class<?> clazz) {
        try {
            return clazz.getDeclaredField(this.variableName);
        } catch (final Exception e) {
            e.printStackTrace(System.err);

            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Stream<Arguments> getValue(final Field field) {
        Object value = null;

        try {
            value = field.get(null);    // Null specified for static fields
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }

        return value == null ? null : (Stream<Arguments>) value;
    }
}
