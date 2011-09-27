package org.blackpanther.aestokenizer.gui;

import org.blackpanther.aestokenizer.csv.DataProcessor;
import org.blackpanther.aestokenizer.gui.handler.SQLExport;
import org.blackpanther.util.Tuple;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import static java.awt.Color.BLACK;
import static org.blackpanther.util.Swings.*;

/**
 * @author MACHIZAUD Andréa
 * @version 19/09/11
 */
public class AESPanel extends JPanel {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AESPanel.class.getCanonicalName());

    public static final String NO_SITE_ID = "Site ID unknown";
    public static final String SITE_PREFIX = "Site ID : ";

    public static final Dimension CENTER_PANEL_DIMENSION = new Dimension(800, 500);

    public static final JPanel NO_DATA_AVAILABLE = buildNoDataAvailablePanel();

    private final JPanel outerWrapper;

    private boolean tableIsVisible;
    private final JButton buttonCSV;
    private final JButton buttonReset;
    private final JButton buttonAddRow;
    private final JButton buttonGenerateSQL;

    private static JPanel buildNoDataAvailablePanel() {
        final JPanel panelNoDataAvailable = new JPanel();

        panelNoDataAvailable.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 5, 15));
        panelNoDataAvailable.setPreferredSize(
                CENTER_PANEL_DIMENSION);

        panelNoDataAvailable.add(
                new JLabel("No data available"));

        return panelNoDataAvailable;
    }

    /**
     * Site field, formatted to control input
     */
    private final JLabel lblSiteID;
    private final JPanel panelSiteID;
    /**
     * Generated fields, load them when a valid csv file is picked
     */
    private JTable dataTable;

    private final JLabel informationChannel;

    public AESPanel() {

        //no site
        lblSiteID = new JLabel(NO_SITE_ID);
        //lblSiteID = new JLabel("Site ID : 0003245");

        panelSiteID = new JPanel(new BorderLayout());
        panelSiteID.setBorder(
                BorderFactory.createLineBorder(BLACK, 2));
        panelSiteID.add(wrap(
                lblSiteID,
                8, 15, 8, 15
        ));

        final JPanel panelButtons = new JPanel(new GridLayout(4, 1));
        panelButtons.setBorder(
                BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        final Dimension buttonDimension = new Dimension(160, 28);

        buttonCSV = new JButton(
                "Pick a CSV",
                loadIcon("org/blackpanther/aestokenizer/images/csv.png"));
        buttonGenerateSQL = new JButton(
                "Generate SQL",
                loadIcon("org/blackpanther/aestokenizer/images/sql.png"));
        buttonAddRow = new JButton(
                "Add row",
                loadIcon("org/blackpanther/aestokenizer/images/row.png")
        );
        buttonReset = new JButton(
                "Reset values",
                loadIcon("org/blackpanther/aestokenizer/images/reset.png")
        );

        buttonCSV.setPreferredSize(buttonDimension);
        buttonGenerateSQL.setPreferredSize(buttonDimension);
        buttonAddRow.setPreferredSize(buttonDimension);
        buttonReset.setPreferredSize(buttonDimension);


        buttonGenerateSQL.setEnabled(false);
        buttonAddRow.setEnabled(false);
        buttonReset.setEnabled(false);

        //no table by default
        dataTable = new JTable();
        dataTable.setPreferredSize(CENTER_PANEL_DIMENSION);
        tableIsVisible = false;

        informationChannel = new InformationChannel();
        informationChannel.setPreferredSize(new Dimension(400, 20));
        informationChannel.setFont(new Font("SansSerif", Font.ITALIC, 10));


        final Component parent = this;
        buttonCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final Tuple<String, TableModel> extractedData = CSVFileChooser.pickCSVFile(parent);

                    lblSiteID.setText(SITE_PREFIX + extractedData.getFirst());
                    dataTable.setModel(extractedData.getSecond());

                    if (!tableIsVisible) {
                        final JPanel wrapperTable = new JPanel(new BorderLayout());
                        wrapperTable.add(verticalScrollable(dataTable.getTableHeader()), BorderLayout.NORTH);
                        wrapperTable.add(scrollable(dataTable), BorderLayout.CENTER);

                        outerWrapper.remove(NO_DATA_AVAILABLE);
                        outerWrapper.add(wrapperTable, BorderLayout.CENTER);
                        tableIsVisible = true;
                    }

                    buttonGenerateSQL.setEnabled(true);
                    buttonAddRow.setEnabled(true);
                    buttonReset.setEnabled(true);

                    informationChannel.setText("Table data updated");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            parent,
                            "Unexpected error while processing CSV File, please check your file before submitting it\n" +
                                    "Table value will now be reset",
                            "CSV processing error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    resetFields();
                } catch (DataProcessor.MalformedSiteIDException ex) {
                    JOptionPane.showMessageDialog(
                            parent,
                            ex.getMessage() + "\n" +
                                    "Table value will now be reset",
                            "CSV processing error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    resetFields();
                } catch (DataProcessor.MalformedContentException ex) {
                    JOptionPane.showMessageDialog(
                            parent,
                            ex.getMessage() + "\n" +
                                    "Table value will now be reset",
                            "CSV processing error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    resetFields();
                }
            }
        });

        buttonGenerateSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Map<String, Collection<Object>> data =
                        SQLExport.extractData(dataTable.getModel());

                if (!data.isEmpty()) {
                    final String sqlInstructions =
                            SQLExport.exportSQL(data);

                    PreviewWindowFactory.preview(parent, sqlInstructions);
                } else {
                    JOptionPane.showMessageDialog(
                            parent,
                            "No data to export to SQL !",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        });

        buttonReset.addActionListener(EventHandler.create(
                ActionListener.class,
                this,
                "resetFields"
        ));

        buttonAddRow.addActionListener(EventHandler.create(
                ActionListener.class,
                this,
                "addRow"
        ));

        panelButtons.add(wrap(
                buttonCSV
        ));

        panelButtons.add(wrap(
                buttonGenerateSQL
        ));

        panelButtons.add(wrap(
                buttonAddRow
        ));

        panelButtons.add(wrap(
                buttonReset
        ));

        setLayout(new BorderLayout());

        outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.add(panelSiteID, BorderLayout.NORTH);
        outerWrapper.add(NO_DATA_AVAILABLE, BorderLayout.CENTER);
        outerWrapper.add(informationChannel, BorderLayout.SOUTH);

        add(outerWrapper, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.EAST);
    }

    public void resetFields() {
        logger.info("Resetting fields... (table visibility : " + tableIsVisible + ")");
        lblSiteID.setText(NO_SITE_ID);
        if (tableIsVisible) {
            outerWrapper.removeAll();
            outerWrapper.add(panelSiteID, BorderLayout.NORTH);
            outerWrapper.add(NO_DATA_AVAILABLE, BorderLayout.CENTER);
            outerWrapper.add(informationChannel, BorderLayout.SOUTH);
            outerWrapper.invalidate();
            outerWrapper.repaint();
            tableIsVisible = false;
        }

        buttonGenerateSQL.setEnabled(false);
        buttonAddRow.setEnabled(false);
        buttonReset.setEnabled(false);

        informationChannel.setText("Reset done");
    }

    public void addRow() {
        final DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.addRow(new Object[model.getColumnCount()]);
    }

    private Icon loadIcon(final String resourcePath) {
        final URL resource = getClass().getClassLoader().getResource(resourcePath);
        logger.info("loaded resource : " + resource);
        return new ImageIcon(resource);
    }
}
