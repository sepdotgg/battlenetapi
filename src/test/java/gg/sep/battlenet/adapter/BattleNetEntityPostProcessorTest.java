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

package gg.sep.battlenet.adapter;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.auth.model.OAuthToken;

/**
 * Tests for {@link BattleNetEntityPostProcessor}.
 */
public class BattleNetEntityPostProcessorTest {

    private static final class InnerClass {
        private String foo;
        private InnerClass(final String foo) {
            this.foo = foo;
        }
        private String getFoo() {
            return foo;
        }
    }

    @Test void create_BattleNetEntity_SetsBattleNet() throws Exception {
        final String jsonInput = "{\"access_token\":\"fooToken\"}";
        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);
        final Gson gson = new Gson();

        final BattleNetEntityPostProcessor processor = new BattleNetEntityPostProcessor(mockBattleNet);
        final JsonReader jsonReader = new JsonReader(new StringReader(jsonInput));

        final TypeAdapter<OAuthToken> tokenTypeAdapter = processor.create(gson, new TypeToken<OAuthToken>(){});
        final OAuthToken response = tokenTypeAdapter.read(jsonReader);
        Assertions.assertSame(mockBattleNet, response.getBattleNet());
    }

    @Test void create_NonBattleNetEntity_ReturnsCorrectObject() throws Exception {
        final InnerClass expectedObject = new InnerClass("bar");
        final String jsonInput = "{\"foo\":\"bar\"}";
        final Gson gson = new Gson();

        final BattleNetEntityPostProcessor processor = new BattleNetEntityPostProcessor(null);
        final JsonReader jsonReader = new JsonReader(new StringReader(jsonInput));
        final TypeAdapter<InnerClass> tokenTypeAdapter =
            processor.create(gson, new TypeToken<InnerClass>(){});
        final InnerClass output = tokenTypeAdapter.read(jsonReader);
        Assertions.assertEquals(expectedObject.getFoo(), output.getFoo());
    }
}
