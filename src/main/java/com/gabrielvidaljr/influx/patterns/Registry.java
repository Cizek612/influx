package com.gabrielvidaljr.influx.patterns;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

public interface Registry<K, V> {

    /**
     * Returns the registry core - a {@link Map}.
     *
     * @return The registry
     */

    Map<K, V> getRegistry();

    /**
     * Register an entry
     *
     * @param key - The key
     * @param value - The value
     */

    default void register(@NotNull final K key, @NotNull final V value) {
        this.getRegistry().put(key, value);
    }

    /**
     * Get an value from the key
     *
     * @param key - The key of the entry
     * @return Returns a #ofNullable {@link Optional}.
     */

    @NotNull
    default Optional<V> get(@NotNull final K key) {
        return Optional.ofNullable(this.getRegistry().get(key));
    }

    /**
     * Unregister an entry
     *
     * @param key - The key of the entry you want to unregister
     */

    default void unregister(@NotNull final K key) {
        this.getRegistry().remove(key);
    }

    /**
     * Iterate over the entries
     *
     * @param consumer - The {@link BiConsumer} / the action
     */

    default void iterate(@NotNull final BiConsumer<K, V> consumer) {

        for (final Map.Entry<K, V> entry : this.getRegistry().entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Check if an entry contains the specified key
     *
     * @param key The key of the entry
     * @return Returns a {@link Boolean}, telling you if the registry contains the specified key
     */

    default boolean containsKey(@NotNull final K key) {
        return this.getRegistry().containsKey(key);
    }

    /**
     * Check if an entry contains the specified value
     *
     * @param value The value of the entry
     * @return Returns a {@link Boolean}, telling you if the registry contains the specified value
     */

    default boolean containsValue(@NotNull final V value) {
        return this.getRegistry().containsValue(value);
    }

    /**
     * Get all the keys in the registry.
     *
     * @return A {@link Set} containing all the keys in the registry
     */

    default Set<K> keySet() {
        return this.getRegistry().keySet();
    }

    /**
     * Get all the entries in the registry
     *
     * @return A {@link Set} containing all the entries in the registry
     */

    default Set<Map.Entry<K, V>> entrySet() {
        return this.getRegistry().entrySet();
    }

    /**
     * Get all the values in the registry
     *
     * @return A {@link Collection} containing all the values in the registry
     */

    default Collection<V> values() {
        return this.getRegistry().values();
    }

}
