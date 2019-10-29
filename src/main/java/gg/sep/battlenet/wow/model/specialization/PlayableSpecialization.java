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

package gg.sep.battlenet.wow.model.specialization;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.battlenet.model.AbstractBattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;
import gg.sep.battlenet.wow.model.HasMedia;
import gg.sep.battlenet.wow.model.WoWMediaLink;
import gg.sep.battlenet.wow.model.playableclass.PlayableClassIndexItem;
import gg.sep.battlenet.wow.model.talent.TalentIndexItem;
import gg.sep.battlenet.wow.model.talent.TalentTier;

/**
 * Represents the full WoW Playable Specialization API entity.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
@Getter
public class PlayableSpecialization extends AbstractBattleNetEntity implements HasMedia, JsonSerializable {
    private Long id;
    private String name;
    @SerializedName("playable_class")
    private PlayableClassIndexItem playableClass;
    @SerializedName("gender_description")
    private GenderDescription genderDescription;
    @SerializedName("talent_tiers")
    private List<TalentTier> talentTiers;
    @SerializedName("pvp_talents")
    private List<TalentIndexItem> pvpTalents;
    private SpecializationRole role;

    @SerializedName("media")
    private WoWMediaLink mediaLink;

}
