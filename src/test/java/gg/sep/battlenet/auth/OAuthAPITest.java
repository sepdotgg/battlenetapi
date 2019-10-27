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

package gg.sep.battlenet.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import okhttp3.HttpUrl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gg.sep.battlenet.APITest;
import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.auth.api.OAuthAPI;
import gg.sep.battlenet.auth.model.OAuthToken;
import gg.sep.battlenet.model.BattleNetEntity;

/**
 * Unit test for {@link gg.sep.battlenet.auth.api.OAuthAPI}.
 */
public class OAuthAPITest extends APITest {

    private OAuthAPI basicAPI(final BattleNetEntity entity, final HttpUrl baseUrl) {
        return basicAPI(baseUrl, setupBattleNet(entity));
    }

    private OAuthAPI basicAPI(final HttpUrl baseUrl, final BattleNet battleNet) {
        final OAuthAPI.OAuthAPIBuilder builder = OAuthAPI.builder()
            .clientId("")
            .clientSecret("")
            .battleNet(battleNet);
        if (baseUrl != null) {
            builder.baseUrl(baseUrl);
        }
        return builder.build();
    }

    @Test void builder_WhenBaseUrlNotSet_UsesDefaultBaseUrl() {
        final OAuthToken simpleToken = OAuthToken.builder()
            .build();
        final OAuthAPI oAuthAPI = basicAPI(simpleToken, null);
        assertEquals(HttpUrl.get("https://us.battle.net"), oAuthAPI.getBaseUrl());
    }

    @Test void builder_BaseUrlSet_UsesSetBaseUrl() {
        final OAuthToken simpleToken = OAuthToken.builder()
            .build();
        final HttpUrl expected = HttpUrl.get("https://sep.gg");
        final OAuthAPI oAuthAPI = basicAPI(simpleToken, expected);
        assertEquals(expected, oAuthAPI.getBaseUrl());
    }

    @Test void getToken_SuccessPath_ReturnsValidToken() {
        final OAuthToken expectedToken = OAuthToken.builder()
            .accessToken("foo")
            .expiresIn(123L)
            .tokenType("access_token")
            .build();

        final BattleNet battleNet = setupBattleNet(expectedToken);
        final OAuthAPI oAuthAPI = basicAPI(expectedToken, battleNet.getRetrofit().baseUrl());

        final Optional<OAuthToken> token = oAuthAPI.getToken();
        assertEquals(Optional.of(expectedToken), token);
    }

    @Test void getToken_UnparsableResponse_ReturnsEmpty() {
        final BattleNetEntity mockEntity = Mockito.mock(BattleNetEntity.class);
        Mockito.when(mockEntity.toJson()).thenReturn("[123]"); // unparsable for the type

        final BattleNet battleNet = setupBattleNet(mockEntity);
        final OAuthAPI oAuthAPI = basicAPI(mockEntity, battleNet.getRetrofit().baseUrl());
        final Optional<OAuthToken> token = oAuthAPI.getToken();
        assertFalse(token.isPresent());
    }

    @Test void getToken_IOError_ReturnsEmpty() {
        final OAuthToken simpleToken = OAuthToken.builder()
            .build();
        // invalid URL that won't be able to resolve DNS
        final OAuthAPI oAuthAPI = basicAPI(simpleToken, HttpUrl.get("https://willnotrespond.tld"));
        final Optional<OAuthToken> token = oAuthAPI.getToken();
        assertFalse(token.isPresent());
    }

    @Test void battleNet_UsesPostProcessor_SetsBattleNetOnEntity() {
        final OAuthToken expectedToken = OAuthToken.builder()
            .accessToken("foo")
            .expiresIn(123L)
            .tokenType("access_token")
            .build();

        final BattleNet battleNet = setupBattleNet(expectedToken);
        final OAuthAPI oAuthAPI = basicAPI(battleNet.getRetrofit().baseUrl(), battleNet);

        final Optional<OAuthToken> token = oAuthAPI.getToken();
        assertTrue(token.isPresent());
        assertSame(battleNet, token.get().getBattleNet());
    }
}
