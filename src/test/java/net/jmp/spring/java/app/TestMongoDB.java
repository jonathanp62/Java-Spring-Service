package net.jmp.spring.java.app;

/*
 * (#)TestMongoDB.java  0.5.0   11/15/2024
 * (#)TestMongoDB.java  0.1.0   11/04/2024
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.jmp.spring.java.app.classes.DemoDocument;

import net.jmp.spring.java.app.repositories.DemoDocumentRepository;

import org.junit.Before;
import org.junit.Test;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.data.mongodb.core.MongoTemplate;

/// The test class for the hello world service bean.
/// Note that because this is not a Spring Boot
/// application autowiring does not work.
///
/// @version    0.5.0
/// @since      0.1.0
public final class TestMongoDB {
    private ApplicationContext context;

    @Before
    public void before() {
        if (this.context == null) {
            this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
    }

    @Test
    public void testMongoTemplate() {
        final MongoTemplate mongoTemplate = this.context.getBean(MongoTemplate.class);
        final List<DemoDocument> documents = mongoTemplate.findAll(DemoDocument.class);

        assertEquals(4, documents.size());

        final DemoDocument document0 = new DemoDocument();

        document0.setId("672a33a932aa022e27e36664");
        document0.setPrice(20);
        document0.setProdId(100);
        document0.setQuantity(125);

        final DemoDocument document1 = new DemoDocument();

        document1.setId("672a33a932aa022e27e36665");
        document1.setPrice(10);
        document1.setProdId(101);
        document1.setQuantity(234);

        final DemoDocument document2 = new DemoDocument();

        document2.setId("672a33a932aa022e27e36666");
        document2.setPrice(15);
        document2.setProdId(102);
        document2.setQuantity(432);

        final DemoDocument document3 = new DemoDocument();

        document3.setId("672a33a932aa022e27e36667");
        document3.setPrice(17);
        document3.setProdId(103);
        document3.setQuantity(320);

        assertTrue(documents.contains(document0));
        assertTrue(documents.contains(document1));
        assertTrue(documents.contains(document2));
        assertTrue(documents.contains(document3));
    }

    @Test
    public void testMongoRepositoryByPrice() {
        final DemoDocumentRepository demoDocumentRepository = this.context.getBean(DemoDocumentRepository.class);
        final List<DemoDocument> documents = demoDocumentRepository.findByPrice(17);

        assertEquals(1, documents.size());
        assertEquals("672a33a932aa022e27e36667", documents.getFirst().getId());
    }

    @Test
    public void testMongoRepositoryByQuantity() {
        final DemoDocumentRepository demoDocumentRepository = this.context.getBean(DemoDocumentRepository.class);
        final List<DemoDocument> documents = demoDocumentRepository.findByQuantity(234);

        assertEquals(1, documents.size());
        assertEquals("672a33a932aa022e27e36665", documents.getFirst().getId());
    }

    @Test
    public void testMongoRepositoryById() {
        final DemoDocumentRepository demoDocumentRepository = this.context.getBean(DemoDocumentRepository.class);
        final Optional<DemoDocument> document = demoDocumentRepository.findById("672a33a932aa022e27e36664");

        assertTrue(document.isPresent());
        assertEquals("672a33a932aa022e27e36664", document.get().getId());
    }

    @Test
    public void testMongoRepositoryByProdId() {
        final DemoDocumentRepository demoDocumentRepository = this.context.getBean(DemoDocumentRepository.class);
        final Optional<DemoDocument> document = demoDocumentRepository.findByProdId(102);

        assertTrue(document.isPresent());
        assertEquals("672a33a932aa022e27e36666", document.get().getId());
    }
}
