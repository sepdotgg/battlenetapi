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

package gg.sep.battlenet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.auth.api.OAuthAPI;
import gg.sep.battlenet.auth.model.OAuthToken;
import gg.sep.battlenet.model.BattleNetLocale;
import gg.sep.battlenet.model.BattleNetRegion;
import gg.sep.result.Ok;

/**
 * Unit tests for {@link BattleNetInterceptor}.
 */
public class BattleNetInterceptorTest {

    private BattleNet simpleBattleNet(final BattleNetRegion region, final BattleNetLocale locale) {
        return BattleNet.builder()
            .clientSecret("")
            .clientId("")
            .region(region)
            .locale(locale)
            .build();
    }

    private BattleNet simpleBattleNet() {
        return simpleBattleNet(null, null);
    }

    private static Chain basicChain(final boolean hasAccessToken,
                                    final boolean hasLocale,
                                    final String addNamespace,
                                    final Request.Builder requestBuilder) throws IOException {
        final Chain mockChain = Mockito.mock(Chain.class);
        final Request mockRequest = Mockito.mock(Request.class);
        final Response mockResponse = Mockito.mock(Response.class);

        final HttpUrl.Builder urlBuilder = HttpUrl.get("https://sep.gg").newBuilder();

        if (hasAccessToken) {
            urlBuilder.addQueryParameter("access_token", "foo");
        }
        if (hasLocale) {
            urlBuilder.addQueryParameter("locale", "en_US");
        }
        if (addNamespace != null) {
            urlBuilder.addQueryParameter("addNamespace", addNamespace);
        }


        Mockito.when(mockChain.request()).thenReturn(mockRequest);
        Mockito.when(mockRequest.url()).thenReturn(urlBuilder.build());
        Mockito.when(mockRequest.newBuilder()).thenReturn(requestBuilder);
        Mockito.when(mockChain.proceed(Mockito.any())).thenReturn(mockResponse);
        return mockChain;
    }

    @Test void intercept_RetrievesTokenFromOAuthAPI() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, simpleBattleNet());

        final OAuthToken expectedToken = OAuthToken.builder()
            .accessToken("fooAccessToken")
            .build();
        final Request.Builder newRequestBuilder = new Request.Builder();

        Mockito.when(mockOAuthAPI.getToken()).thenReturn(Ok.of(expectedToken));
        final Chain mockChain = basicChain(false, false, null, newRequestBuilder);
        interceptor.intercept(mockChain);

        // check that the new access token matches
        final HttpUrl requestUrl = newRequestBuilder.build().url();
        assertEquals(expectedToken.getAccessToken(), requestUrl.queryParameter("access_token"));
    }

    @Test void intercept_UsesStoredToken() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, simpleBattleNet());

        final OAuthToken expectedToken = OAuthToken.builder()
            .accessToken("fooAccessToken")
            .build();
        final OAuthToken secondRequestToken = OAuthToken.builder()
            .accessToken("FAILED_IF_THIS_TOKEN_IS_USED")
            .build();
        final Request.Builder newRequestBuilder = new Request.Builder();

        Mockito.when(mockOAuthAPI.getToken()).thenReturn(Ok.of(expectedToken))
            .thenReturn(Ok.of(secondRequestToken));
        final Chain mockChain = basicChain(false, false, null, newRequestBuilder);
        interceptor.intercept(mockChain);
        interceptor.intercept(mockChain);

        // check that the new access token matches the cached token
        final HttpUrl requestUrl = newRequestBuilder.build().url();
        assertEquals(expectedToken.getAccessToken(), requestUrl.queryParameter("access_token"));
    }

    @Test void intercept_DoesNotOverwriteTokenOnUrl() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, simpleBattleNet());
        final Request.Builder newRequestBuilder = new Request.Builder();

        final Chain mockChain = basicChain(true, false, null, newRequestBuilder);
        interceptor.intercept(mockChain);

        // check that proceed was only ever called once
        Mockito.verify(mockChain, Mockito.times(1)).proceed(Mockito.any());
        // check that the original request was used
        Mockito.verify(mockChain, Mockito.times(1)).proceed(Mockito.eq(mockChain.request()));
    }

    @Test void intercept_addsNamespace() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNet battleNet = simpleBattleNet(BattleNetRegion.EUROPE, BattleNetLocale.DE_DE);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, battleNet);

        final Request.Builder newRequestBuilder = new Request.Builder();
        final Chain mockChain = basicChain(true, false, "static", newRequestBuilder);
        interceptor.intercept(mockChain);
        assertEquals("static-eu", newRequestBuilder.build().url().queryParameter("namespace"));
    }

    @Test void intercept_doesNotAddInvalidNamespace() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNet battleNet = simpleBattleNet(BattleNetRegion.EUROPE, BattleNetLocale.DE_DE);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, battleNet);

        final Request.Builder newRequestBuilder = new Request.Builder();
        final Chain mockChain = basicChain(true, false, "invalid", newRequestBuilder);
        interceptor.intercept(mockChain);
        assertNull(newRequestBuilder.build().url().queryParameter("namespace"));
    }

    @Test void intercept_addsLocale() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNet battleNet = simpleBattleNet(BattleNetRegion.EUROPE, BattleNetLocale.DE_DE);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, battleNet);

        final Request.Builder newRequestBuilder = new Request.Builder();
        final Chain mockChain = basicChain(true, false, null, newRequestBuilder);
        interceptor.intercept(mockChain);
        assertEquals("de_DE", newRequestBuilder.build().url().queryParameter("locale"));
    }

    @Test void intercept_doesNotOverrideLocale() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final BattleNet battleNet = simpleBattleNet(BattleNetRegion.EUROPE, BattleNetLocale.DE_DE);
        final BattleNetInterceptor interceptor = new BattleNetInterceptor(mockOAuthAPI, battleNet);

        final Request.Builder newRequestBuilder = new Request.Builder();
        final Chain mockChain = basicChain(true, true, null, newRequestBuilder);
        interceptor.intercept(mockChain);
        assertEquals("en_US", newRequestBuilder.build().url().queryParameter("locale"));
    }
}
