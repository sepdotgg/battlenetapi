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

package gg.sep.battlenet.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

/**
 * The supported API regions for the Battle.net API client.
 *
 * API Reference: https://develop.battle.net/documentation/guides/regionality-partitions-and-localization
 */
@Getter
public enum BattleNetRegion {

    NORTH_AMERICA("us", new BattleNetLocale[] {
        BattleNetLocale.EN_US,
        BattleNetLocale.ES_MX,
        BattleNetLocale.PT_BR
    }),
    EUROPE("eu", new BattleNetLocale[] {
        BattleNetLocale.EN_GB,
        BattleNetLocale.ES_ES,
        BattleNetLocale.FR_FR,
        BattleNetLocale.RU_RU,
        BattleNetLocale.DE_DE,
        BattleNetLocale.PT_PT,
        BattleNetLocale.IT_IT
    }),
    KOREA("kr", new BattleNetLocale[] {BattleNetLocale.KO_KR}),
    TAIWAN("tw", new BattleNetLocale[] {BattleNetLocale.ZH_TW});

    // TODO: CHINA? I mean, China is super important to Blizzard these days so maybe? #FreeHongKong

    private String regionUrlValue;
    private List<BattleNetLocale> supportedLocales;

    /**
     * Constructs the region with the specified URL value to be formatted into base API URLs, and the
     * set of {@link BattleNetLocale} supported in that region.
     *
     * Reference: https://develop.battle.net/documentation/guides/regionality-partitions-and-localization
     * @param regionUrlValue Value to format into base URLs for the API. Eg, north america uses "us"
     *                       for "https://us.api.blizzard.com".
     * @param supportedLocales Array of supported locales for this region.
     */
    BattleNetRegion(final String regionUrlValue, final BattleNetLocale[] supportedLocales) {
        this.regionUrlValue = regionUrlValue;
        this.supportedLocales = Stream.of(supportedLocales).collect(Collectors.toList());
    }

    /**
     * Checks if the specified locale is supported in this region.
     * @param locale Locale to check.
     * @return {@code true} if the {@code locale} is one of the supported locales for this region,
     *         otherwise {@code false}.
     */
    public boolean hasLocale(final BattleNetLocale locale) {
        return supportedLocales.contains(locale);
    }
}
