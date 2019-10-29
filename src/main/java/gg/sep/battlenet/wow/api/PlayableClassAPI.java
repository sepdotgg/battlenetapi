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
import gg.sep.battlenet.wow.endpoint.PlayableClassEndpoint;
import gg.sep.battlenet.wow.model.WoWMedia;
import gg.sep.battlenet.wow.model.playableclass.PlayableClass;
import gg.sep.battlenet.wow.model.playableclass.PlayableClassIndex;
import gg.sep.battlenet.wow.model.playableclass.PlayableClassIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Playable Class API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class PlayableClassAPI extends WoWAPI {

    private final PlayableClassEndpoint playableClassEndpoint;

    /**
     * Creates an Playable Class API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Playable Class API.
     */
    public PlayableClassAPI(final BattleNet battleNet) {
        super(battleNet);
        this.playableClassEndpoint = battleNet.getRetrofit().create(PlayableClassEndpoint.class);
    }

    /**
     * Gets a list of all Pet Class Index items for the WoW Playable Class API.
     * @return {@link Ok} containing a list of pet class index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<PlayableClassIndexItem>, String> getPlayableClasses() {
        final Call<PlayableClassIndex> call = playableClassEndpoint.getPlayableClasses();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Playable Class entity for the specified Class {@code id}.
     * @param id ID of the WoW Playable Class.
     * @return {@link Ok} containing the Playable Class if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<PlayableClass, String> getPlayableClass(final long id) {
        final Call<PlayableClass> call = playableClassEndpoint.getPlayableClass(id);
        return executeCall(call);
    }

    /**
     * Gets a WoW Playable Class Media entity for the specified Playable Class {@code id}.
     * @param id ID of the WoW Playable Class.
     * @return {@link Ok} containing the Playable Class Media if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<WoWMedia, String> getPlayableClassMedia(final long id) {
        final Call<WoWMedia> call = playableClassEndpoint.getPlayableClassMedia(id);
        return executeCall(call);
    }
}
