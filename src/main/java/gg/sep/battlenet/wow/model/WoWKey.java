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

package gg.sep.battlenet.wow.model;

import java.net.URL;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.battlenet.model.AbstractBattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;
import gg.sep.battlenet.wow.endpoint.KeyFullItemEndpoint;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Contains a permalink to the full version of a {@link gg.sep.battlenet.model.BattleNetEntity}
 * for the WoW Battle.net API. Making an API call to the URL in {@link #getHref()} will produce
 * the full entity.
 *
 * @param <T> The type of item that is contained in the key response.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Log4j2
public class WoWKey<T> extends AbstractBattleNetEntity implements JsonSerializable {
    private URL href;

    /**
     * Helper method that classes which contain a WoWKey can use in their own {@code getFullItem()} implementation
     * to return the full version of an API item.
     *
     * <p>This method performs a {@code GET} request to the full URL contained in {@link #getHref()}
     * and converts it to the model specified by {@code T}.
     *
     * @param clazz Class of {@code T} which will be used to parse the API response.
     * @return {@link Ok} result containing of {@code T} if the API call was successful, otherwise an {@link Err}
     *         containing the error message.
     */
    protected Result<T, String> getItem(final Class<T> clazz) {
        final KeyFullItemEndpoint endpoint = getBattleNet().getRetrofit().create(KeyFullItemEndpoint.class);
        final Call<JsonElement> call = endpoint.getFullItem(href.toExternalForm());
        try {
            final JsonElement jsonElement = call.execute().body();
            final T entity = getBattleNet().getJsonParser().fromJson(jsonElement, clazz);
            return nullableResult(entity, "Error retrieving the full item %s", call.request().url());
        } catch (final Exception e) {
            log.error(e);
            return nullableResult(null, "Exception when attempting to get Full Item. url=%s, exception=%s",
                call.request().url(), e);
        }
    }

    private Result<T, String> nullableResult(final T ok, final String err, final Object... args) {
        return (ok == null) ? Err.of(String.format(err, args)) : Ok.of(ok);
    }
}
