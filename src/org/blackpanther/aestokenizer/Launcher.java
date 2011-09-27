package org.blackpanther.aestokenizer;

import org.blackpanther.aestokenizer.gui.AESPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author MACHIZAUD Andréa
 * @version 19/09/11
 */
public final class Launcher {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Launcher.class.getCanonicalName());

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                final LookAndFeel defaultLAF = UIManager.getLookAndFeel();
//                try {
//                    UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
//                } catch (Exception e) {
//                    logger.info("Unsupported LAF : " + NimbusLookAndFeel.class.getCanonicalName());
//                }

                final JFrame frameMain = new JFrame("AES Tokenizer");
                final JPanel panelAES = new AESPanel();

                frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frameMain.setContentPane(panelAES);

                frameMain.pack();

                frameMain.setLocationRelativeTo(null);
                frameMain.setMaximumSize(new Dimension(800, 600));

                frameMain.setVisible(true);
            }
        });
    }

}
