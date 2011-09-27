package org.blackpanther.aestokenizer.gui.handler;

import org.blackpanther.aestokenizer.crypto.Cryptography;

import javax.swing.table.TableModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.blackpanther.aestokenizer.crypto.Cryptography.crypt;
import static org.blackpanther.aestokenizer.crypto.Cryptography.cryptedAsHexString;

/**
 * @author MACHIZAUD Andréa
 * @version 9/22/11
 */
public final class SQLExport {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SQLExport.class.getCanonicalName());

    public static final String CREATE_TABLE =
            "CREATE TABLE %s { DATA VARCHAR( " + 256 + " ) }%n";

    public static final String INSERT_INTO =
            "INSERT INTO %s VALUES ( %s ) %n";

    public static String exportSQL(final Map<String, Collection<Object>> data) {
        logger.info("Header : " + data.keySet());
        logger.info("Data : " + data.values());

        StringBuilder sb = new StringBuilder();

        for (String name : data.keySet()) {
            sb.append(String.format(CREATE_TABLE, name.toUpperCase()));
            logger.info("Create Table : \n" + sb.toString());
        }

        sb.append('\n');

        logger.info("Exploded map : " + data.toString());
        for (Map.Entry<String, Collection<Object>> entry : data.entrySet())
            for (Object value : entry.getValue()) {
                if (value != null) {
                    final byte[] cryptedData = crypt(value);
                    sb.append(String.format(
                            INSERT_INTO,
                            entry.getKey().toUpperCase(),
                            cryptedData == Cryptography.NO_CRYPT
                                    ? "NULL"
                                    : cryptedAsHexString(cryptedData)
                    ));
                }
            }

        logger.info(sb.toString());
        return sb.toString();
    }

    public static Map<String, Collection<Object>> extractData(TableModel dataModel) {
        final Map<String, Collection<Object>> data =
                new HashMap<String, Collection<Object>>(dataModel.getColumnCount());
        for (int i = dataModel.getRowCount(); i-- > 0; )
            for (int j = dataModel.getColumnCount(); j-- > 0; ) {
                if (!data.containsKey(dataModel.getColumnName(j))) {
                    data.put(dataModel.getColumnName(j), new LinkedList<Object>());
                }
                data.get(dataModel.getColumnName(j)).add(dataModel.getValueAt(i, j));
            }
        return data;
    }
}
