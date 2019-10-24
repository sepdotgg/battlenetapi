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

import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import gg.sep.battlenet.api.BattleNetAPIProxy;
import gg.sep.battlenet.model.AbstractJsonEntity;

/**
 * Provides access to the Battle.net APIs.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference
 *
 * <p>Client IDs and secrets are created by registering an application on the Battle.net developer portal:
 * https://develop.battle.net/documentation/guides/getting-started
 */
public class BattleNet {
    private static final String BATTLENET_API_BASE_URL = "https://us.api.blizzard.com/"; // TODO: Support regions

    @Getter private final BattleNetAPIProxy proxy;
    @Getter private final Retrofit retrofit = initRetrofit();

    /**
     * Create a new instance of the Battle.net API client using the specified application Client ID and secret.
     * @param clientId Client ID of the Battle.net API application.
     * @param clientSecret Client Secret key of the Battle.net API application.
     */
    public BattleNet(final String clientId, final String clientSecret) {
        this.proxy = new BattleNetAPIProxy(this);
    }

    /**
     * Build an instance of the default {@link Retrofit} API library for the Battle.net API.
     * @return Completed instance of the Retrofit API library.
     */
    private Retrofit initRetrofit() {
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(AbstractJsonEntity.defaultGson()))
            .client(httpClientBuilder.build())
            .baseUrl(BATTLENET_API_BASE_URL)
            .build();
    }
}
