package net.jmp.spring.java.app.demos;

/*
 * (#)MongoRepositoryDemo.java  0.5.0   11/15/2024
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

import java.util.List;
import java.util.Optional;

import net.jmp.spring.java.app.Main;

import net.jmp.spring.java.app.classes.DemoDocument;

import net.jmp.spring.java.app.repositories.DemoDocumentRepository;

import net.jmp.util.extra.demo.Demo;
import net.jmp.util.extra.demo.DemoClass;
import net.jmp.util.extra.demo.DemoVersion;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The MongoDB repository demonstration.
///
/// @version    0.5.0
/// @since      0.5.0
@DemoClass
@DemoVersion(0.5)
public final class MongoRepositoryDemo implements Demo {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The default constructor.
    public MongoRepositoryDemo() {
        super();
    }

    /// The demo method.
    @Override
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final DemoDocumentRepository repository = Main.APPLICATION_CONTEXT.getBean(DemoDocumentRepository.class);
        final List<DemoDocument> documentsByPrice = repository.findByPrice(17);

        documentsByPrice.forEach(document -> this.logger.info(document.toString()));

        final List<DemoDocument> documentsByQuantity = repository.findByQuantity(234);

        documentsByQuantity.forEach(document -> this.logger.info(document.toString()));

        final Optional<DemoDocument> documentById = repository.findById("672a33a932aa022e27e36664");

        documentById.ifPresent(document -> this.logger.info(document.toString()));

        final Optional<DemoDocument> documentByProdId = repository.findByProdId(102);

        documentByProdId.ifPresent(document -> this.logger.info(document.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
