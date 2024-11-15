package net.jmp.spring.java.app;

/*
 * (#)DemoDocument.java 0.5.0   11/15/2024
 * (#)DemoDocument.java 0.2.0   11/10/2024
 * (#)DemoDocument.java 0.1.0   11/05/2024
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

import java.util.Objects;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/// A MongoDB document class from the demo collection.
///
/// @version    0.5.0
/// @since      0.1.0
@Document(collection = "demo")
public class DemoDocument {
    /// The identifier.
    @Id
    private String id;

    /// The production identifier.
    private int prodId;

    /// The price.
    private int price;

    /// The quantity.
    private int quantity;

    /// The default constructor.
    public DemoDocument() {
        super();
    }

    /// Get the identifier.
    ///
    /// @return java.lang.String
    public String getId() {
        return this.id;
    }

    /// Set the identifier.
    ///
    /// @param  id  java.lang.String
    public void setId(final String id) {
        this.id = id;
    }

    /// Get the production identifier.
    ///
    /// @return int
    public int getProdId() {
        return this.prodId;
    }

    /// Set the production identifier.
    ///
    /// @param  prodId  int
    public void setProdId(final int prodId) {
        this.prodId = prodId;
    }

    /// Get the price.
    ///
    /// @return int
    public int getPrice() {
        return this.price;
    }

    /// Set the price.
    ///
    /// @param  price   int
    public void setPrice(final int price) {
        this.price = price;
    }

    /// Get the quantity.
    ///
    /// @return int
    public int getQuantity() {
        return this.quantity;
    }

    /// Set the quantity.
    ///
    /// @param  quantity    int
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    /// The equals methods.
    ///
    /// @param  o   java.lang.Object
    /// @return     boolean
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final DemoDocument demoDocument = (DemoDocument) o;

        return this.prodId == demoDocument.prodId && this.price == demoDocument.price && this.quantity == demoDocument.quantity && Objects.equals(this.id, demoDocument.id);
    }

    /// The hash-code method.
    ///
    /// @return int
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.prodId, this.price, this.quantity);
    }

    /// The to-string method.
    ///
    /// @return java.lang.String
    @Override
    public String toString() {
        return "DemoDocument{" +
                "id='" + this.id + '\'' +
                ", prodId=" + this.prodId +
                ", price=" + this.price +
                ", quantity=" + this.quantity +
                '}';
    }
}
