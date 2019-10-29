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
import gg.sep.battlenet.wow.endpoint.MountEndpoint;
import gg.sep.battlenet.wow.model.mount.Mount;
import gg.sep.battlenet.wow.model.mount.MountIndex;
import gg.sep.battlenet.wow.model.mount.MountIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Mount API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class MountAPI extends WoWAPI {

    private final MountEndpoint mountEndpoint;

    /**
     * Creates an Mount API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Mount API.
     */
    public MountAPI(final BattleNet battleNet) {
        super(battleNet);
        this.mountEndpoint = battleNet.getRetrofit().create(MountEndpoint.class);
    }

    /**
     * Gets a list of all Mount Index items for the WoW Mount API.
     * @return {@link Ok} containing a list of mount index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<MountIndexItem>, String> getMounts() {
        final Call<MountIndex> call = mountEndpoint.getMounts();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Mount entity for the specified Mount {@code id}.
     * @param id ID of the WoW Mount.
     * @return {@link Ok} containing the Mount if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<Mount, String> getMount(final long id) {
        final Call<Mount> call = mountEndpoint.getMount(id);
        return executeCall(call);
    }
}
