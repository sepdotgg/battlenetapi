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

package gg.sep.battlenet.api;

import java.io.IOException;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.JsonParseException;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.battlenet.util.Waits;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Simple "proxy" class which handles actually executing the {@link retrofit2.Retrofit} API {@link Call}s.
 *
 * <p>This proxy handles simple rate limiting and retries when Battle.net throttles.
 */
@Log4j2
public final class BattleNetAPIProxy {
    private static final int DEFAULT_MAX_THROTTLE_RETRIES = 10;
    private static final int RETRY_INTERVAL_MS = 500;
    private static final int DEFAULT_RATE_LIMIT_PER_SECOND = 10;
    private final BattleNet battleNet;
    private final RateLimiter rateLimiter;
    @Setter
    private int maxThrottleRetries;

    /**
     * Create a new proxy for the specified Battle.net API client with the default rate limit.
     *
     * @param battleNet Battle.net Client instance.
     */
    public BattleNetAPIProxy(final BattleNet battleNet) {
        this(battleNet, DEFAULT_RATE_LIMIT_PER_SECOND);
    }

    /**
     * Create a new proxy for the specified Battle.net API client with the specified rate limit in requests per second.
     *
     * @param battleNet Battle.net Client instance.
     * @param requestsPerSecond Maximum number of requests per second to call the API.
     */
    public BattleNetAPIProxy(final BattleNet battleNet, final long requestsPerSecond) {
        this.battleNet = battleNet;
        this.rateLimiter = RateLimiter.create(DEFAULT_RATE_LIMIT_PER_SECOND);
        this.maxThrottleRetries = DEFAULT_MAX_THROTTLE_RETRIES;
    }

    /**
     * Executes the Retrofit {@link Call}s, accounting for rate limits and throttling.
     *
     * @param call Retrofit API call to execute.
     * @param <T> Type of the call's response model.
     * @return An {@link gg.sep.result.Ok} result containing the entity {@code T} if the API call
     *         was successful, otherwise an {@link gg.sep.result.Err} containing the error message.
     */
    public <T extends BattleNetEntity> Result<T, String> getResponse(final Call<T> call) {
        return getResponse(call, 0);
    }

    /**
     * Executes the Retrofit {@link Call}s, accounting for rate limits and throttling.
     *
     * @param call Retrofit API call to execute.
     * @param prevCount Number representing the previous number of attempts, in the case of retries.
     * @param <T> Type of the call's response model.
     * @return An {@link gg.sep.result.Ok} result containing the entity {@code T} if the API call
     *         was successful, otherwise an {@link gg.sep.result.Err} containing the error message.
     */
    private <T extends BattleNetEntity> Result<T, String> getResponse(final Call<T> call, final long prevCount) {
        try {
            final double waitTimeMs = this.rateLimiter.acquire() * 1000; // acquire a permit and convert it to millis
            final Response<T> apiResponse = call.execute();
            log.debug("BattleNet API | path={}, rateLimitWaitMs={}", call.request().url().encodedPath(), waitTimeMs);
            // check if we're being throttled
            if (apiResponse.code() == 429) {
                final long currAttempt = prevCount + 1;
                log.warn("[Response 429] Battle.Net is throttling requests. Attempt {}/{}",
                    currAttempt, maxThrottleRetries);
                // quit if we hit the maximum number of retries
                if (currAttempt == maxThrottleRetries) {
                    log.error("Maximum retries hit. Stopping...");
                    return Err.of("Maximum number of throttle retries hit");
                }
                Waits.simpleSleep(RETRY_INTERVAL_MS);
                return getResponse(call.clone(), currAttempt);
            }
            final T body = apiResponse.body();
            if (body == null) {
                final String error = "Unable to get an API response from Battle.net: " + apiResponse.raw().request().url();
                log.error(error);
                return Err.of(error);
            }
            return Ok.of(body);
        } catch (final IOException | JsonParseException e) {
            return Err.of(String.format("Error retrieving valid response from Battle.net. url=%s, exception=%s",
                call.request().url(), e));
        }

    }
}
