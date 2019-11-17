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

package gg.sep.battlenet.wow.model.keystone;

import java.time.Instant;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.battlenet.model.AbstractBattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;
import gg.sep.battlenet.wow.model.realm.ConnectedRealmKey;

/**
 * Represents the full WoW Mythic Keystone Leaderboard API entity.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
@Getter
public class MythicKeystoneLeaderboard extends AbstractBattleNetEntity implements JsonSerializable {
    private KeystoneMap map;
    private String name;
    @SerializedName("map_challenge_mode_id")
    private Long mapChallengeModeId;
    private Long period;
    @SerializedName("period_start_timestamp")
    private Instant periodStart;
    @SerializedName("period_end_timestamp")
    private Instant periodEnd;

    @SerializedName("connected_realm")
    private ConnectedRealmKey connectedRealm;

    @SerializedName("leading_groups")
    private List<KeystoneGroup> leadingGroups;

    @SerializedName("keystone_affixes")
    private List<KeystoneLeaderboardAffixItem> keystoneAffixes;
}
