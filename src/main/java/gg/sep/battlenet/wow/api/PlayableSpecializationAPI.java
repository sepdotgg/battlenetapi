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
import gg.sep.battlenet.wow.endpoint.PlayableSpecializationEndpoint;
import gg.sep.battlenet.wow.model.specialization.PlayableSpecialization;
import gg.sep.battlenet.wow.model.specialization.PlayableSpecializationIndex;
import gg.sep.battlenet.wow.model.specialization.PlayableSpecializationIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Playable Specialization API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class PlayableSpecializationAPI extends WoWAPI {

    private final PlayableSpecializationEndpoint playableSpecializationEndpoint;

    /**
     * Creates an Playable Specialization API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Playable Specialization API.
     */
    public PlayableSpecializationAPI(final BattleNet battleNet) {
        super(battleNet);
        this.playableSpecializationEndpoint = battleNet.getRetrofit().create(PlayableSpecializationEndpoint.class);
    }

    /**
     * Gets a list of all Character Specializations Index items for the WoW Playable Specialization API.
     * @return {@link Ok} containing a list of character specialization index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<PlayableSpecializationIndexItem>, String> getCharacterSpecializations() {
        final Call<PlayableSpecializationIndex> call = playableSpecializationEndpoint.getPlayableSpecializations();
        final Result<PlayableSpecializationIndex, String> result = executeCall(call);
        if (result.isErr()) {
            return Err.of(result.unwrapErr());
        }
        return Ok.of(result.unwrap().getItems());
    }

    /**
     * Gets a list of all Pet Specializations Index items for the WoW Playable Specialization API.
     * @return {@link Ok} containing a list of pet specialization index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<PlayableSpecializationIndexItem>, String> getPetSpecializations() {
        final Call<PlayableSpecializationIndex> call = playableSpecializationEndpoint.getPlayableSpecializations();
        final Result<PlayableSpecializationIndex, String> result = executeCall(call);
        if (result.isErr()) {
            return Err.of(result.unwrapErr());
        }
        return Ok.of(result.unwrap().getPetSpecializations());
    }

    /**
     * Gets a WoW Playable Specialization entity for the specified Specialization {@code id}.
     * @param id ID of the WoW Playable Specialization.
     * @return {@link Ok} containing the Playable Specialization if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<PlayableSpecialization, String> getSpecialization(final long id) {
        final Call<PlayableSpecialization> call = playableSpecializationEndpoint.getPlayableSpecialization(id);
        return executeCall(call);
    }
}
