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

package gg.sep.battlenet.model;

import com.google.gson.Gson;

/**
 * A base class for representing Json-serializable models used by the Battle.net API.
 *
 * Implements all of the necessary type adapters for this class's subclasses, and implements the
 * {@link JsonSerializable#toJson()} methods at a basic level.
 */
public abstract class AbstractJsonEntity implements JsonSerializable {

    /**
     * Returns a new instance of {@link Gson} which is appropriate for serializing/deserializing operations.
     * @return A new instance of {@link Gson} which is appropriate for serializing/deserializing operations.
     */
    public static Gson defaultGson() {
        return new Gson();
    }

    /**
     * {@inheritDoc}
     */
    public String toJson() {
        return defaultGson()
            .toJson(this);
    }
}
