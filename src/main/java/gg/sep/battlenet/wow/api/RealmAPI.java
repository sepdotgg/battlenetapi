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
import gg.sep.battlenet.wow.endpoint.RealmEndpoint;
import gg.sep.battlenet.wow.model.realm.ConnectedRealm;
import gg.sep.battlenet.wow.model.realm.ConnectedRealmIndex;
import gg.sep.battlenet.wow.model.realm.ConnectedRealmKey;
import gg.sep.battlenet.wow.model.realm.RealmIndex;
import gg.sep.battlenet.wow.model.realm.RealmIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Realm API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class RealmAPI extends WoWAPI {

    private final RealmEndpoint realmEndpoint;

    /**
     * Creates an Realm API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Realm API.
     */
    public RealmAPI(final BattleNet battleNet) {
        super(battleNet);
        this.realmEndpoint = battleNet.getRetrofit().create(RealmEndpoint.class);
    }

    /**
     * Gets a list of all Realm Index items for the WoW Realm API.
     * @return {@link Ok} containing a list of realm index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<RealmIndexItem>, String> getRealms() {
        final Call<RealmIndex> call = realmEndpoint.getRealms();
        return executeIndexCall(call);
    }

    /**
     * Gets a list of all Realm Index items for the WoW Connected Realm API.
     * @return {@link Ok} containing a list of connected realm index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<ConnectedRealmKey>, String> getConnectedRealms() {
        final Call<ConnectedRealmIndex> call = realmEndpoint.getConnectedRealms();
        return executeKeyIndexCall(call);
    }

    /**
     * Gets a WoW Connected Realm entity for the specified Realm {@code id}.
     * @param id Slug of the WoW Connected Realm.
     * @return {@link Ok} containing the Connected Realm if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<ConnectedRealm, String> getConnectedRealm(final int id) {
        final Call<ConnectedRealm> call = realmEndpoint.getConnectedRealm(id);
        return executeCall(call);
    }
}
