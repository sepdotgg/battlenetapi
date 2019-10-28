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
import gg.sep.battlenet.wow.endpoint.PowerTypeEndpoint;
import gg.sep.battlenet.wow.model.powertype.PowerType;
import gg.sep.battlenet.wow.model.powertype.PowerTypeIndex;
import gg.sep.battlenet.wow.model.powertype.PowerTypeIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Power Type API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class PowerTypeAPI extends WoWAPI {

    private final PowerTypeEndpoint powerTypeEndpoint;

    /**
     * Creates an Power Type API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Power Type API.
     */
    public PowerTypeAPI(final BattleNet battleNet) {
        super(battleNet);
        this.powerTypeEndpoint = battleNet.getRetrofit().create(PowerTypeEndpoint.class);
    }

    /**
     * Gets a list of all Power Type Index items for the WoW Power Type API.
     * @return {@link Ok} containing a list of power type index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<PowerTypeIndexItem>, String> getPowerTypes() {
        final Call<PowerTypeIndex> call = powerTypeEndpoint.getPowerTypes();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Power TYpe entity for the specified Power Type {@code id}.
     * @param id ID of the WoW Power Type.
     * @return {@link Ok} containing the Power Type if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<PowerType, String> getPowerType(final long id) {
        final Call<PowerType> call = powerTypeEndpoint.getPowerType(id);
        return executeCall(call);
    }
}
