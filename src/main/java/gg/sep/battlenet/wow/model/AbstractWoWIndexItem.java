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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import gg.sep.battlenet.model.BattleNetEntity;
import gg.sep.battlenet.model.JsonSerializable;

/**
 * Abstract implementation of {@link WoWIndexItem}, which implements the core getters for index items.
 *
 * @param <T> The type of the full item that will be returned by a call to {@link Keyed#getFullItem()}.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Log4j2
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractWoWIndexItem<T extends BattleNetEntity> extends AbstractKeyedEntity<T>
    implements WoWIndexItem<T>, JsonSerializable {
    private Long id;
    @EqualsAndHashCode.Exclude
    private String name;
}
