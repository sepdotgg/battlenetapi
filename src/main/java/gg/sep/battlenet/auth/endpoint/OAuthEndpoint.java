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

package gg.sep.battlenet.auth.endpoint;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

import gg.sep.battlenet.auth.model.OAuthToken;

/**
 * Endpoints for the Battle.net OAuth APIs.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference/oauth-api
 */
public interface OAuthEndpoint {
    /**
     * The OAuth Access Token request API located at POST /oauth/token.
     *
     * Using the specified client credentials, retrieves a {@link OAuthToken} containing the application's
     * access token.
     *
     * Because the OAuthEndpoints use a different base URL than the rest of the Battle.net APIs,
     * this endpoint accepts a full URL to the endpoint, which overrides the default API base URL. This
     * URL should be constructed in the implementing API class and passed to the endpoint request.
     *
     * @param fullUrl Full URL to the API endpoint.
     * @param clientCredentials Basic HTTP Authorization credentials consisting of the client ID and secret.
     * @return getAccessToken Retrofit call.
     */
    @POST
    Call<OAuthToken> getAccessToken(@Url String fullUrl, @Header("authorization") String clientCredentials);
}
