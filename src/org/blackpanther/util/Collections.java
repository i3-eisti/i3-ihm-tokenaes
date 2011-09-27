package org.blackpanther.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MACHIZAUD Andréa
 * @version 9/22/11
 */
public class Collections {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Collections.class.getCanonicalName());

    public static <E> String mkString(final Collection<E> collection){
        final List<E> iterator = new ArrayList<E>(collection);
        final StringBuilder writer = new StringBuilder();

        writer.append(collection.getClass().getSimpleName());
        writer.append('(');
        for (int index = iterator.size(); index-- > 0; ) {
            writer.append(iterator.get(index).toString());
            if(index != 0)
                writer.append(',');
        }

        return writer.toString();
    }
}
