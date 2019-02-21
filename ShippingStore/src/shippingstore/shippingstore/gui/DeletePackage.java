package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * @author Zac Golla and Kentessa Fanfair
 *
 * DeletePackage is a subclass of JFrame class and deletes package from the  packages list based on the
 * tracking number provided by the user.
 */
public class DeletePackage extends JFrame implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String trackingNumber;
    private ShippingStore ss;
    private boolean isDeleted;

    private JTextField textFieldTrackingNumber;
    private JButton btnDelete;
    private JPanel panelButtons;
    private JButton btnShowPackages;


    /**
     * Default constructor of the DeletePackage class. Generates and initializes all the component used for the class.
     * It also sets the default settings used for the frame. Also gives user option to show all current packages in
     * database.
     */
    DeletePackage() {
        // default initial settings for the frame
        setSize(600, 75);
        setTitle("Delete a Package");
        setLayout(new FormLayout());

        // initializing shipping store object;
        ss = new ShippingStore().readDatabase();

        panelButtons = new JPanel();

        //tracking number field
        add(new JLabel("  Tracking Number"));
        textFieldTrackingNumber = new JTextField(10);

        // delete button
        btnDelete = new JButton("Delete Package");
        btnDelete.addActionListener(this);

        // show packages button
        btnShowPackages = new JButton("Show All Packages");
        btnShowPackages.addActionListener(this);

        // adding components to delete panel
        panelButtons.add(textFieldTrackingNumber);
        panelButtons.add(btnDelete);
        panelButtons.add(btnShowPackages);

        // adding panel to frame
        add(panelButtons);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    /**
     * Validates entered tracking number. If validated, the tracking number is used to search for a package matching it
     * and deletes package from database from found. If packing matching the tracking number provided does not exist
     * the user is prompted. It also responds to when the user clicks the show packages option. When click the table of
     * all packages is presented.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnDelete)) {
            if (textFieldTrackingNumber.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(new JFrame(), "Please enter a tracking number.");
            }
            else {
                trackingNumber = textFieldTrackingNumber.getText();
                isDeleted = ss.deletePackage(trackingNumber);

                if (isDeleted) {
                    LOGGER.info("Package with tracking number: '" + trackingNumber + "' was deleted from the packages list");
                    JOptionPane.showMessageDialog(new JFrame(), "Package was deleted");
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Package with the given tracking number (#" + trackingNumber + ") does not exist.");
                try {
                    ss.writeDatabase();
                    LOGGER.info("Saving changes to ShippingStore db");
                } catch (Exception e1) {
                    LOGGER.severe("A error occurred while attempting to save the changes made to ShippingStore db");
                }
            }
        }

        if (e.getSource().equals(btnShowPackages)) {
            ShowPackages sp = new ShowPackages();
            sp.setLocation(this.getX(), this.getY() + 76);
            LOGGER.info("User selects: Show All Packages option");
        }
    }
}
