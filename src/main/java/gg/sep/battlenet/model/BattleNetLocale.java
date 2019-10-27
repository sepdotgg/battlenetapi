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

import java.util.stream.Stream;

import lombok.Getter;

/**
 * The supported API locales for the Battle.net API client.
 *
 * API Reference: https://develop.battle.net/documentation/guides/regionality-partitions-and-localization
 */
@Getter
public enum BattleNetLocale {
    // North America
    EN_US("en_US"),
    ES_MX("es_MX"),
    PT_BR("pt_BR"),

    // Europe
    EN_GB("en_GB"),
    ES_ES("es_ES"),
    FR_FR("fr_FR"),
    RU_RU("ru_RU"),
    DE_DE("de_DE"),
    PT_PT("pt_PT"),
    IT_IT("it_IT"),

    // Korea
    KO_KR("ko_KR"),

    // Taiwan
    ZH_TW("zh_TW");

    private String localeString;

    /**
     * Constructs the enum with the specified locale string (used in API query parameters).
     *
     * @param localeString Locale string to be formatted into API request query parameters.
     */
    BattleNetLocale(final String localeString) {
        this.localeString = localeString;
    }

    /**
     * Gets the Battle.net API region associated with the locale.
     * @return The Battle.net API region associated with the locale.
     */
    public BattleNetRegion getRegion() {
        return Stream.of(BattleNetRegion.values())
            .filter(r -> r.hasLocale(this))
            .findFirst()
            .orElseThrow(RuntimeException::new);
    }
}
