package shippingstore.shippingstore.gui;

import shippingstore.Package;
import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.*;


/**
 * @author Zac Golla and Kentessa Fanfair
 *
 * AddPackage is a subclass of JFrame class. It creates a new frame when the user selects the 'Add a New Package menu
 * option and, if valid data is provided, it adds a new package to shipping store database. It also allows the user to
 * select an option to view the list of packages held in the database. It also logs key user interactions with the class
 * to a file.
 */
public class AddPackage extends JFrame implements ItemListener, ActionListener {
    private static final String[] packageTypeOptions = {"Box", "Crate", "Drum", "Envelope"};
    private static final String[] specificationOptions = {"Fragile", "Books", "Catalogs", "Do-Not-Bend", "N/A"};
    private static final String[] mailingClassOptions = {"First-Class", "Priority", "Retail", "Ground", "Metro"};
    private static final String errorMessage = "Invalid entry: (Empty fields not allowed) Please check the form and try again.";
    private static final String successMessage = "Success: New package added to the database";
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    ArrayList<Package> packages;

    private JComboBox comboBoxPackageType;
    private JTextField textFieldTrackingNumber;
    private JComboBox comboBoxSpecification;
    private JComboBox comboBoxMailingClass;
    private JPanel panelOtherDetails;
    private JPanel panelSubmit;
    private JTextField textFieldOtherDetails1;
    private JTextField textFieldOtherDetails2;
    private JLabel labelOtherDetails1;
    private JLabel labelOtherDetails2;
    private JButton btnSubmit;
    private JButton btnReset;
    private JButton btnShowPackages;

    private ShippingStore ss;
    private String trackingNumber;
    private String type;
    private String specification;
    private String mailingClass;
    private String otherdetails1;
    private String otherdetails2;

    /**
     * Default constructor for AddPackage class. Generates a random new Tracking Number for user and creates
     * drop boxes for user to choose different Package type, Specification, and Mailing Class. Based on package
     * type chosen, constructor generates new input locations for type-specific information.
     */
    AddPackage()  {
        setSize(650, 200);
        setTitle("Add a New Package");
        setLayout(new FormLayout());

        // shipping store object;
        ss = new ShippingStore().readDatabase();
        packages = (ArrayList<Package>) ss.getPackages();

        // tracking number field
        add(new JLabel("Tracking Number"));
        textFieldTrackingNumber = new JTextField(10);
        textFieldTrackingNumber.setText(randomlyGeneratedTrackingNumber());
        textFieldTrackingNumber.addActionListener(this);
        add(textFieldTrackingNumber);

        // package type field
        add(new JLabel("Package Type"));
        comboBoxPackageType = new JComboBox(packageTypeOptions);
        comboBoxPackageType.setSelectedIndex(-1);
        comboBoxPackageType.addItemListener(this);
        add(comboBoxPackageType);

        // specification field
        add(new JLabel("Specification"));
        comboBoxSpecification = new JComboBox(specificationOptions);
        comboBoxSpecification.setSelectedIndex(-1);
        add(comboBoxSpecification);

        // mailing class field
        add(new JLabel("Mailing Class"));
        comboBoxMailingClass = new JComboBox(mailingClassOptions);
        comboBoxMailingClass.setSelectedIndex(-1);
        add(comboBoxMailingClass);

        //other details fields
        add(new JLabel("Other Details"));
        panelOtherDetails = new JPanel();
        add(panelOtherDetails);

        // submit panel
        add(new JLabel(""));
        panelSubmit = new JPanel();
        add(panelSubmit);

        // buttons
        btnSubmit = new JButton("Add Package");
        btnSubmit.addActionListener(this);
        btnReset = new JButton("Reset");
        btnReset.addActionListener(this);
        btnShowPackages = new JButton("Show All Packages");
        btnShowPackages.addActionListener(this);


        panelSubmit.add(btnShowPackages);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }


    /**
     * Depending on which type is selected. Creates new input values with the selection made by the user.
     * @param e
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (comboBoxPackageType.getSelectedIndex() != -1) {

            switch (comboBoxPackageType.getSelectedIndex()) {
                case 0:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Dimension (inches)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Volume (inches)");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);

                    break;
                case 1:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Load Weight (inches)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Content");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);
                    break;
                case 2:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Material (Plastic, Fiber)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Diameter (inches)");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);
                    break;
                case 3:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Height (inches)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Width (inches)");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);
                    break;
            }

            // disable the box to block user from created unnecessary add-ons
            comboBoxPackageType.setEnabled(false);

            // add new buttons and repaint the frame
            panelSubmit.add(btnSubmit);
            panelSubmit.add(btnReset);
            repaint();
            setVisible(true);
        }
    }


    /**
     * Validates information of package being entered. If valid, adds package to database. If not valid, notifies user
     * to updated the information and try again. It also reacts to the user event when user clicks the 'reset button'.
     * If reset button is click it resets the form and generates and auto-fill the tracking number field. If the user
     * clicks the 'show all packages' button it initializes that class and presents the packages table.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            trackingNumber = textFieldTrackingNumber.getText();
            type = (String) comboBoxPackageType.getItemAt(comboBoxPackageType.getSelectedIndex());
            specification = (String) comboBoxSpecification.getItemAt(comboBoxSpecification.getSelectedIndex());
            mailingClass = (String) comboBoxMailingClass.getItemAt(comboBoxMailingClass.getSelectedIndex());
            otherdetails1 =  textFieldOtherDetails1.getText();
            otherdetails2 =  textFieldOtherDetails2.getText();

            //validating tracking number
            if (isDuplicateTrackingNumber(trackingNumber)) {
                JOptionPane.showMessageDialog(new JFrame(), "Package with the specified tracking number " +
                        "already exists. Please change the tracking number and try again.");
                return;
            }

            if (!trackingNumber.matches("[A-Za-z0-9]{5}")) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for the tracking number. " +
                        "(Must be of length 5)");
                return;
            }

            if (type == "" || specification == "" || mailingClass == "" || otherdetails1 == "" || otherdetails2 == "") {
                JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                return;
            }

            switch (comboBoxPackageType.getSelectedIndex()) {
                case 0:
                    try {
                        ss.addBox(trackingNumber, specification, mailingClass, Integer.parseInt(otherdetails1), Integer.parseInt(otherdetails2));
                        JOptionPane.showMessageDialog(new JFrame(), successMessage);
                        LOGGER.info("New package of type Box added to the packages list");
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                        LOGGER.severe("A NumberFormatException occurred while attempting to add save the package");
                    }
                    break;
                case 1:
                    try {
                        ss.addCrate(trackingNumber, specification, mailingClass,Float.parseFloat(otherdetails1), otherdetails2);
                        JOptionPane.showMessageDialog(new JFrame(), successMessage);
                        LOGGER.info("New package of type Crate added to the packages list");
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                        LOGGER.severe("A NumberFormatException occurred while attempting to add save the package");
                    }
                    break;
                case 2:
                    try {
                        //validate that details entered is plastic or fiber only
                        if ((otherdetails1.equalsIgnoreCase("Plastic")) || otherdetails1.equalsIgnoreCase("Fiber")) {
                            ss.addDrum(trackingNumber, specification, mailingClass, otherdetails1, Float.parseFloat(otherdetails2));
                            JOptionPane.showMessageDialog(new JFrame(), successMessage);
                            LOGGER.info("New package of type Drum added to the packages list");
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Please enter 'Plastic or Fiber'");
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                        LOGGER.severe("A NumberFormatException occurred while attempting to add save the package");
                    }
                    break;
                case 3:
                    try {
                        ss.addEnvelope(trackingNumber, specification, mailingClass, Integer.parseInt(otherdetails1), Integer.parseInt(otherdetails2));
                        JOptionPane.showMessageDialog(new JFrame(), successMessage);
                        LOGGER.info("New package of type Envelope added to the packages list");

                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                        LOGGER.severe("A NumberFormatException occurred while attempting to add save the package to the packages");
                    }
                    break;
            }
            // save changes
            ss.writeDatabase();
            LOGGER.info("Saving changes to ShippingStore db");
        }
        if (e.getSource() == btnReset) {
            LOGGER.info("Resetting 'Add a New Package' form");

            textFieldTrackingNumber.setText(randomlyGeneratedTrackingNumber());
            comboBoxPackageType.setSelectedIndex(-1);
            comboBoxSpecification.setSelectedIndex(-1);
            comboBoxMailingClass.setSelectedIndex(-1);
            textFieldOtherDetails1.setText("");
            textFieldOtherDetails2.setText("");

            comboBoxPackageType.setEnabled(true);
            panelOtherDetails.remove(textFieldOtherDetails1);
            panelOtherDetails.remove(textFieldOtherDetails2);
            panelOtherDetails.remove(labelOtherDetails1);
            panelOtherDetails.remove(labelOtherDetails2);
            repaint();
            setVisible(true);
        }

        if (e.getSource() == btnShowPackages) {
            ShowPackages sp = new ShowPackages();
            sp.setLocation(this.getX(), this.getY()+200);
            LOGGER.info("User selects: Show All Packages");
        }
    }


    /**
     * Creates a random tracking number of length 5 and returns it to the caller.
     * @return text - the string containing the random tracking number generated
     */

    public String randomlyGeneratedTrackingNumber() {
        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        long randomNumber = 0;
           for (int i = 0; i < 5; i++) {
               text += possible.charAt((int) Math.floor(Math.random() * possible.length()));
           }
        return text;
    }


    /**
     * Checks if tracking number is duplicate.
     * @param trackingNumber
     * @return true - if duplicate number is detected, false otherwise
     */

    public boolean isDuplicateTrackingNumber(String trackingNumber) {
        for (Package p: packages) {
            if (p.getPtn().equalsIgnoreCase(trackingNumber))
                    return true;
        }
        return false;
    }
}


