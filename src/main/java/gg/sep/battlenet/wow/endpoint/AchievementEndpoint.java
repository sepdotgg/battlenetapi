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
import gg.sep.battlenet.wow.model.achievement.Achievement;
import gg.sep.battlenet.wow.model.achievement.AchievementCategory;
import gg.sep.battlenet.wow.model.achievement.AchievementCategoryIndex;
import gg.sep.battlenet.wow.model.achievement.AchievementIndex;

/**
 * Retrofit interface which defines the endpoints available in the Battle.net WoW Achievement API.
 *
 * The class which implements these API calls is {@link gg.sep.battlenet.wow.api.AchievementAPI}.
 *
 * <p>API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public interface AchievementEndpoint {

    /**
     * Retrieves an index of all WoW achievements.
     * @return Retrofit call which will retrieve the entity
     */
    @GET("data/wow/achievement/index?addNamespace=static")
    Call<AchievementIndex> getAchievements();

    /**
     * Retrieves the WoW achievement for the specified Achievement ID.
     * @param id ID of the WoW achievement.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/achievement/{id}?addNamespace=static")
    Call<Achievement> getAchievement(@Path("id") long id);

    /**
     * Retrieves an index of all WoW achievement categories.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/achievement-category/index?addNamespace=static")
    Call<AchievementCategoryIndex> getAchievementCategories();

    /**
     * Retrieves the WoW achievement category for the specified Achievement Category ID.
     * @param id ID of the WoW achievement category.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/achievement-category/{id}?addNamespace=static")
    Call<AchievementCategory> getAchievementCategory(@Path("id") long id);

    /**
     * Retrieves the WoW achievement category media for the specified Achievement Category ID.
     * @param id ID of the WoW achievement category.
     * @return Retrofit call which will retrieve the entity.
     */
    @GET("data/wow/media/achievement/{id}?addNamespace=static")
    Call<WoWMedia> getAchievementMedia(@Path("id") long id);
}
