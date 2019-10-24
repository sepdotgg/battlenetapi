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

package gg.sep.battlenet;

import okhttp3.mockwebserver.MockResponse;

import gg.sep.battlenet.model.JsonSerializable;

/**
 * Utility builder class for {@code MockWebServer}'s {@link MockResponse} object.
 */
public class MockResponseBuilder {
    private MockResponse mockResponse = new MockResponse();

    /**
     * Marks the response as an HTTP success.
     *
     * @return The {@link MockResponseBuilder} instance.
     */
    public MockResponseBuilder success() {
        mockResponse.setResponseCode(200);
        return this;
    }

    /**
     * Marks the response as a JSON response, and sets the specified json entity as the response body.
     *
     * @param jsonEntity Serializable JSON entity which be returned in the response.
     * @return The {@link MockResponseBuilder} instance.
     */
    public MockResponseBuilder json(final JsonSerializable jsonEntity) {
        final String body = (jsonEntity == null) ? "null" : jsonEntity.toJson();
        mockResponse.setHeader("Content-Type", "application/json");
        mockResponse.setBody(body);
        return this;
    }

    /**
     * Returns the built {@link MockResponse}.
     * @return The built {@link MockResponse}.
     */
    public MockResponse build() {
        return mockResponse;
    }
}
