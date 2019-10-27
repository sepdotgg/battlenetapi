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

import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;
import gg.sep.result.Result;

/**
 * An entity from the Battle.net WoW API that contains a {@code key} field. This typically occurs on
 * {@link WoWIndex} items, which are minimal versions of the full item. The {@code key} field contains a
 * {@code href} field, which is a permalink path to the API which will return the full item {@code T}.
 *
 * @param <T> The type of item which will be returned by calling the full URL via {@link #getFullItem()}.
 */
public interface Keyed<T extends BattleNetEntity> extends JsonSerializable {
    /**
     * Returns the key associated with the API entity. The {@code href} on the key
     * can be used by {@link #getFullItem()} to retrieve the full version of the item {@code T} from the API.
     * @return The {@code key} field associated with the API entity.
     */
    WoWKey getKey();

    /**
     * Using the {@link #getKey()} href field, attempts to retrieve the full version of the API entity {@code T}
     * via a call to the Battle.net API.
     *
     * @return An {@link gg.sep.result.Ok} result of the full item {@code T} located at the {@link #getKey()}}
     *         permalink if the API call was successful, otherwise an {@link gg.sep.result.Err} containing
     *         the error message.
     */
    Result<T, String> getFullItem();
}
