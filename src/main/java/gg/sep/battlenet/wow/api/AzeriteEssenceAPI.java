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

import java.util.List;

import retrofit2.Call;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.wow.endpoint.AzeriteEssenceEndpoint;
import gg.sep.battlenet.wow.model.azerite.AzeriteEssence;
import gg.sep.battlenet.wow.model.azerite.AzeriteEssenceIndex;
import gg.sep.battlenet.wow.model.azerite.AzeriteEssenceIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Azerite Essence API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class AzeriteEssenceAPI extends WoWAPI {

    private final AzeriteEssenceEndpoint azeriteEssenceEndpoint;

    /**
     * Creates an Azerite Essence API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Azerite Essence API.
     */
    public AzeriteEssenceAPI(final BattleNet battleNet) {
        super(battleNet);
        this.azeriteEssenceEndpoint = battleNet.getRetrofit().create(AzeriteEssenceEndpoint.class);
    }

    /**
     * Gets a list of all Azerite Essence Index items for the WoW Azerite Essence API.
     * @return {@link Ok} containing a list of Azerite Essence index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<AzeriteEssenceIndexItem>, String> getAzeriteEssences() {
        final Call<AzeriteEssenceIndex> call = azeriteEssenceEndpoint.getAzeriteEssences();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Azerite Essence entity for the specified Azerite Essence {@code id}.
     * @param id ID of the WoW Azerite Essence.
     * @return {@link Ok} containing the Azerite Essence if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<AzeriteEssence, String> getAzeriteEssence(final long id) {
        final Call<AzeriteEssence> call = azeriteEssenceEndpoint.getAzeriteEssence(id);
        return executeCall(call);
    }
}
