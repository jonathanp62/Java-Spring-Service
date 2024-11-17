package net.jmp.spring.java.app.demos;

/*
 * (#)RedissonDemo.java 0.5.0   11/15/2024
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

import net.jmp.spring.java.app.AppContext;

import net.jmp.util.extra.demo.Demo;
import net.jmp.util.extra.demo.DemoClass;
import net.jmp.util.extra.demo.DemoVersion;

import static net.jmp.util.logging.LoggerUtils.*;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The Redisson demonstration.
///
/// @version    0.5.0
/// @since      0.5.0
@DemoClass
@DemoVersion(0.5)
public final class RedissonDemo implements Demo {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The default constructor.
    public RedissonDemo() {
        super();
    }

    /// The demo method.
    @Override
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final RedissonClient client = AppContext.getInstance().getApplicationContext().getBean(RedissonClient.class);

        try {
            final RBucket<String> bucket = client.getBucket("my-bucket");

            bucket.set("my-bucket-value");

            final String result = bucket.get();

            assert result != null;
            assert result.equals("my-bucket-value");

            if (this.logger.isInfoEnabled()) {
                this.logger.info(result);
            }

            if (!bucket.delete()) {
                this.logger.warn("Bucket 'my-bucket-value' was not deleted");
            }
        } finally {
            client.shutdown();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
