package org.blackpanther.util;

/**
 * @author MACHIZAUD Andréa
 * @version 23/09/11
 */
public class Strings {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Strings.class.getCanonicalName());

    public static int count(final String string, final String item) {
        final int index =  string.indexOf(item);
        return ( index < 0 )
                ? 0
                : 1 + count(string.substring(index + 1),item);
    }

    public static int count(final String string, final char item) {
        final int index =  string.indexOf(item);
        return ( index < 0 )
                ? 0
                : 1 + count(string.substring(index + 1),item);
    }
}
