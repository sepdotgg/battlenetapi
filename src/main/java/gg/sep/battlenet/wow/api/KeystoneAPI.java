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
import gg.sep.battlenet.wow.endpoint.KeystoneEndpoint;
import gg.sep.battlenet.wow.model.keystone.KeystoneAffix;
import gg.sep.battlenet.wow.model.keystone.KeystoneAffixIndex;
import gg.sep.battlenet.wow.model.keystone.KeystoneAffixIndexItem;
import gg.sep.battlenet.wow.model.keystone.MythicKeystoneLeaderboard;
import gg.sep.battlenet.wow.model.keystone.MythicKeystoneLeaderboardIndex;
import gg.sep.battlenet.wow.model.keystone.MythicKeystoneLeaderboardIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Mythic Keystone API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class KeystoneAPI extends WoWAPI {

    private final KeystoneEndpoint keystoneEndpoint;

    /**
     * Creates an Mythic Keystone API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Region API.
     */
    public KeystoneAPI(final BattleNet battleNet) {
        super(battleNet);
        this.keystoneEndpoint = battleNet.getRetrofit().create(KeystoneEndpoint.class);
    }

    /**
     * Gets a list of all Mythic Keystone Leaderboard Index items for specified Connected Realm ID.
     *
     * @param connectedRealmId ID of the WoW connected realm.
     * @return {@link Ok} containing a list of region index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<MythicKeystoneLeaderboardIndexItem>, String> getLeaderboards(final int connectedRealmId) {
        final Call<MythicKeystoneLeaderboardIndex> call = keystoneEndpoint.getLeaderboards(connectedRealmId);
        return executeIndexCall(call);
    }

    /**
     * Gets the Mythic Keystone leaderboard for the specified Conencted Realm ID, dungeon ID, and period.
     * @param connectedRealmId ID of the WoW connected realm.
     * @param dungeonId ID of the Keystone Dungeon.
     * @param period Period of the keystone leaderboard.
     * @return {@link Ok} containing a list of the keystone leaderboard if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<MythicKeystoneLeaderboard, String> getLeaderboard(final int connectedRealmId,
                                                                    final int dungeonId, final int period) {
        final Call<MythicKeystoneLeaderboard> call = keystoneEndpoint.getLeaderboard(
            connectedRealmId, dungeonId, period);
        return executeCall(call);
    }

    /**
     * Gets a list of all Mythic Keystone Affix Index items for the WoW Keystone API.
     * @return {@link Ok} containing a list of mythic keystone affix index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<KeystoneAffixIndexItem>, String> getAfixes() {
        final Call<KeystoneAffixIndex> call = keystoneEndpoint.getAffixes();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Mythic Keystone affix entity for the specified Affix {@code id}.
     * @param id ID of the WoW Keystone Affix.
     * @return {@link Ok} containing the Keystone Affix if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<KeystoneAffix, String> getAffix(final int id) {
        final Call<KeystoneAffix> call = keystoneEndpoint.getAffix(id);
        return executeCall(call);
    }
}
