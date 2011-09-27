package org.blackpanther.aestokenizer.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author MACHIZAUD Andréa
 * @version 23/09/11
 */
public class InformationChannel extends JLabel {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(InformationChannel.class.getCanonicalName());

    private final Timer textTimer;

    public InformationChannel() {
        super();
        textTimer = new Timer(0, new TextEraser());
        textTimer.setInitialDelay(3000);
        textTimer.setRepeats(false);
    }

    @Override
    public void setText(final String text) {
        super.setText(text);
        if (textTimer != null) {
            if (textTimer.isRunning())
                textTimer.stop();
            textTimer.restart();
        }
    }

    private class TextEraser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            logger.info("Message '" + getText() + "' allowed time has elapsed, time to clear the channel");
            setText(null);
            textTimer.stop();
        }
    }
}
