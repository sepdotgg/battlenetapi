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

package gg.sep.battlenet.wow.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import gg.sep.battlenet.model.AbstractBattleNetEntity;
import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.result.Err;
import gg.sep.result.Ok;
import gg.sep.result.Result;

/**
 * Abstract implementation of {@link Keyed}, which implements a base helper method that
 * sub-classes can use in their own {@link #getFullItem()} implementation.
 *
 * @param <T> The type of the full item that will be returned by a call to {@link Keyed#getFullItem()}.
 */
@Getter
@Log4j2
public abstract class AbstractKeyedEntity<T extends BattleNetEntity> extends AbstractBattleNetEntity implements Keyed<T> {

    private WoWKey<T> key;

    /**
     * Helper method that sub-classes can use in their own {@link #getFullItem()} implementation
     * to return the full version of an API item.
     *
     * <p>This method performs a {@code GET} request to the full URL contained in {@link #getKey()}
     * and converts it to the model specified by {@code T}.
     *
     * @param clazz Class of {@code T} which will be used to parse the API response.
     * @return {@link Ok} result containing of {@code T} if the API call was successful, otherwise an {@link Err}
     *         containing the error message.
     */
    protected Result<T, String> getFullItem(final Class<T> clazz) {
        return key.getItem(clazz);
    }
}
