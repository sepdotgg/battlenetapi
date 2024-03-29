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

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import gg.sep.battlenet.adapter.BattleNetEntityPostProcessor;
import gg.sep.battlenet.adapter.DurationDeserializer;
import gg.sep.battlenet.adapter.InstantDeserializer;
import gg.sep.battlenet.adapter.ZoneIdDeserializer;
import gg.sep.battlenet.api.BattleNetAPIProxy;
import gg.sep.battlenet.auth.api.OAuthAPI;
import gg.sep.battlenet.interceptor.BattleNetInterceptor;
import gg.sep.battlenet.model.BattleNetLocale;
import gg.sep.battlenet.model.BattleNetRegion;
import gg.sep.battlenet.wow.model.talent.TalentTier;
import gg.sep.battlenet.wow.serializer.TalentTierDeserializer;

/**
 * Provides access to the Battle.net APIs.
 *
 * API Reference: https://develop.battle.net/documentation/api-reference
 *
 * <p>Client IDs and secrets are created by registering an application on the Battle.net developer portal:
 * https://develop.battle.net/documentation/guides/getting-started
 */

public final class BattleNet {
    private static final String BATTLENET_API_BASE_URL_F = "https://%s.api.blizzard.com/";

    @Getter
    private BattleNetRegion region;
    @Getter
    private BattleNetLocale locale;
    @Getter
    private final HttpUrl baseUrl;
    @Getter
    private final BattleNetAPIProxy proxy;
    @Getter
    private final Retrofit retrofit;
    @Getter
    private final Gson jsonParser;

    /**
     * Create a new instance of the Battle.net API client using the specified application Client ID and secret.
     * @param clientId Client ID of the Battle.net API application.
     * @param clientSecret Client Secret key of the Battle.net API application.
     * @param baseUrl Base URL of the Battle.net API. Generally you can leave this null, unless you have
     *                good reason to explicitly override it.
     */
    @Builder
    private BattleNet(@NonNull final String clientId, @NonNull final String clientSecret, final HttpUrl baseUrl,
                      final BattleNetRegion region, final BattleNetLocale locale) {

        if (region == null && locale != null) {
            this.locale = locale;
            this.region = locale.getRegion();
        } else {
            this.region = (region == null) ? BattleNetRegion.NORTH_AMERICA : region;
            this.locale = (locale == null) ? this.region.getSupportedLocales().get(0) : locale;
        }

        // check that the locale provided is valid for the region
        if (!this.region.hasLocale(this.locale)) {
            throw new IllegalArgumentException(String.format(
                "Locale '%s' is not supported in region '%s'. Valid locales: %s",
                this.locale, this.region, this.region.getSupportedLocales()
            ));
        }

        if (baseUrl == null) {
            final String formattedUrl = String.format(BATTLENET_API_BASE_URL_F, this.region.getRegionUrlValue());
            this.baseUrl = HttpUrl.get(formattedUrl);
        } else {
            this.baseUrl = baseUrl;
        }

        this.proxy = new BattleNetAPIProxy(this);
        this.jsonParser = buildJsonParser();

        final OAuthAPI oAuthAPI = OAuthAPI.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .battleNet(this)
            .build();
        this.retrofit = initRetrofit(this.baseUrl, oAuthAPI);
    }



    /**
     * Creates a new instance of the Gson JSON parser that will be used by the Battle.net client.
     *
     * <p>This should register whatever custom type adapters and factories that are needed to construct, serialize,
     * and deserialize API responses.
     *
     * @return New instance of Gson which can be used to serialize/deserialize Battle.net JSON and objects.
     */
    private Gson buildJsonParser() {
        return new GsonBuilder()
            .registerTypeAdapterFactory(new BattleNetEntityPostProcessor(this))
            .registerTypeAdapter(TalentTier.class, new TalentTierDeserializer(this))
            .registerTypeAdapter(ZoneId.class, new ZoneIdDeserializer())
            .registerTypeAdapter(Instant.class, new InstantDeserializer())
            .registerTypeAdapter(Duration.class, new DurationDeserializer())
            .create();
    }

    /**
     * Build an instance of the default {@link Retrofit} API library for the Battle.net API.
     * @return Completed instance of the Retrofit API library.
     */
    private Retrofit initRetrofit(final HttpUrl apiBaseUrl, final OAuthAPI oAuthAPI) {
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new BattleNetInterceptor(oAuthAPI, this));
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(jsonParser))
            .client(httpClientBuilder.build())
            .baseUrl(apiBaseUrl)
            .build();
    }
}
