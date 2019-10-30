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

import gg.sep.battlenet.wow.model.WoWMedia;
import gg.sep.battlenet.wow.model.creature.Creature;
import gg.sep.battlenet.wow.model.creature.CreatureFamily;
import gg.sep.battlenet.wow.model.creature.CreatureFamilyIndex;
import gg.sep.battlenet.wow.model.creature.CreatureType;
import gg.sep.battlenet.wow.model.creature.CreatureTypeIndex;

/**
 * Retrofit interface which defines the endpoints available in the Battle.net WoW creature family API.
 *
 * The class which implements these API calls is {@link gg.sep.battlenet.wow.api.CreatureAPI}.
 *
 * <p>API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public interface CreatureEndpoint {

    /**
     * Retrieves an index of WoW creature families.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/creature-family/index?addNamespace=static")
    Call<CreatureFamilyIndex> getCreatureFamilies();

    /**
     * Retrieves the WoW creature family for the specified creature family ID.
     * @param id ID of the WoW creature family.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/creature-family/{id}?addNamespace=static")
    Call<CreatureFamily> getCreatureFamily(@Path("id") long id);

    /**
     * Retrieves the WoW creature family media for the specified creature family ID.
     * @param id ID of the WoW creature family.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/media/creature-family/{id}?addNamespace=static")
    Call<WoWMedia> getCreatureFamilyMedia(@Path("id") long id);

    /**
     * Retrieves an index of WoW creature types.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/creature-type/index?addNamespace=static")
    Call<CreatureTypeIndex> getCreatureTypes();

    /**
     * Retrieves the WoW creature type for the specified creature type ID.
     * @param id ID of the WoW creature type.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/creature-type/{id}?addNamespace=static")
    Call<CreatureType> getCreatureType(@Path("id") long id);

    /**
     * Retrieves the WoW creature type for the specified creature type ID.
     * @param id ID of the WoW creature type.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/creature/{id}?addNamespace=static")
    Call<Creature> getCreature(@Path("id") long id);

    /**
     * Retrieves the WoW creature display media for the specified creature display ID.
     * @param id ID of the WoW creature display.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/media/creature-display/{id}?addNamespace=static")
    Call<WoWMedia> getCreatureDisplayMedia(@Path("id") long id);
}
