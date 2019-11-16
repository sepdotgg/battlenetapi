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

package gg.sep.battlenet.wow.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import gg.sep.battlenet.wow.model.reputation.ReputationFaction;
import gg.sep.battlenet.wow.model.reputation.ReputationFactionIndex;
import gg.sep.battlenet.wow.model.reputation.ReputationTiers;
import gg.sep.battlenet.wow.model.reputation.ReputationTiersIndex;

/**
 * Retrofit interface which defines the endpoints available in the Battle.net WoW Reputation API.
 *
 * The class which implements these API calls is {@link gg.sep.battlenet.wow.api.ReputationAPI}.
 *
 * <p>API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public interface ReputationEndpoint {

    /**
     * Retrieves an index of WoW Reputation Factions.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/reputation-faction/index?addNamespace=static")
    Call<ReputationFactionIndex> getFactions();

    /**
     * Retrieves the WoW reputation faction for the specified Faction ID.
     * @param id ID of the WoW reputation faction.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/reputation-faction/{id}?addNamespace=static")
    Call<ReputationFaction> getFaction(@Path("id") long id);

    /**
     * Retrieves an index of WoW Reputation Tiers.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/reputation-tiers/index?addNamespace=static")
    Call<ReputationTiersIndex> getReputationTiersIndex();

    /**
     * Retrieves the WoW reputation tiers for the specified Reputation Tiers ID.
     * @param id ID of the WoW reputation tiers.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/reputation-tiers/{id}?addNamespace=static")
    Call<ReputationTiers> getReputationTiers(@Path("id") long id);
}
