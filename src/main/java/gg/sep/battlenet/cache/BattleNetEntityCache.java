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

package gg.sep.battlenet.cache;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Callable;

import gg.sep.battlenet.model.BattleNetEntity;

/**
 * The cache interface is used by the BattleNet API to attempt to retrieve cached copies
 * of API data rather than calling the API each time. This allows the usage of various types
 * of caching systems such as Redis, Memcache/Memcached, DynamoDB, MongoDB, SQL, etc.
 *
 * <p>At its core, the cache operations as a key/value store with some custom configuration
 * around entity-specific TTLs, retrieval of non-cached or stale/expired data, and so forth.
 *
 * <p>A prefix can be customized so as to avoid collisions with preexisting or external data if
 * a shared cache is used.
 */
public interface BattleNetEntityCache {

    /**
     * Returns the prefix which is prepended to the entity's key before writing to the cache.
     * @return The prefix which is prepended to the entity's key before writing to the cache.
     */
    String getKeyPrefix();

    /**
     * Sets the TTL for entities of a {@link BattleNetEntity} subclass, which will be used
     * for all future cache inserts.
     *
     * <p>If a previous TTL existed for this type, the previous value will be replaced and returned.
     *
     * @param entityClass The types of entities which should use the specified {@code ttl}.
     * @param ttl The duration that future entities of {@code entityClass} will
     *            have set for the TTL.
     * @return The previous value associated with the {@code entityClass}, or {@code null} if there
     *         was no previous association. A {@code null} can also indicate that
     *         {@code null} was previously associated with the {@code entityClass}.
     */
    Duration setEntityTTL(Class<? extends BattleNetEntity> entityClass, Duration ttl);

    /**
     * Retrieves the TTL associated with the specified entity class.
     *
     * <p>If a specific TTL has not be sent for the entity's type,
     * the default TTL will be returned.
     *
     * @param entityClass The type of entity for which to retrieve the TTL.
     * @return The TTL associated with the {@code entityClass}, or the default
     *         TTL if none has been explicitly set for the type.
     */
    Duration getEntityTTL(Class<? extends BattleNetEntity> entityClass);

    /**
     * Gets the remaining TTL in the cache for the specified {@code key}.
     *
     * <p>If the key has expired <i>or does not exist</i>, this method will return
     * {@link Duration#ZERO}. Some cache implementations keep expired data in
     * the cache even after it has expired, which results in negative TTLs be returned
     * from the cache. In the case of negative TTLs, this method should still return
     * {@link Duration#ZERO}. If your usage requires to know the exact TTL, this should be
     * implemented as a separate class-specific method.
     *
     * @param key The key for which to get the TTL.
     * @return The duration of the remaining TTL for the specified {@code key}, or {@link Duration#ZERO}
     *         if the key does not exist or it has expired.
     */
    Duration getTTL(String key);

    /**
     * Returns a cached {@link BattleNetEntity} (if available) for the specified {@code key},
     * or {@code null} if there is no non-expired cache entry for this key.
     *
     * <p>Depending on the cache implementation, a return value of {@code null} could mean
     * that {@code null} was explicitly associated with the {@code key} previously.
     *
     * @param key The key with which the cached item is stored.
     * @param entityType The class type of the entity item to return. The implementing class
     *                   needs to handle the serialization/deserialization of the entity,
     *                   and is generally recommended to be stored in JSON format so as to be
     *                   compatible with {@link gg.sep.battlenet.model.JsonSerializable}.
     * @param <T> The type of {@code entityType}.
     * @return The cached non-expired {@link BattleNetEntity} (if available) for the
     *         specified {@code key} or {@code null} if there is no non-expired
     *         cache entry for this key.
     */
    <T extends BattleNetEntity> T get(String key, Class<T> entityType);

    /**
     * Attempts to retrieve a cached {@link BattleNetEntity} (if available) for the specified
     * {@code key}, with the ability to retrieve a fresh entity if one is not available.
     *
     * <p>If the cached entity is not available or has expired, {@code retrieve}
     * will be called in order to get a fresh version of the {@link BattleNetEntity}. This value
     * will then be cached via {@link #set(String, BattleNetEntity)} and returned.
     *
     * @param key The key with which the cached item is stored.
     * @param entityType The class type of the entity item to return. The implementing class
     *                   needs to handle the serialization/deserialization of the entity,
     *                   and is generally recommended to be stored in JSON format so as to be
     *                   compatible with {@link gg.sep.battlenet.model.JsonSerializable}.
     * @param retrieve Callable which will be executed if the cached item is not available
     *                 or has expired in order to produce a fresh version of the entity.
     *                 The retrieved value will be cached via {@link #set(String, BattleNetEntity)}
     *                 and returned.
     * @param <T> The type of {@code entityType}.
     * @return The cached non-expired {@link BattleNetEntity} (if available) for the
     *         specified {@code key}, or the return value of {@code retrieve} if none
     *         is available from the cache.
     */
    <T extends BattleNetEntity> T getOrElse(String key, Class<T> entityType, Callable<T> retrieve);

    /**
     * Inserts or updates an item in the cache.
     *
     * <p>The TTL used for the cache entry will be the value retrieved from {@link #getEntityTTL(Class)}.
     *
     * <p>If the insert/update operation was successful, {@code true} will be returned,
     * otherwise a {@code false} will be returned.
     *
     * @param key The key with which to store the item in the cache.
     * @param value The entity to store in the cache at the key.
     * @return {@code true} if the insert/update operation was successful, otherwise {@code false}.
     */
    boolean set(String key, BattleNetEntity value);

    /**
     * Deletes the specified keys from the cache.
     *
     * <p>If the delete operation was successful, {@code true} will be returned,
     * otherwise a {@code false} will be returned.
     *
     * @param keys The keys to delete from the cache.
     * @return {@code true} if the delete operation was successful, otherwise {@code false}.
     */
    boolean del(String... keys);

    /**
     * Updates the TTL in the cache for the specified keys.
     *
     * <p>If the update TTL operation was successful, {@code true} will be returned,
     * otherwise a {@code false} will be returned.
     *
     * @param ttl The duration to set for the specified {@code keys}.
     * @param keys The keys for which to update the TTL.
     * @return {@code true} if the update TTL operation was successful, otherwise {@code false}.
     */
    boolean updateTTL(Duration ttl, String... keys);

    /**
     * Resets the TTLs for the given keys and their entity types.
     *
     * <p>Given a mapping of {@code Map<K, V>}, the key {@code <K>} will have its TTL
     * reset to the value returned where {@code V} is the input to {@link #getEntityTTL(Class)}.
     *
     * <p>If the update TTL operation was successful, {@code true} will be returned,
     * otherwise a {@code false} will be returned.
     *
     * @param keysAndTypes Mapping of keys to their entity types, which will have their TTLs reset to their defaults.
     * @return {@code true} if the update TTL operation was successful, otherwise {@code false}.
     */
    boolean resetTTL(Map<String, Class<? extends BattleNetEntity>> keysAndTypes);

    /**
     * Deletes or expires all keys in the cache which are prefixed with the cache prefix {@link #getKeyPrefix()}.
     *
     * <p>If the operation was successful, {@code true} will be returned,
     * otherwise a {@code false} will be returned.
     *
     * @return {@code true} if the operation was successful, otherwise {@code false}.
     */
    boolean flushCache();
}
