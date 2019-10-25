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

package gg.sep.battlenet.auth.api;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.api.BattleNetAPI;
import gg.sep.battlenet.auth.endpoint.OAuthEndpoint;
import gg.sep.battlenet.auth.model.OAuthToken;
import gg.sep.battlenet.model.AbstractJsonEntity;

/**
 * This class implements the retrieval of resources at the Battle.net OAuth API endpoint,
 * modeled by {@link OAuthEndpoint}.
 *
 * <p>API Reference: https://develop.battle.net/documentation/api-reference/oauth-api
 *
 * Handles the retrieval of application access tokens and other other OAuth related requests.
 */
@Log4j2
public final class OAuthAPI extends BattleNetAPI {
    private static final String BATTLE_NET_OAUTH_BASE_URL = "https://us.battle.net/"; // TODO: Support multiple regions
    private static final String TOKEN_POST_PATH = "oauth/token?grant_type=client_credentials";

    private final transient String basicAuthCredentials;
    private final OAuthEndpoint oAuthEndpoint;

    @Getter
    private HttpUrl baseUrl;
    private Retrofit retrofit;


    /**
     * Initialize the OAuth API using the specified client ID, secret, and BattleNet instance.
     *
     * <p>The OAuth API is the only API in Battle.net that requires the client ID and secret,
     * so this class needs to accept them as parameters and store them internally for use in the API request.
     * @param clientId Client ID of the application.
     * @param clientSecret Client Secret key of the application.
     * @param battleNet Instance of the BattleNet client.
     */
    @Builder
    private OAuthAPI(final String clientId, final String clientSecret, final BattleNet battleNet, final HttpUrl baseUrl) {
        super(battleNet);
        this.basicAuthCredentials = Credentials.basic(clientId, clientSecret);

        // the order of these initializing is important
        this.baseUrl = (baseUrl == null) ? HttpUrl.get(BATTLE_NET_OAUTH_BASE_URL) : baseUrl;
        this.retrofit = initOAuthRetrofit(this.baseUrl);
        this.oAuthEndpoint = this.retrofit.create(OAuthEndpoint.class);

    }

    private Retrofit initOAuthRetrofit(final HttpUrl oAuthBaseUrl) {
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(AbstractJsonEntity.defaultGson()))
            .client(httpClientBuilder.build())
            .baseUrl(oAuthBaseUrl)
            .build();
    }

    /**
     * Retrieve the client credentials grant response from the OAuthToken API.
     * Action: POST /oauth/token
     * @return Optional of the OAuthToken response, if the call was successful, otherwise empty.
     */
    public Optional<OAuthToken> getToken() {
        final Call<OAuthToken> call = oAuthEndpoint.getAccessToken(baseUrl + TOKEN_POST_PATH, basicAuthCredentials);
        return executeCall(call);
    }
}
