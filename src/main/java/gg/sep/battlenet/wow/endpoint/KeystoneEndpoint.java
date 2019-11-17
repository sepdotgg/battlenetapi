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

import gg.sep.battlenet.wow.model.keystone.KeystoneAffix;
import gg.sep.battlenet.wow.model.keystone.KeystoneAffixIndex;
import gg.sep.battlenet.wow.model.keystone.MythicKeystoneLeaderboard;
import gg.sep.battlenet.wow.model.keystone.MythicKeystoneLeaderboardIndex;


/**
 * Retrofit interface which defines the endpoints available in the Battle.net WoW Mythic Keystone API.
 *
 * The class which implements these API calls is {@link gg.sep.battlenet.wow.api.KeystoneAPI}.
 *
 * <p>API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public interface KeystoneEndpoint {

    /**
     * Retrieves an index of WoW Mythic Leaderboards for the specified Wow Connected Realm ID.
     * @param connectedRealmId ID of the WoW connected Realm.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/connected-realm/{connectedRealmId}/mythic-leaderboard/index?addNamespace=dynamic")
    Call<MythicKeystoneLeaderboardIndex> getLeaderboards(@Path("connectedRealmId") int connectedRealmId);

    /**
     * Retrieves the WoW Mythic Keystone leaderboard for the specified realm, dungeon, and period.
     * @param connectedRealmId ID of the WoW connected realm.
     * @param dungeonId ID of the WoW Keystone dungeon.
     * @param period Period of the WoW Keystone leaderboard.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/connected-realm/{connectedRealmId}/mythic-leaderboard/{dungeonId}/period/{period}" +
        "?addNamespace=dynamic")
    Call<MythicKeystoneLeaderboard> getLeaderboard(@Path("connectedRealmId") int connectedRealmId,
                                                   @Path("dungeonId") int dungeonId,
                                                   @Path("period") int period);

    /**
     * Retrieves an index of WoW Mythic Keystone Affixes.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/keystone-affix/index?addNamespace=static")
    Call<KeystoneAffixIndex> getAffixes();

    /**
     * Retrieves the WoW mount for the specified Mythic Keystone Affix ID.
     * @param id ID of the WoW keystone affix.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/keystone-affix/{id}?addNamespace=static")
    Call<KeystoneAffix> getAffix(@Path("id") long id);
}
