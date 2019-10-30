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

import gg.sep.battlenet.wow.model.pet.Pet;
import gg.sep.battlenet.wow.model.pet.PetIndex;

/**
 * Retrofit interface which defines the endpoints available in the Battle.net WoW Pet API.
 *
 * The class which implements these API calls is {@link gg.sep.battlenet.wow.api.PetAPI}.
 *
 * <p>API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public interface PetEndpoint {

    /**
     * Retrieves an index of WoW Pets.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/pet/index?addNamespace=static")
    Call<PetIndex> getPets();

    /**
     * Retrieves the WoW pet for the specified Pet ID.
     * @param id ID of the WoW pet.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/pet/{id}?addNamespace=static")
    Call<Pet> getPet(@Path("id") long id);
}
