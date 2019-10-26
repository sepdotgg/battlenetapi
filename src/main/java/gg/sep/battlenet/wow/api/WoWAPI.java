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

package gg.sep.battlenet.wow.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.api.BattleNetAPI;
import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.battlenet.wow.model.WoWIndex;
import gg.sep.battlenet.wow.model.WoWIndexItem;

/**
 * A subclass of {@link BattleNetAPI} which implements WoW-API specific
 * helper methods.
 */
public abstract class WoWAPI extends BattleNetAPI {

    /**
     * Creates an instance of the WoWAPI using the specified {@link BattleNet} client.
     * @param battleNet Battle.net API client to associate with the WoW API.
     */
    public WoWAPI(final BattleNet battleNet) {
        super(battleNet);
    }

    /**
     * A modified version of {@link #executeCall(Call)} that's designed for {@link WoWIndex} API endpoints.
     *
     * It is a wrapper around {@link #executeCall(Call)} which assigns the instance of {@link BattleNet} to each
     * of the child items contained in the {@link WoWIndex#getItems()} field.
     *
     * @param call Retrofit call which will return a {@code I} {@link WoWIndex} API response.
     * @param <I> Type of index API response to expect from the raw API call.
     * @param <E> Type of index items that are contained on the {@code items} field of the index response.
     * @param <T> Type of full entity versions of the index items for the {@link WoWIndexItem}.
     * @return List of {@link WoWIndexItem}s contained in the index if the API call was successful,
     *         otherwise an empty list.
     */
    protected <I extends WoWIndex<E>, E extends WoWIndexItem<T>, T extends BattleNetEntity> List<E> executeIndexCall(
        final Call<I> call) {

        final Optional<I> indexResponse = executeCall(call);
        if (!indexResponse.isPresent()) {
            return Collections.emptyList();
        }
        final List<E> items = indexResponse.get().getItems();
        final BattleNet thisBattleNet = getBattleNet();
        items.forEach(i -> i.setBattleNet(thisBattleNet));
        return items;
    }
}
