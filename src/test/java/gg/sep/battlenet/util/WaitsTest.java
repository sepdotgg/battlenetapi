/*
 * Copyright (c) 2019 sep.gg <seputaes@sep.gg>
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

package gg.sep.battlenet.util;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Waits}.
 */
public class WaitsTest {
    @Test
    void testSimpleSleepWaits() {
        final long waitTimeMs = 5;
        final long currTime = System.currentTimeMillis();
        Waits.simpleSleep(5);
        Assertions.assertTrue(System.currentTimeMillis() - waitTimeMs >= currTime);
    }

    @Test
    void testSimpleSleepInterrupts() {
        // if the thread throws an exception, this boolean will be set to true.
        final AtomicBoolean exceptionThrown = new AtomicBoolean(false);
        final Runnable simpleSleepRunnable = () -> {
            try {
                Waits.simpleSleep(1);
            } catch (final Throwable t) {
                exceptionThrown.set(true);
            }
        };
        final Thread thread = new Thread(simpleSleepRunnable);
        thread.start();
        thread.interrupt();
        Assertions.assertTrue(thread.isInterrupted());
        Assertions.assertFalse(exceptionThrown.get());
    }
}
