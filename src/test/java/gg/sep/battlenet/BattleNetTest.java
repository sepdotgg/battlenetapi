/*
 * Copyright (c) 2019 sep.gg <seputaes@sep.gg>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package gg.sep.battlenet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import gg.sep.battlenet.model.BattleNetLocale;
import gg.sep.battlenet.model.BattleNetRegion;

/**
 * Tests for {@link BattleNet}.
 */
class BattleNetTest {
    @Test void testBattleNetConstructor() {
        Assertions.assertDoesNotThrow(() -> BattleNet.builder().clientId("").clientSecret("").build());
    }

    @Test void constructor_NullClientIdOrSecret_ThrowException() {
        assertThrows(NullPointerException.class, () -> BattleNet.builder().clientId("").build());
        assertThrows(NullPointerException.class, () -> BattleNet.builder().clientSecret("").build());
        assertThrows(NullPointerException.class, () -> BattleNet.builder().build());
    }

    @MethodSource("regionAndLocale")
    @ParameterizedTest void constructor_usesCorrectRegionAndLocale(final BattleNetRegion region,
                                                                   final BattleNetLocale locale,
                                                                   final BattleNetRegion expectedRegion,
                                                                   final BattleNetLocale expectedLocale) {
        final BattleNet battleNet = BattleNet.builder()
            .clientId("").clientSecret("")
            .region(region)
            .locale(locale)
            .build();
        assertEquals(expectedRegion, battleNet.getRegion());
        assertEquals(expectedLocale, battleNet.getLocale());
    }

    @Test void constructor_InvalidLocaleRegionCombination_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> BattleNet.builder()
            .clientId("")
            .clientSecret("")
            .region(BattleNetRegion.NORTH_AMERICA)
            .locale(BattleNetLocale.KO_KR)
            .build());
    }

    private static Stream<Arguments> regionAndLocale() {
        // entered region, entered locale, expected region, expected locale
        return Stream.of(
            Arguments.of(null, null, BattleNetRegion.NORTH_AMERICA, BattleNetLocale.EN_US),
            Arguments.of(BattleNetRegion.EUROPE, null, BattleNetRegion.EUROPE, BattleNetLocale.EN_GB),
            Arguments.of(BattleNetRegion.KOREA, null, BattleNetRegion.KOREA, BattleNetLocale.KO_KR),
            Arguments.of(BattleNetRegion.TAIWAN, null, BattleNetRegion.TAIWAN, BattleNetLocale.ZH_TW),
            Arguments.of(null, BattleNetLocale.EN_US, BattleNetRegion.NORTH_AMERICA, BattleNetLocale.EN_US),
            Arguments.of(BattleNetRegion.EUROPE, BattleNetLocale.DE_DE, BattleNetRegion.EUROPE, BattleNetLocale.DE_DE),
            Arguments.of(BattleNetRegion.KOREA, BattleNetLocale.KO_KR, BattleNetRegion.KOREA, BattleNetLocale.KO_KR),
            Arguments.of(BattleNetRegion.TAIWAN, BattleNetLocale.ZH_TW, BattleNetRegion.TAIWAN, BattleNetLocale.ZH_TW)
        );
    }

}
