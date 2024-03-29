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

package gg.sep.battlenet.wow.model.pet;

import java.net.URL;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.battlenet.model.AbstractBattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;
import gg.sep.battlenet.wow.model.creature.CreatureDisplay;

/**
 * Represents the full WoW Pet API entity.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
@Getter
public class Pet extends AbstractBattleNetEntity implements JsonSerializable {
    private Long id;
    private String name;
    @SerializedName("creature_display")
    private CreatureDisplay creatureDisplay;
    private String description;

    private PetSource source;

    @SerializedName("battle_pet_type")
    private BattlePetTypeItem battlePetType;

    private List<PetAbilitiesItem> abilities;

    @SerializedName("is_capturable")
    private Boolean isCapturable;
    @SerializedName("is_tradable")
    private Boolean isTradable;
    @SerializedName("is_battlepet")
    private Boolean isBattlepet;
    @SerializedName("is_alliance_only")
    private Boolean isAllianceOnly;
    @SerializedName("is_horde_only")
    private Boolean isHordeOnly;
    private URL icon;
}
