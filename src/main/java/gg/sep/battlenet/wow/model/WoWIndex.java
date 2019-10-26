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

import java.util.List;

import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;

/**
 * Represents an <em>index</em> API response from the Battle.net WoW API.
 *
 * <p>The entity will contains any number of {@code T} index items in the {@link #getItems()} field.
 *
 * <p>Each index response will have a different field name for the items. Implementing classes should
 * ensure that field correctly maps to {@link #getItems()}.
 *
 * @param <T> The type of index item that is contained in the index response.
 */
public interface WoWIndex<T extends WoWIndexItem> extends BattleNetEntity, JsonSerializable {
    /**
     * Returns the list of index items contained on the index entity response.
     * @return List of index items contained on the index entity response.
     */
    List<T> getItems();
}
