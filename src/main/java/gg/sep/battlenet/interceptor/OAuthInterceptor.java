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

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import gg.sep.battlenet.auth.api.OAuthAPI;
import gg.sep.battlenet.auth.model.OAuthToken;
import gg.sep.result.Result;

/**
 * HTTP Request interceptor that handles adding the client's OAuth Access token to each
 * Battle.net API request made with the HTTP Client that has this interceptor.
 *
 * The OAuth token is stored on the instance, so in general it'll only be called once and will be reused. If it expires,
 * a new one can be retrieved and stored again via {@link #refreshToken()}.
 */
public final class OAuthInterceptor implements Interceptor {

    private transient OAuthToken oAuthToken;
    private OAuthAPI oAuthAPI;

    /**
     * Creates an instance of the interceptor with the specified {@link OAuthAPI}.
     *
     * {@link OAuthAPI} will be used to retrieve a new OAuth access token if it is not already known.
     * @param oAuthAPI Instance of OAuthAPI that can be used to retrieve the  OAuth access token.
     */
    public OAuthInterceptor(final OAuthAPI oAuthAPI) {
        this.oAuthAPI = oAuthAPI;
    }

    private OAuthToken getToken() {
        if (oAuthToken != null) {
            return oAuthToken;
        }
        return refreshToken();
    }

    private OAuthToken refreshToken() {
        final Result<OAuthToken, String> tokenRequest = oAuthAPI.getToken();
        this.oAuthToken = tokenRequest.getOk().orElseThrow(RuntimeException::new);
        return this.oAuthToken;
    }

    /**
     * Intercept's the HTTP request and appends the OAuth Token query parameter to the request, if
     * it is not already present.
     * @param chain HTTP request chain.
     * @return The HTTP response that is the result of modifying the request.
     * @throws IOException Thrown if there is an issue completing the HTTP request.
     */
    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final HttpUrl originalUrl = originalRequest.url();
        final String originalToken = originalUrl.queryParameter("access_token");

        if (originalToken != null) {
            return chain.proceed(originalRequest);
        }
        final HttpUrl newUrl = originalUrl.newBuilder()
            .addQueryParameter("access_token", getToken().getAccessToken())
            .build();
        final Request newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build();
        return chain.proceed(newRequest);
    }
}
