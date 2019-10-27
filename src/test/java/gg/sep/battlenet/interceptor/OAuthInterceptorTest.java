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

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gg.sep.battlenet.auth.api.OAuthAPI;
import gg.sep.battlenet.auth.model.OAuthToken;
import gg.sep.result.Ok;

/**
 * Unit tests for {@link OAuthInterceptor}.
 */
public class OAuthInterceptorTest {

    private static Chain basicChain(final boolean hasAccessToken,
                                    final Request.Builder requestBuilder) throws IOException {
        final Chain mockChain = Mockito.mock(Chain.class);
        final Request mockRequest = Mockito.mock(Request.class);
        final Response mockResponse = Mockito.mock(Response.class);
        final HttpUrl requestUrl = (hasAccessToken) ? HttpUrl.get("https://sep.gg/?access_token=foo") :
            HttpUrl.get("https://sep.gg");

        Mockito.when(mockChain.request()).thenReturn(mockRequest);
        Mockito.when(mockRequest.url()).thenReturn(requestUrl);
        Mockito.when(mockRequest.newBuilder()).thenReturn(requestBuilder);
        Mockito.when(mockChain.proceed(Mockito.any())).thenReturn(mockResponse);
        return mockChain;
    }

    @Test void intercept_RetrievesTokenFromOAuthAPI() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final OAuthInterceptor interceptor = new OAuthInterceptor(mockOAuthAPI);

        final OAuthToken expectedToken = OAuthToken.builder()
            .accessToken("fooAccessToken")
            .build();
        final Request.Builder newRequestBuilder = new Request.Builder();

        Mockito.when(mockOAuthAPI.getToken()).thenReturn(Ok.of(expectedToken));
        final Chain mockChain = basicChain(false, newRequestBuilder);
        interceptor.intercept(mockChain);

        // check that the new access token matches
        final HttpUrl requestUrl = newRequestBuilder.build().url();
        assertEquals(expectedToken.getAccessToken(), requestUrl.queryParameter("access_token"));
    }

    @Test void intercept_UsesStoredToken() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final OAuthInterceptor interceptor = new OAuthInterceptor(mockOAuthAPI);

        final OAuthToken expectedToken = OAuthToken.builder()
            .accessToken("fooAccessToken")
            .build();
        final OAuthToken secondRequestToken = OAuthToken.builder()
            .accessToken("FAILED_IF_THIS_TOKEN_IS_USED")
            .build();
        final Request.Builder newRequestBuilder = new Request.Builder();

        Mockito.when(mockOAuthAPI.getToken()).thenReturn(Ok.of(expectedToken))
            .thenReturn(Ok.of(secondRequestToken));
        final Chain mockChain = basicChain(false, newRequestBuilder);
        interceptor.intercept(mockChain);
        interceptor.intercept(mockChain);

        // check that the new access token matches the cached token
        final HttpUrl requestUrl = newRequestBuilder.build().url();
        assertEquals(expectedToken.getAccessToken(), requestUrl.queryParameter("access_token"));
    }

    @Test void intercept_DoesNotOverwriteTokenOnUrl() throws Exception {
        final OAuthAPI mockOAuthAPI = Mockito.mock(OAuthAPI.class);
        final OAuthInterceptor interceptor = new OAuthInterceptor(mockOAuthAPI);
        final Request.Builder newRequestBuilder = new Request.Builder();

        final Chain mockChain = basicChain(true, newRequestBuilder);
        interceptor.intercept(mockChain);

        // check that proceed was only ever called once
        Mockito.verify(mockChain, Mockito.times(1)).proceed(Mockito.any());
        // check that the original request was used
        Mockito.verify(mockChain, Mockito.times(1)).proceed(Mockito.eq(mockChain.request()));

    }
}
