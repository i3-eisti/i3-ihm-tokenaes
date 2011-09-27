package org.blackpanther.util;

import javax.swing.*;
import java.awt.*;

/**
 * @author MACHIZAUD Andréa
 * @version 23/09/11
 */
public class Swings {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Swings.class.getCanonicalName());

    public static Component verticalScrollable(final Component comp) {
        return new JScrollPane(comp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public static Component scrollable(final Component comp) {
        return new JScrollPane(comp);
    }

    public static JPanel wrap(
            final JComponent comp) {
        final JPanel wrapper = new JPanel(new FlowLayout());
        wrapper.add(comp);
        return wrapper;
    }

    public static JPanel wrap(
            final JComponent comp,
            int margin) {
        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(comp, BorderLayout.CENTER);
        wrapper.setBorder(
                BorderFactory.createEmptyBorder(margin, margin, margin, margin));
        return wrapper;
    }

    public static JPanel wrap(
            final JComponent comp,
            int top, int left, int bottom, int right) {
        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(comp, BorderLayout.CENTER);
        wrapper.setBorder(
                BorderFactory.createEmptyBorder(top, left, bottom, right));
        return wrapper;
    }

    public static JPanel wrap(
            final JComponent comp,
            final Insets insets) {
        return wrap(comp, insets.top, insets.left, insets.bottom, insets.right);
    }

}
