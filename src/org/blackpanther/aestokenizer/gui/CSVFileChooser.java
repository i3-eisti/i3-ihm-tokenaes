package org.blackpanther.aestokenizer.gui;

import org.blackpanther.aestokenizer.csv.DataProcessor;
import org.blackpanther.aestokenizer.gui.handler.FileHandler;
import org.blackpanther.util.Tuple;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author MACHIZAUD Andréa
 * @version 19/09/11
 */
public final class CSVFileChooser {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(CSVFileChooser.class.getCanonicalName());

    public static Tuple<String, TableModel> pickCSVFile(final Component parent) throws IOException, DataProcessor.MalformedSiteIDException, DataProcessor.MalformedContentException {
        switch (FileHandler.open(parent, FileHandler.Type.CSV)) {
            case JFileChooser.APPROVE_OPTION:
                final File selectedFile = FileHandler.getSelectedFile();
                final DataProcessor csvProcessor = new DataProcessor(selectedFile);
                final String siteID = csvProcessor.getSiteID();
                final Map<String, Collection<String>> data = csvProcessor.getData();

                return new Tuple<String, TableModel>(
                        siteID,
                        new DefaultTableModel(
                                extractData(data),
                                extractColumns(data)
                        )
                );
            default:
                return new Tuple<String, TableModel>(AESPanel.NO_SITE_ID, new DefaultTableModel());
        }
    }

    private static Object[] extractColumns(Map<String, Collection<String>> data) {
        final int keysSize = data.keySet().size();
        return data.keySet().toArray(new Object[keysSize]);
    }

    private static Object[][] extractData(final Map<String, Collection<String>> data) {
        final int keysSize = data.keySet().size();
        final int longestCollectionSize = findMax(data);

        final java.util.List<String> keys = asList(data.keySet().toArray(new String[keysSize]));

        final Object[][] tableData = new Object[longestCollectionSize][keysSize];

        for (int keyIndex = keysSize; keyIndex-- > 0; ) {
            final String key = keys.get(keyIndex);
            final List<String> values = asList(data.get(key).toArray(new String[data.get(key).size()]));

            for (int index = values.size(); index-- > 0; )
                tableData[index][keyIndex] = values.get(index);
        }

        return tableData;
    }

    private static int findMax(Map<String, Collection<String>> data) {
        int max = Integer.MIN_VALUE;
        for (Collection<String> keySet : data.values()) {
            max = Math.max(max, keySet.size());
        }
        return max;
    }
}
