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

import okhttp3.HttpUrl;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import retrofit2.Call;
import retrofit2.Response;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.result.Result;

/**
 * Tests for {@link BattleNetAPIProxy}.
 */
@SuppressWarnings("unchecked")
public class BattleNetAPIProxyTest {

    private BattleNetAPIProxy getProxy(final BattleNet mockBattleNet, final Call mockCall,
                                       final BattleNetEntity mockObject, final int maxThrottleRetries,
                                       final int responseCode, final boolean succeedAfter) throws Exception {
        final Request mockRequest = Mockito.mock(Request.class);
        final Response<BattleNetEntity> mockResponse = Mockito.mock(Response.class);
        final okhttp3.Response mockRawResponse = Mockito.mock(okhttp3.Response.class);
        final HttpUrl httpUrl = HttpUrl.get("https://sep.gg");

        Mockito.when(mockCall.clone()).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(mockResponse);
        Mockito.when(mockCall.request()).thenReturn(mockRequest);
        Mockito.when(mockRequest.url()).thenReturn(httpUrl);
        final OngoingStubbing<Integer> responseCodeStub = Mockito.when(mockResponse.code()).thenReturn(responseCode);
        if (succeedAfter) {
            responseCodeStub.thenReturn(200);
        }
        Mockito.when(mockResponse.body()).thenReturn(mockObject);
        Mockito.when(mockResponse.raw()).thenReturn(mockRawResponse);
        Mockito.when(mockRawResponse.request()).thenReturn(mockRequest);

        final BattleNetAPIProxy proxy = new BattleNetAPIProxy(mockBattleNet);
        if (maxThrottleRetries > 0) {
            proxy.setMaxThrottleRetries(maxThrottleRetries);
        }
        return proxy;
    }

    @Test
    void getResponse_HitMaxRetries_IsErr() throws Exception {
        final Call<BattleNetEntity> mockCall = Mockito.mock(Call.class);
        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);
        final BattleNetAPIProxy proxy = getProxy(mockBattleNet, mockCall, null, 2,
            429, false);
        proxy.setMaxThrottleRetries(2);
        final Result<?, String> response = proxy.getResponse(mockCall);
        Assertions.assertEquals("Maximum number of throttle retries hit", response.unwrapErr());
    }

    @Test
    void getResponse_GetsThrottled_SucceedsNextTry() throws Exception {
        final BattleNetEntity mockObject = Mockito.mock(BattleNetEntity.class);
        final Call<BattleNetEntity> mockCall = Mockito.mock(Call.class);
        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);

        final BattleNetAPIProxy proxy = getProxy(mockBattleNet, mockCall, mockObject, 4, 429,
            true);
        final Result<BattleNetEntity, String> responseObject = proxy.getResponse(mockCall);
        Assertions.assertTrue(responseObject.isOk());
        Assertions.assertEquals(mockObject, responseObject.unwrap());
        Mockito.verify(mockCall, Mockito.times(2)).execute();
    }

    @Test
    void getResponse_ReturnsObject() throws Exception {
        final BattleNetEntity mockObject = Mockito.mock(BattleNetEntity.class);
        final Call<BattleNetEntity> mockCall = Mockito.mock(Call.class);
        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);

        final BattleNetAPIProxy proxy = getProxy(mockBattleNet, mockCall, mockObject, 1, 200,
            false);
        final Result<BattleNetEntity, String> responseObject = proxy.getResponse(mockCall);

        Assertions.assertTrue(responseObject.isOk());
        Assertions.assertEquals(mockObject, responseObject.unwrap());
    }

    @Test void getResponse_NullBody_ReturnsErr() throws Exception {
        final Call<BattleNetEntity> mockCall = Mockito.mock(Call.class);
        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);

        final BattleNetAPIProxy proxy = getProxy(mockBattleNet, mockCall, null, 1, 200,
            false);
        final Result<BattleNetEntity, String> responseObject = proxy.getResponse(mockCall);

        Assertions.assertTrue(responseObject.isErr());
        Assertions.assertTrue(responseObject.unwrapErr().startsWith("Unable to get an API response from Battle.net:"));
    }
}
