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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.wow.model.talent.TalentIndexItem;
import gg.sep.battlenet.wow.model.talent.TalentSpellTooltip;
import gg.sep.battlenet.wow.model.talent.TalentTier;

/**
 * Deserializer for {@link TalentTier} WoW API entities.
 *
 * <p>Unfortunately, the talents returned inside the {@link TalentTier#getTalents()} aren't simply a list
 * of {@link TalentIndexItem}s, but rather more complex objects that nest the item one more level deep,
 * and also include a {@code spell_tooltip} sibling field.
 *
 * <p>This deserializer merges the spell tooltip into the {@link TalentIndexItem} entity, and simply
 * returns a list of the combined objects in {@link TalentTier#getTalents()}.
 */
@RequiredArgsConstructor
public class TalentTierDeserializer implements JsonDeserializer<TalentTier> {

    private final BattleNet battleNet;

    @Override
    public TalentTier deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return null;
        }

        final JsonObject jsonObject = json.getAsJsonObject();
        final Long level = jsonObject.get("level").getAsLong();
        final JsonArray talents = jsonObject.get("talents").getAsJsonArray();

        final List<TalentIndexItem> indexItems = new ArrayList<>();
        for (final JsonElement talentElement : talents) {
            final JsonElement innerTalent = talentElement.getAsJsonObject().get("talent");
            final JsonElement innerTooltip = talentElement.getAsJsonObject().get("spell_tooltip");

            final TalentIndexItem indexItem = context.deserialize(innerTalent, TalentIndexItem.class);
            final TalentSpellTooltip tooltipItem = context.deserialize(innerTooltip, TalentSpellTooltip.class);
            indexItem.setSpellTooltip(tooltipItem);
            indexItems.add(indexItem);
        }
        final TalentTier talentTier = TalentTier.builder()
            .level(level)
            .talents(indexItems)
            .build();
        talentTier.setBattleNet(battleNet);
        return talentTier;
    }
}
