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
import gg.sep.battlenet.wow.endpoint.CreatureEndpoint;
import gg.sep.battlenet.wow.model.WoWMedia;
import gg.sep.battlenet.wow.model.creature.Creature;
import gg.sep.battlenet.wow.model.creature.CreatureFamily;
import gg.sep.battlenet.wow.model.creature.CreatureFamilyIndex;
import gg.sep.battlenet.wow.model.creature.CreatureFamilyIndexItem;
import gg.sep.battlenet.wow.model.creature.CreatureType;
import gg.sep.battlenet.wow.model.creature.CreatureTypeIndex;
import gg.sep.battlenet.wow.model.creature.CreatureTypeIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Creature API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class CreatureAPI extends WoWAPI {

    private final CreatureEndpoint creatureEndpoint;

    /**
     * Creates an Creature API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Creature API.
     */
    public CreatureAPI(final BattleNet battleNet) {
        super(battleNet);
        this.creatureEndpoint = battleNet.getRetrofit().create(CreatureEndpoint.class);
    }

    /**
     * Gets a list of all Creature Family Index items for the WoW Creature API.
     * @return {@link Ok} containing a list of creature family index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<CreatureFamilyIndexItem>, String> getCreatureFamilies() {
        final Call<CreatureFamilyIndex> call = creatureEndpoint.getCreatureFamilies();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Creature Family entity for the specified Creature Family {@code id}.
     * @param id ID of the WoW Creature Family.
     * @return {@link Ok} containing the Creature Family if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<CreatureFamily, String> getCreatureFamily(final long id) {
        final Call<CreatureFamily> call = creatureEndpoint.getCreatureFamily(id);
        return executeCall(call);
    }

    /**
     * Gets a WoW Creature Family Media entity for the specified Creature Family {@code id}.
     * @param id ID of the WoW Creature Family.
     * @return {@link Ok} containing the Creature Family Media if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<WoWMedia, String> getCreatureFamilyMedia(final long id) {
        final Call<WoWMedia> call = creatureEndpoint.getCreatureFamilyMedia(id);
        return executeCall(call);
    }

    /**
     * Gets a list of all Creature Type Index items for the WoW Creature API.
     * @return {@link Ok} containing a list of creature type index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<CreatureTypeIndexItem>, String> getCreatureTypes() {
        final Call<CreatureTypeIndex> call = creatureEndpoint.getCreatureTypes();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Creature Type entity for the specified Creature Type {@code id}.
     * @param id ID of the WoW Creature Type.
     * @return {@link Ok} containing the Creature Type if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<CreatureType, String> getCreatureType(final long id) {
        final Call<CreatureType> call = creatureEndpoint.getCreatureType(id);
        return executeCall(call);
    }

    /**
     * Gets a WoW Creature entity for the specified Creature {@code id}.
     * @param id ID of the WoW Creature.
     * @return {@link Ok} containing the Creature if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<Creature, String> getCreature(final long id) {
        final Call<Creature> call = creatureEndpoint.getCreature(id);
        return executeCall(call);
    }

    /**
     * Gets a WoW Creature Display Media entity for the specified Creature Display {@code id}.
     * @param id ID of the WoW Creature Display.
     * @return {@link Ok} containing the Creature Display Media if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<WoWMedia, String> getCreatureDisplayMedia(final long id) {
        final Call<WoWMedia> call = creatureEndpoint.getCreatureDisplayMedia(id);
        return executeCall(call);
    }
}
