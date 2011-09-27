package org.blackpanther.aestokenizer.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.blackpanther.util.Swings.wrap;

/**
 * @author MACHIZAUD Andréa
 * @version 23/09/11
 */
public final class PreviewWindowFactory extends JDialog {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(PreviewWindowFactory.class.getCanonicalName());

    private PreviewWindowFactory() {
        setTitle("Preview Frame for SQL generated instructions");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);
    }

    private static class PreviewWindowHolder {
        private static final PreviewWindowFactory instance =
                new PreviewWindowFactory();
    }

    public static PreviewWindowFactory getInstance() {
        return PreviewWindowHolder.instance;
    }   

    public static void preview(final Component parent, final String sqlInstructions) {
        final PreviewWindowFactory previewer = getInstance();
        previewer.setContentPane(new JScrollPane(new PreviewPane(sqlInstructions)));
        previewer.pack();
        previewer.setLocationRelativeTo(parent);
        previewer.setVisible(true);
    }  

    static final class PreviewPane extends JPanel {

        private final JTextArea displayableContent;

        public PreviewPane(final String content) {

            //JTextArea
            final String[] rows = content.split("\n");
            final int rowSize = org.blackpanther.util.Strings.count(content, "\n");
            displayableContent = new JTextArea(
                    rows.length + 2,
                    30
            );
            
            displayableContent.setFont(new Font("SansSerif", Font.ITALIC, 12));
            displayableContent.setEditable(false);
            displayableContent.setText(content);
            
            logger.info("Splitted row : " + java.util.Arrays.toString(rows));

            //JButton - Copy to clipboard
            final JButton buttonCopy = new JButton("Copy to clipboard");
            buttonCopy.addActionListener(new ClipboardHandler());
            
            final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(buttonCopy);
            
            setLayout(new BorderLayout());
            
            add(wrap(displayableContent, 8), BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        final class ClipboardHandler implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection sqlContent = new StringSelection(displayableContent.getText());
                Clipboard transferer = Toolkit.getDefaultToolkit().getSystemClipboard();
                transferer.setContents(sqlContent, null);
                JOptionPane.showMessageDialog(
                        getInstance(),
                        "SQL code copied !",
                        "Notification",
                        JOptionPane.INFORMATION_MESSAGE
                );
                getInstance().setVisible(false);
            }

        }

    }

}
