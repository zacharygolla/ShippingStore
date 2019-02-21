package shippingstore.shippingstore.gui;


import shippingstore.Package;
import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;


/**
 * @author Zac Golla and Kentessa Fanfair
 *
 * SearchPackage class is subclass of JFrame class. It creates the frame of the GUI that
 * allows the user uses to search for a package in the database. It also logs most of the user interactions with the
 * class. It also logs key user interactions with the class to a file.
 */
public class SearchPackage extends JFrame implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private ShippingStore ss;
    private Package packageFound;
    String trackingNumber;


    private JPanel panelSearch;
    private JTextField textFieldSearch;
    private JButton btnSearch;
    private JPanel panelSearchResults;
    private JButton btnClear;
    private JPanel panelClear;
    private JLabel labelSearchResults;
    private JButton showAllPackages;


    /**
     * Default constructor for the SearchPackage class. Generates and initializes all the component used for the class.
     * It also sets the default settings used for the frame.
     */
    SearchPackage() {
        setSize(700, 150);
        setTitle("Search Packages");
        setLayout(new FormLayout());

        ss = new ShippingStore().readDatabase();

        textFieldSearch = new JTextField(10);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        panelSearch = new JPanel();
        panelSearch.add(textFieldSearch);
        panelSearch.add(btnSearch);

        add(new JLabel("  Find a Package"));
        add(panelSearch);

        add(new JLabel(""));
        panelSearchResults = new JPanel();
        add(panelSearchResults);

        // clear btn
        btnClear = new JButton("Clear Results");
        btnClear.addActionListener(this);

        panelClear = new JPanel();
        panelClear.add(btnClear);

        add(new JLabel(""));
        add(panelClear);

        // show all packages btn
        showAllPackages = new JButton("Show All Packages");
        showAllPackages.addActionListener(this);
        panelSearch.add(showAllPackages);

        // search results
        labelSearchResults = new JLabel();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    /**
     * Shows package of tracking number entered (if the package exist). It the package does not exist, it returns a
     * message stating that to the user. Catches exception if tracking number DNE. When the clear button is selected
     * the search results are cleared. If the show all packages button is click the listener shows the table of all
     * packages (useful for quick reference to get a tracking number to look up and if table does not show additional
     * details, then the search results will contain all information about the package).
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnSearch)) {
            if (textFieldSearch.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(new JFrame(), "Please enter a tracking number.");
            }
            else {
                trackingNumber = textFieldSearch.getText().trim();

                try {
                    LOGGER.info("Searching for a package.");
                    packageFound = ss.findPackage(trackingNumber);

                    if (packageFound.equals(null)) {
                    } else {
                        packageFound.getPtn();
                        labelSearchResults.setText("Search results: " + packageFound.getFormattedText());
                        panelSearchResults.add(labelSearchResults);
                        repaint();
                        setVisible(true);
                    }
                } catch (NullPointerException npe) {
                    labelSearchResults.setText("Search results: Package not found." );
                    panelSearchResults.add(labelSearchResults);
                    repaint();
                    setVisible(true);
                    LOGGER.warning("An attempt was made to access a none-existing package in the ShippingStore db.");
                }
            }
        }

        if (e.getSource().equals(btnClear)) {
            labelSearchResults.setText("");
            LOGGER.info("User selects: Clear search results option.");
        }

        if (e.getSource().equals(showAllPackages)) {
            ShowPackages sp = new ShowPackages();
            sp.setLocation(this.getX(), this.getY()+150);
            LOGGER.info("User selects: Show all packages option.");

        }
    }
}
