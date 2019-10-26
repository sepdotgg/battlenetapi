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

package gg.sep.battlenet.wow.endpoint;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

import gg.sep.battlenet.wow.model.AbstractWoWIndexItem;

/**
 * Retrofit API interface for use by {@link AbstractWoWIndexItem#getFullItem()}.
 *
 * <p>Since retrofit does not support nested generics in {@link Call}, the {@link #getFullItem(String)} will
 * return a generic Gson {@link JsonElement}, which can then be parsed into the final type by
 * {@link AbstractWoWIndexItem#getFullItem()}.
 */
public interface KeyFullItemEndpoint {
    /**
     * Call the full URL provided by {@code fullUrl} and return a Gson {@link JsonElement} representing the response.
     *
     * @param fullUrl Full URL to {@code GET}.
     * @return Returns a generic Gson {@link JsonElement} representing the response.
     */
    @GET
    Call<JsonElement> getFullItem(@Url String fullUrl);
}
