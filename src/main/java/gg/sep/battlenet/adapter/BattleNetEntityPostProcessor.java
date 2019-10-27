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

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.RequiredArgsConstructor;

import gg.sep.battlenet.BattleNet;
import gg.sep.battlenet.model.BattleNetEntity;

/**
 * Gson {@link TypeAdapterFactory} which sets the {@link BattleNet} client object on
 * {@link BattleNetEntity} objects immediately after they have been parsed into the concrete objects by
 * Gson.
 */
@RequiredArgsConstructor
public class BattleNetEntityPostProcessor implements TypeAdapterFactory {

    private final BattleNet battleNet;

    /**
     * {@inheritDoc}
     *
     * Returns a type adaptor for the {@code type} specified. If that type implements {@link BattleNetEntity},
     * then the {@link BattleNet} client which was used to retrieve the entity will be set using
     * {@link BattleNetEntity#setBattleNet(BattleNet)} after the concrete class has been constructed by Gson.
     *
     * If it is not an {@link BattleNetEntity}, returns the same object that Gson would without the adapter.
     *
     * @param gson The Gson instance which will be used to serialize/deserialize.
     * @param type Type token of the object requested to be handled by the {@link TypeAdapter}.
     * @param <T> Type of the object requested to be handled by the {@link TypeAdapter}.
     * @return {@code TypeAdapter} for the type {@code T}.
     */
    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        return new TypeAdapter<T>() {
            /**
             * {@inheritDoc}.
             *
             * No changes in this class's implementation.
             */
            @Override
            public void write(final JsonWriter out, final T value) throws IOException {
                delegate.write(out, value);
            }

            /**
             * If the parsed entity type {@code T} implements {@link BattleNetEntity},
             * sets the {@link BattleNet} client on the entity with {@link BattleNetEntity#setBattleNet(BattleNet)}.
             * @param in JsonReader input.
             * @return Parsed instance of {@code T}. If the type was a {@link BattleNetEntity}, it will
             *         have the {@link BattleNet} client set on it.
             *
             * @throws IOException Thrown by Gson if parsing failed.
             */
            @Override
            public T read(final JsonReader in) throws IOException {
                final T entity = delegate.read(in);
                if (entity instanceof BattleNetEntity) {
                    ((BattleNetEntity) entity).setBattleNet(battleNet);
                }
                return entity;
            }
        };
    }
}
