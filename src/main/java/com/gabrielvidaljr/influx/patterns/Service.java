package com.gabrielvidaljr.influx.patterns;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Service<S> {

    /**
     * Returns the service, a {@link Collection}.
     *
     * @return The service
     */

    Collection<S> getService();

    /**
     * Adds a entry to the service.
     *
     * @param s The entry added.
     */

    default void add(final S s) {
        this.getService().add(s);
    }

    /**
     * Removes an entry from the service.
     *
     * @param s The entry removed.
     */

    default void remove(final S s) {
        this.getService().remove(s);
    }

    /**
     * Checks if a entry is contained within this service instance.
     *
     * @param s The entry checked.
     *
     * @return A boolean telling us if this is within the service.
     */

    default boolean contains(final S s) {
        return this.getService().contains(s);
    }

    /**
     * Clears every entry from the service instance.
     */

    default void clear() {
        this.getService().clear();
    }

    /**
     * Adds all entries from a {@link Collection} into the service.
     *
     * @param collection Entries added.
     */

    default void addAll(final Collection<S> collection) {
        this.getService().addAll(collection);
    }

    /**
     * Iterate over the entries
     *
     * @param consumer - The {@link Consumer} / the action
     */

    default void iterate(final Consumer<S> consumer) {
        for (final S s : this.getService()) {
            consumer.accept(s);
        }
    }

    /**
     * Gets an specific entry based off it's index.
     *
     * @param index Index of the entry. Starts from 0.
     *
     * @return Entry from the specified index.
     */

    default S get(final int index) {
        return this.asList().get(index);
    }

    /**
     * Puts all the registered entries into a list.
     *
     * @return A list containing all the entries.
     */

    default List<S> asList() {
        return (List<S>) this.getService();
    }

    /**
     * Puts all the registered entries into a queue.
     *
     * @return A queue containing all the entries.
     */

    default Queue<S> asQueue() {
        return (Queue<S>) this.getService();
    }
}
