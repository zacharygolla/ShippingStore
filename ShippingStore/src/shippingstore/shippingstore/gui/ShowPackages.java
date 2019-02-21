package shippingstore.shippingstore.gui;

import shippingstore.*;
import shippingstore.Box;
import shippingstore.Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * @author Zac Golla and Kentessa Fanfair
 *
 * showPackages is a subclass of the JFrame class. It shows all current and undelivered packages in the shipping store
 * database.
 *
 * */
public class ShowPackages extends JFrame{
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    ShippingStore ss;

    JTable packagesTable;
    String[] columnNames = {"Tracking #", "Package Type", "Specification", "Mailing Class", "Other Details"};
    ArrayList<Package> packages;



    /**
     * Default constructor for the showTransactions class. Generated the table and necessary components for the frame
     * Places data from the packages list info into table and displays the table to the user.
     */
    ShowPackages() {
        setSize(1075, 500);
        setTitle("Package List");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ss = new ShippingStore().readDatabase();
        packages = (ArrayList) ss.getPackages();

        packagesTable = new JTable();
        packagesTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
        packagesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        packagesTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        packagesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        packagesTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        packagesTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        packagesTable.getColumnModel().getColumn(4).setPreferredWidth(350);

        addRowToJTable();

        add(new JScrollPane(packagesTable));
        setVisible(true);
    }


    /**
     * Reads the data of each Package into the table in the specified format.
     */
    public void addRowToJTable() {
        LOGGER.info("Generating packages table");

        DefaultTableModel tableModel = (DefaultTableModel) packagesTable.getModel();
        Object rowData[] = new Object[5];

            for (Package p: packages) {
                rowData[0] = p.getPtn();
                rowData[1] = splitFormattedText(p.getFormattedText(), 0);
                rowData[2] = p.getSpecification();
                rowData[3] = p.getMailingClass();
                rowData[4] = getOtherPackageInfo(p);

                tableModel.addRow(rowData);
            }
    }


    /**
     * Separates each point of the string at a space to several to separate sub-strings.
     * @param line
     * @param index
     * @return
     */
    public String splitFormattedText(String line, int index) {
        String temp[] = line.trim().split(" ");

        return temp[index];
    }


    /**
     * Adds additional information needed for each package based on package type.
     * @param p
     * @return
     */
    public String getOtherPackageInfo(Package p) {
        if (p instanceof Crate) {
            return "Load Weight: " + ((Crate) p).getLoadWeight() + ", Content: " + ((Crate) p).getContent();

        }else if (p instanceof Box) {
            return "Dimension: " + ((Box) p).getDimension() +  ", Volume: " + ((Box) p).getVolume();

        } else if (p instanceof Drum) {
            return "Material: " + ((Drum) p).getMaterial() + ", Diameter: " + ((Drum) p).getDiameter();

        } else if (p instanceof Envelope) {
            return "Height: "+ ((Envelope) p).getHeight() + ", Width: " + ((Envelope) p).getWidth();

        } else {
            return "";
        }
    }
}

