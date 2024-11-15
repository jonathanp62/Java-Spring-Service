package net.jmp.spring.java.app;

/*
 * (#)DemoDocumentRepository.java   0.5.0   11/15/2024
 * (#)DemoDocumentRepository.java   0.1.0   11/05/2024
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

import net.jmp.spring.java.app.classes.DemoDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/// The demo document repository interface.
///
/// @version    0.5.0
/// @since      0.1.0
public interface DemoDocumentRepository extends MongoRepository<DemoDocument, String> {
    /// Get a document by identifier.
    ///
    /// @param  id  java.lang.String
    /// @return     java.util.Optional<net.jmp.spring.java.app.classes.DemoDocument>
    @Query("{ 'id' :  ?0}")
    Optional<DemoDocument> findById(final String id);

    /// Get a document by production identifier.
    ///
    /// @param  prodId  int
    /// @return         java.util.Optional<net.jmp.spring.java.app.classes.DemoDocument>
    @Query("{ 'prodId' :  ?0}")
    Optional<DemoDocument> findByProdId(final int prodId);

    /// Get a list of documents by price.
    ///
    /// @param  price   int
    /// @return         java.util.List<net.jmp.spring.java.app.classes.DemoDocument>
    @Query("{ 'price' :  ?0}")
    List<DemoDocument> findByPrice(final int price);

    /// Get a list of documents by quantity.
    ///
    /// @param  quantity    int
    /// @return             java.util.List<net.jmp.spring.java.app.classes.DemoDocument>
    @Query("{ 'quantity' :  ?0}")
    List<DemoDocument> findByQuantity(final int quantity);
}
