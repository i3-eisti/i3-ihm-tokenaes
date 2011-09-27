package org.blackpanther.aestokenizer.gui.handler;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * @author MACHIZAUD Andréa
 * @version 9/22/11
 */
public final class FileHandler {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FileHandler.class.getCanonicalName());

    public enum Type {
        CSV
    }

    private static final JFileChooser chooserReference = new JFileChooser(".");

    public static final FileFilter CSV_FILE_FILTER = new FileNameExtensionFilter("CSV Files","csv");

    public static int open(final Component parent, final Type filetype) {
        switch (filetype) {
            case CSV:
                chooserReference.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooserReference.setMultiSelectionEnabled(false);
                chooserReference.setFileFilter(CSV_FILE_FILTER);
                break;
        }
        return chooserReference.showOpenDialog(parent);
    }

    public static File getSelectedFile() {
        return chooserReference.getSelectedFile();
    }

    public static File[] getSelectedFiles() {
        return chooserReference.getSelectedFiles();
    }
}
