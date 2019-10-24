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

package gg.sep.battlenet.model;

import gg.sep.battlenet.BattleNet;

/**
 * Represents any one of the object responses from the Battle.net API.
 *
 * <p>Used so we can infer the type of our API models and assign a reference to the Battle.net client instance.
 * This is useful for writing {@code BattleNetObject} subclass which themselves can call back into the API, eg,
 * retrieving one of the keyed full objects. // TODO link to keyed
 */
public interface BattleNetEntity {
    /**
     * Returns the {@link BattleNet} client instance used to retrieve the object.
     * @return The {@link BattleNet} client instance used to retrieve the object.
     */
    BattleNet getBattleNet();

    /**
     * Sets the {@link BattleNet} client instance used to retrieve the object.
     * @param battleNet The {@link BattleNet} client instance used to retrieve the object.
     */
    void setBattleNet(BattleNet battleNet);
}
