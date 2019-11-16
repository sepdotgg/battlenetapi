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
import gg.sep.battlenet.wow.endpoint.ReputationEndpoint;
import gg.sep.battlenet.wow.model.reputation.ReputationFaction;
import gg.sep.battlenet.wow.model.reputation.ReputationFactionIndex;
import gg.sep.battlenet.wow.model.reputation.ReputationFactionIndexItem;
import gg.sep.battlenet.wow.model.reputation.ReputationTiers;
import gg.sep.battlenet.wow.model.reputation.ReputationTiersIndex;
import gg.sep.battlenet.wow.model.reputation.ReputationTiersIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Reputation API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class ReputationAPI extends WoWAPI {

    private final ReputationEndpoint reputationEndpoint;

    /**
     * Creates an Reputation API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Reputation API.
     */
    public ReputationAPI(final BattleNet battleNet) {
        super(battleNet);
        this.reputationEndpoint = battleNet.getRetrofit().create(ReputationEndpoint.class);
    }

    /**
     * Gets a list of all Reputation Faction Index items for the WoW Reputation API.
     * @return {@link Ok} containing a list of reputation faction index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<ReputationFactionIndexItem>, String> getFactions() {
        final Call<ReputationFactionIndex> call = reputationEndpoint.getFactions();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Reputation Faction entity for the specified Reputation Faction {@code id}.
     * @param id ID of the WoW Reputation Faction.
     * @return {@link Ok} containing the Reputation faction if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<ReputationFaction, String> getFaction(final long id) {
        final Call<ReputationFaction> call = reputationEndpoint.getFaction(id);
        return executeCall(call);
    }

    /**
     * Gets a list of all Reputation Tiers Index items for the WoW Reputation API.
     * @return {@link Ok} containing a list of reputation tiers index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<ReputationTiersIndexItem>, String> getReputationTiersIndex() {
        final Call<ReputationTiersIndex> call = reputationEndpoint.getReputationTiersIndex();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Reputation Tiers entity for the specified Reputation Tiers {@code id}.
     * @param id ID of the WoW Reputation Tiers.
     * @return {@link Ok} containing the Reputation tiers if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<ReputationTiers, String> getReputationTiers(final long id) {
        final Call<ReputationTiers> call = reputationEndpoint.getReputationTiers(id);
        return executeCall(call);
    }
}
