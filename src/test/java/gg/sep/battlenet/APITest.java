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

package gg.sep.battlenet;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import gg.sep.battlenet.model.BattleNetEntity;

/**
 * Base abstract class for the various Battle.net API class tests, providing common methods for building test data.
 */
public abstract class APITest {

    /**
     * Creates a basic Mock Web server with a successful JSON response containing the specified entity.
     *
     * @param responseEntity JSON serializable entity to be used as the body of the response.
     * @return URL of the web server's URL which will respond to the request.
     */
    private void setupSingleEntityResponse(final MockWebServer mockWebServer, final BattleNetEntity responseEntity) {

        final MockResponse mockResponse = new MockResponseBuilder()
            .success()
            .json(responseEntity)
            .build();

        enqueueResponses(mockWebServer, ImmutableList.of(mockResponse));
    }

    private MockWebServer buildMockWebServer() {
        return new MockWebServer();
    }

    /**
     * Enqueues the specified mocked responses to the web server.
     *
     * @param webServer MockWebServer on which to enqueue responses.
     * @param responses Collection of MockResponses to enqueue on the server.
     */
    private static void enqueueResponses(final MockWebServer webServer, final Collection<MockResponse> responses) {
        responses.forEach(webServer::enqueue);
    }

    /**
     * Creates a new Battle.net instance with the specified JSON entity that will be returned from
     * the various internal APIs.
     *
     * @param responseEntity JSON serializable response entity to be returned from the API calls.
     * @return New Battle.net instance which will call a Mocked web server rather than the real API.
     */
    protected BattleNet setupBattleNet(final BattleNetEntity responseEntity) {
        final MockWebServer mockWebServer = buildMockWebServer();
        final HttpUrl baseUrl = mockWebServer.url("/");
        final BattleNet battleNet = BattleNet.builder()
            .clientId("")
            .clientSecret("")
            .baseUrl(baseUrl)
            .build();
        responseEntity.setBattleNet(battleNet);
        setupSingleEntityResponse(mockWebServer, responseEntity);
        return battleNet;
    }
}
