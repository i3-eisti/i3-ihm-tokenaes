package org.blackpanther.util;

/**
 * @author MACHIZAUD Andréa
 * @version 23/09/11
 */
public final class Tuple<T,V> {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Tuple.class.getCanonicalName());

    private final T first;
    private final V second;

    public Tuple(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public final T getFirst() {
        return first;
    }

    public final V getSecond() {
        return second;
    }

    @Override
    public final boolean equals(Object o) {
        return o.getClass().equals(getClass())
                && o.hashCode() == hashCode();
    }

    @Override
    public final int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }
}
