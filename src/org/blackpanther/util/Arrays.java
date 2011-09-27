package org.blackpanther.util;

import java.lang.reflect.Array;

/**
 * @author MACHIZAUD Andréa
 * @version 23/09/11
 */
public class Arrays {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Arrays.class.getCanonicalName());

    @SuppressWarnings("unchecked")
    public static <T,V> V[] map(final T[] source, final MappingFunction<T,V> function) {
        if( source.length > 0 ) {
            final V[] result = (V[]) Array.newInstance(source[0].getClass(), source.length);
            for (int index = source.length - 1; index >= 0; index--) {
                result[index] = function.apply(source[index]);
            }
            return result;
        } else {
            return (V[]) new Object[0];
        }
    }

    public static int max(final Integer[] items){
        int cursor = items.length;
        int max = Integer.MIN_VALUE;
        while(cursor-- > 0){
            max = Math.max(max,items[cursor]);
        }
        return max;
    }

    public interface MappingFunction<T,V> {
        public V apply(final T item);
    }

}
