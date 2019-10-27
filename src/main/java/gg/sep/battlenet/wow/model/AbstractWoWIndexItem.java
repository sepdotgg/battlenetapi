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
import java.util.Optional;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;

import gg.sep.battlenet.model.AbstractBattleNetEntity;
import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;
import gg.sep.battlenet.wow.endpoint.KeyFullItemEndpoint;

/**
 * Abstract implementation of {@link WoWIndexItem}, which implements a base helper method that
 * sub-classes can use in their own {@link #getFullItem()} implementation.
 *
 * @param <T> The type of the full item that will be returned by a call to {@link Keyed#getFullItem()}.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractWoWIndexItem<T extends BattleNetEntity> extends AbstractBattleNetEntity
    implements WoWIndexItem<T>, JsonSerializable {

    private WoWKey key;
    private String name;

    /**
     * Helper method that sub-classes can use in their own {@link #getFullItem()} implementation
     * to return the full version of an API item.
     *
     * <p>This method performs a {@code GET} request to the full URL contained in {@link #getKey()}
     * and converts it to the model specified by {@code T}.
     *
     * @param clazz Class of {@code T} which will be used to parse the API response.
     * @return Optional of {@code T} if the API call was successful, otherwise an empty Optional.
     */
    protected Optional<T> getFullItem(final Class<T> clazz) {
        final URL fullUrl = key.getHref();
        final KeyFullItemEndpoint endpoint = getBattleNet().getRetrofit().create(KeyFullItemEndpoint.class);

        final Call<JsonElement> call = endpoint.getFullItem(fullUrl.toExternalForm());
        try {
            final JsonElement jsonElement = call.execute().body();
            final T entity = getBattleNet().getJsonParser().fromJson(jsonElement, clazz);
            return Optional.ofNullable(entity);
        } catch (final Exception e) {
            return Optional.empty();
        }
    }
}
