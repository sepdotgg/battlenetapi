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
import gg.sep.battlenet.wow.endpoint.RegionEndpoint;
import gg.sep.battlenet.wow.model.region.Region;
import gg.sep.battlenet.wow.model.region.RegionIndex;
import gg.sep.battlenet.wow.model.region.RegionKey;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Region API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class RegionAPI extends WoWAPI {

    private final RegionEndpoint regionEndpoint;

    /**
     * Creates an Region API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Region API.
     */
    public RegionAPI(final BattleNet battleNet) {
        super(battleNet);
        this.regionEndpoint = battleNet.getRetrofit().create(RegionEndpoint.class);
    }

    /**
     * Gets a list of all Region Index items for the WoW Region API.
     * @return {@link Ok} containing a list of region index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<RegionKey>, String> getRegions() {
        final Call<RegionIndex> call = regionEndpoint.getRegions();
        return executeKeyIndexCall(call);
    }

    /**
     * Gets a WoW Region entity for the specified Region {@code id}.
     * @param id ID of the WoW Region.
     * @return {@link Ok} containing the Region if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<Region, String> getRegion(final int id) {
        final Call<Region> call = regionEndpoint.getRegion(id);
        return executeCall(call);
    }
}
