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

/**
 * Represents an <em>index item</em> that is part of a {@link WoWIndex} Battle.net WoW API index response.
 *
 * <p>The index item typically represents a "minimal" version of another full API entity. This
 * full item can be accessed by an additional API call to the URL stored on {@link Keyed#getKey()}
 * via the implementing {@link Keyed#getFullItem()} method.
 *
 * <p>The type of this full item is designated by {@code T}.
 * @param <T> The type of the full item that will be returned by a call to {@link Keyed#getFullItem()}.
 */
public interface WoWIndexItem<T extends BattleNetEntity> extends BattleNetEntity, JsonSerializable, Keyed<T> {
    /**
     * The name associated with the index item.
     * @return The name associated with the index item.
     */
    String getName();
}
