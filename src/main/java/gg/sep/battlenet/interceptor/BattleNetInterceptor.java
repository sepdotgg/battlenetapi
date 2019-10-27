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
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.auth.api.OAuthAPI;
import gg.sep.battlenet.auth.model.OAuthToken;
import gg.sep.result.Result;

/**
 * okhttp3 request interceptor for performing various tasks on the Battle.net API urls
 * and requests prior to forwarding them, such as adding OAuthTokens, WoW namespaces, and locales.
 */
public class BattleNetInterceptor implements Interceptor {

    private static final Set<String> NAMESPACES = ImmutableSet.of("static", "dynamic", "profile");


    private final OAuthAPI oAuthAPI;
    private final String regionSuffix;
    private final String localeValue;

    private OAuthToken oAuthToken;

    /**
     * Create the interceptor with the specified built OAuthAPI and Battle.net Client.
     * @param oAuthAPI OAuthAPI class for getting OAuthAccess tokens if the request does not already have one.
     * @param battleNet Battle.net client which is making the requests.
     */
    public BattleNetInterceptor(final OAuthAPI oAuthAPI, final BattleNet battleNet) {
        this.oAuthAPI = oAuthAPI;
        this.regionSuffix = battleNet.getRegion().getRegionUrlValue();
        this.localeValue = battleNet.getLocale().getLocaleString();
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
     * Intercepts the HTTP request prior to sending it and performs several tasks:
     *
     * <ul>
     *     <li>Adds an OAuth access token if there is not already one on the request</li>
     *     <li>Adds the Battle.net client's locale if the request is not already one on the request</li>
     *     <li>Replaces any {@code addNamespace} query parameter with {@code namespace} and a value in the format
     *         of {@code {namespace}-{region}}, provided the value of {@code addNamespace} is one of the valid
     *         namespaces. This only applies to WoW APIs. See the docs:
     *         https://develop.battle.net/documentation/guides/game-data-apis-wow-namespaces
     *     </li>
     * </ul>
     * @param chain The okhttp3 request chain prior to being sent.
     * @return The response, forwarded by the chain.
     * @throws IOException Thrown by okhttp3 if the request fails.
     */
    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final HttpUrl originalUrl = originalRequest.url();
        final HttpUrl.Builder newUrlBuilder = originalUrl.newBuilder();

        handleOAuth(originalUrl, newUrlBuilder);
        handleWoWNamespace(originalUrl, newUrlBuilder);
        handleLocale(originalUrl, newUrlBuilder);

        final Request newRequest = originalRequest.newBuilder()
            .url(newUrlBuilder.build())
            .build();
        return chain.proceed(newRequest);
    }

    private void handleOAuth(final HttpUrl originalUrl, final HttpUrl.Builder urlBuilder) {
        final String originalToken = originalUrl.queryParameter("access_token");
        if (originalToken == null) {
            urlBuilder.addQueryParameter("access_token", getToken().getAccessToken());
        }
    }

    private void handleWoWNamespace(final HttpUrl originalUrl, final HttpUrl.Builder urlBuilder) {
        final String addNamespace = originalUrl.queryParameter("addNamespace");
        if (NAMESPACES.contains(addNamespace)) {
            final String newNamespace = addNamespace + "-" + regionSuffix;
            urlBuilder.removeAllQueryParameters("addNamespace")
                .addQueryParameter("namespace", newNamespace);
        }
    }

    private void handleLocale(final HttpUrl originalUrl, final HttpUrl.Builder urlBuilder) {
        final String originalLocale = originalUrl.queryParameter("locale");
        if (originalLocale == null) {
            urlBuilder.addQueryParameter("locale", localeValue);
        }
    }
}
