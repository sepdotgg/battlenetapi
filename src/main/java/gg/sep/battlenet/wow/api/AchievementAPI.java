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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.wow.endpoint.AchievementEndpoint;
import gg.sep.battlenet.wow.model.WoWMedia;
import gg.sep.battlenet.wow.model.achievement.Achievement;
import gg.sep.battlenet.wow.model.achievement.AchievementCategory;
import gg.sep.battlenet.wow.model.achievement.AchievementCategoryIndex;
import gg.sep.battlenet.wow.model.achievement.AchievementCategoryIndexItem;
import gg.sep.battlenet.wow.model.achievement.AchievementIndex;
import gg.sep.battlenet.wow.model.achievement.AchievementIndexItem;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Provides an API interface for accessing the Battle.net WoW Achievement API endpoints.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/world-of-warcraft-game-data-api
 */
public class AchievementAPI extends WoWAPI {

    private final AchievementEndpoint achievementEndpoint;

    /**
     * Creates an Achievement API instance using the provided Battle.net Client.
     * @param battleNet Battle.net Client to use to connect to the Achievement API.
     */
    public AchievementAPI(final BattleNet battleNet) {
        super(battleNet);
        this.achievementEndpoint = battleNet.getRetrofit().create(AchievementEndpoint.class);
    }

    /**
     * Gets a list of all Achievement Index items for the WoW Achievement API.
     * @return {@link Ok} containing a list of achievement index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<AchievementIndexItem>, String> getAchievements() {
        final Call<AchievementIndex> call = achievementEndpoint.getAchievements();
        return executeIndexCall(call);
    }

    /**
     * Gets a WoW Achievement entity for the specified Achievement {@code id}.
     * @param id ID of the WoW Achievement.
     * @return {@link Ok} containing the Achievement if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<Achievement, String> getAchievement(final long id) {
        final Call<Achievement> call = achievementEndpoint.getAchievement(id);
        return executeCall(call);
    }

    /**
     * Gets a list of all Achievement Category Index items for the WoW Achievement API.
     * @return {@link Ok} containing a list of achievement category index items if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<List<AchievementCategoryIndexItem>, String> getCategories() {
        final Call<AchievementCategoryIndex> call = achievementEndpoint.getAchievementCategories();
        final Result<AchievementCategoryIndex, String> indexResult = executeCall(call);
        if (indexResult.isErr()) {
            return Err.of(indexResult.unwrapErr());
        }

        final List<AchievementCategoryIndexItem> finalList = new ArrayList<>();
        final AchievementCategoryIndex categoryIndex = indexResult.unwrap();
        finalList.addAll(categoryIndex.getItems());
        finalList.addAll(categoryIndex.getRootCategories());
        // this shouldn't be necessary since all guild categories should be covered between root and regular
        // but just in case...
        finalList.addAll(categoryIndex.getGuildCategories());

        return Ok.of(finalList);
    }

    /**
     * Gets a WoW Achievement entity for the specified Achievement Category {@code id}.
     * @param id ID of the WoW Achievement Category.
     * @return {@link Ok} containing the Achievement Category if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<AchievementCategory, String> getCategory(final long id) {
        final Call<AchievementCategory> call = achievementEndpoint.getAchievementCategory(id);
        return executeCall(call);
    }

    /**
     * Gets a WoW Achievement Media entity for the specified Achievement {@code id}.
     * @param id ID of the WoW Achievement.
     * @return {@link Ok} containing the Achievement Media if the API call was successful,
     *         otherwise an {@link Err} containing the error message.
     */
    public Result<WoWMedia, String> getAchievementMedia(final long id) {
        final Call<WoWMedia> call = achievementEndpoint.getAchievementMedia(id);
        return executeCall(call);
    }
}
