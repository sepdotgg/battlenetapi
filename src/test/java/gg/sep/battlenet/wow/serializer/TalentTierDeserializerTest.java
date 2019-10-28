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

package gg.sep.battlenet.wow.serializer;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.wow.model.talent.TalentIndexItem;
import gg.sep.battlenet.wow.model.talent.TalentSpellTooltip;
import gg.sep.battlenet.wow.model.talent.TalentTier;

/**
 * Unit tests for {@link TalentTierDeserializer}.
 */
public class TalentTierDeserializerTest {

    @Test void deserialize_NullObjectReturnsNull() {
        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);
        final TalentTierDeserializer deserializer = new TalentTierDeserializer(mockBattleNet);
        assertNull(deserializer.deserialize(null, TalentTier.class, null));
    }

    @Test void deserialize_ValidTalentTierParsesCorrectly() {
        // the imput doesn't matter as much as the correct entites being set on the result
        final Map<String, Object> input = ImmutableMap.of(
            "level", 15,
            "talents", ImmutableList.of(
                ImmutableMap.of(
                    "talent", ImmutableMap.of(
                        "name", "Earthen Rate",
                        "id", 22356
                    ),
                    "spell_tooltip", ImmutableMap.of(
                        "description", "Earthen Rage Description",
                        "cast_time", "1 second",
                        "range", "40 yd range",
                        "cooldown", "12 seconds"
                    )
                )
            )
        );
        final JsonElement inputElement = new Gson().toJsonTree(input);

        final BattleNet mockBattleNet = Mockito.mock(BattleNet.class);
        final JsonDeserializationContext mockCtx = Mockito.mock(JsonDeserializationContext.class);

        final TalentIndexItem indexItem = new TalentIndexItem();
        final TalentSpellTooltip spellTooltip = new TalentSpellTooltip();

        Mockito.when(mockCtx.deserialize(Mockito.any(), Mockito.eq(TalentIndexItem.class))).thenReturn(indexItem);
        Mockito.when(mockCtx.deserialize(Mockito.any(), Mockito.eq(TalentSpellTooltip.class))).thenReturn(spellTooltip);

        final TalentTierDeserializer deserializer = new TalentTierDeserializer(mockBattleNet);
        final TalentTier talentTier = deserializer.deserialize(inputElement, TalentTier.class, mockCtx);
        assertSame(spellTooltip, talentTier.getTalents().get(0).getSpellTooltip());
    }
}
