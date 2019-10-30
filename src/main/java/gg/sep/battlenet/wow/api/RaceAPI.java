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
import gg.sep.battlenet.wow.endpoint.RaceEndpoint;
import gg.sep.battlenet.wow.model.race.PlayableRace;
import gg.sep.battlenet.wow.model.race.PlayableRaceIndex;
import gg.sep.battlenet.wow.model.race.PlayableRaceIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW PlayableRace API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class RaceAPI extends WoWAPI {

    private final RaceEndpoint raceEndpoint;

    /**
     * Creates an PlayableRace API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the PlayableRace API.
     */
    public RaceAPI(final BattleNet battleNet) {
        super(battleNet);
        this.raceEndpoint = battleNet.getRetrofit().create(RaceEndpoint.class);
    }

    /**
     * Gets a list of all Playable Race Index items for the WoW Playable Race API.
     * @return {@link Ok} containing a list of playable race index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<PlayableRaceIndexItem>, String> getPlayableRaces() {
        final Call<PlayableRaceIndex> call = raceEndpoint.getPlayableRaces();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Playable Race entity for the specified Playable Race {@code id}.
     * @param id ID of the WoW Playable Race.
     * @return {@link Ok} containing the Playable Race if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<PlayableRace, String> getPlayableRace(final long id) {
        final Call<PlayableRace> call = raceEndpoint.getPlayableRace(id);
        return executeCall(call);
    }
}
