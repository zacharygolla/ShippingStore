package shippingstore.shippingstore.gui;

import shippingstore.Customer;
import shippingstore.Employee;
import shippingstore.ShippingStore;
import shippingstore.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;



/**
 * @author Zac Golla and Kentessa Fanfair
 *
 * UpdateUser is a subclass of the JFrame it class. It updates the information of a user based
 * on user ID number provided.
 */
public class UpdateUser extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private  String userId;


    /**
     * Default constructor for UpdateUser. Creates the initial frame that allows user to input
     * user ID number to be updated. Also gives ability to show current Users in database. It also populated the text
     * field with current information that was stored.
     */
    UpdateUser() {
        ShippingStore ss;
        ss = new ShippingStore().readDatabase();

        JFrame updateUserFrame = new JFrame("Update a User");
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        JPanel userFrameTitlePanel = new JPanel(new GridBagLayout());
        JLabel userFrameTitle = new JLabel("Update User Menu");
        JLabel userFrameInstructions = new JLabel("<html>Please search for the user who's <br>details are to be" +
                " updated by User ID<html>");
        c.gridx = 0;
        c.gridy = 0;
        userFrameTitlePanel.add(userFrameTitle, c);
        c.gridx = 0;
        c.gridy = 1;
        userFrameTitlePanel.add(userFrameInstructions, c);
        updateUserFrame.add(userFrameTitlePanel, BorderLayout.NORTH);

        JPanel userFrameCenterPanel = new JPanel(new GridBagLayout());
        JLabel enterUserId = new JLabel("Enter User ID");
        JTextField enterUserIdText = new JTextField();
        enterUserIdText.setPreferredSize(new Dimension(80, 20));
        c.gridx = 0;
        c.gridy = 0;
        userFrameCenterPanel.add(enterUserId, c);
        c.gridx = 1;
        c.gridy = 0;
        userFrameCenterPanel.add(enterUserIdText, c);
        updateUserFrame.add(userFrameCenterPanel, BorderLayout.CENTER);

        JPanel userFrameButtonPanel = new JPanel(new GridBagLayout());
        userFrameButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = new JButton("Next");
        JButton cancelButton = new JButton("Cancel");
        JButton showCurrentUsersButton = new JButton("Show Current Users");
        userFrameButtonPanel.add(showCurrentUsersButton);
        userFrameButtonPanel.add(cancelButton);
        userFrameButtonPanel.add(nextButton);
        updateUserFrame.add(userFrameButtonPanel, BorderLayout.SOUTH);

        updateUserFrame.pack();
        updateUserFrame.setVisible(true);

        showCurrentUsersButton.addActionListener(new ActionListener() {
            /**
             * Show list of users when button is clicked
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowUsers show = new ShowUsers();
                show.setLocation(updateUserFrame.getX(), updateUserFrame.getY()+153);
                LOGGER.info("User selects: Show All Users options.");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Event of Action Listener for cancel button. Cancel button disposes current frame and
             * gives user the ability to chose a different menu option.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserFrame.dispose();
            }
        });

        /**
         * The next button builds compares user ID to whether it is either a customer
         * or employee and loads the correct corresponding frame that is necessary.
         * @param e
         */
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userId = enterUserIdText.getText();
                try {
                    Integer userIdInt = Integer.parseInt(userId);

                    if (!ss.userExists(userIdInt)) {
                    JOptionPane.showMessageDialog(new JFrame(), "User not found. Please check the user id " +
                            "and try again");

                    }
                    else if (ss.isCustomer(userIdInt)) {
                        JFrame addCustomerFrame = new JFrame("Update Customer Information");
                        addCustomerFrame.setSize(400, 400);

                        // get user information
                         User user = ss.findUser(userIdInt);

                        // create panel for title
                        JPanel topPanel = new JPanel();
                        JLabel titleLabel = new JLabel("Update Customer Menu", JLabel.CENTER);
                        topPanel.add(titleLabel);
                        addCustomerFrame.add(topPanel, BorderLayout.NORTH);

                        //create grid for labels and fields
                        JPanel centerPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints c = new GridBagConstraints();
                        c.insets = new Insets(5, 5, 5, 5);

                        //first name
                        JLabel firstNameLabel = new JLabel("First Name: ");
                        JTextField firstNameField = new JTextField();
                        firstNameField.setPreferredSize(new Dimension(120, 20));
                        firstNameField.setText(user.getFirstName());

                        c.gridx = 0;
                        c.gridy = 0;
                        centerPanel.add(firstNameLabel, c);
                        c.gridx = 1;
                        c.gridy = 0;
                        centerPanel.add(firstNameField, c);

                        //last name
                        JLabel lastNameLabel = new JLabel("Last Name: ", JLabel.CENTER);
                        JTextField lastNameField = new JTextField();
                        lastNameField.setText(user.getLastName());

                        lastNameField.setPreferredSize(new Dimension(120, 20));
                        c.gridx = 0;
                        c.gridy = 1;
                        centerPanel.add(lastNameLabel, c);
                        c.gridx = 1;
                        c.gridy = 1;
                        centerPanel.add(lastNameField, c);

                        //phone number
                        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
                        JTextField phoneNumberField = new JTextField();
                        phoneNumberField.setText(((Customer) user).getPhoneNumber());
                        phoneNumberField.setPreferredSize(new Dimension(120, 20));
                        c.gridx = 0;
                        c.gridy = 2;
                        centerPanel.add(phoneNumberLabel, c);
                        c.gridx = 1;
                        c.gridy = 2;
                        centerPanel.add(phoneNumberField, c);

                        //address
                        JLabel addressLabel = new JLabel("Address: ");
                        JTextField addressField = new JTextField();
                        addressField.setPreferredSize(new Dimension(200, 20));
                        addressField.setText(((Customer) user).getAddress());
                        c.gridx = 0;
                        c.gridy = 3;
                        centerPanel.add(addressLabel, c);
                        c.gridx = 1;
                        c.gridy = 3;
                        centerPanel.add(addressField, c);
                        addCustomerFrame.add(centerPanel, BorderLayout.WEST);

                        //create next and cancel buttons
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        JButton nextButton = new JButton("Save Changes");
                        JButton cancelButton = new JButton("Cancel");
                        buttonPanel.add(cancelButton);
                        buttonPanel.add(nextButton);
                        addCustomerFrame.add(buttonPanel, BorderLayout.SOUTH);

                        addCustomerFrame.pack();
                        addCustomerFrame.setVisible(true);

                        nextButton.addActionListener(new ActionListener() {
                            /**
                             * Validates user information if customer when next button is pressed. If input passes
                             * validation it updates user with corresponding user ID.
                             * @param e
                             */
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String firstName = firstNameField.getText();
                                String lastName = lastNameField.getText();
                                String phoneNumber = phoneNumberField.getText();
                                String address = addressField.getText().trim();

                                // validate customer names
                                if (firstName.matches("[a-zA-z\\s.]*") && lastName.matches("[a-zA-z\\s]*")) {

                                    //validate customer phone number
                                    if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {

                                        //validate customer address
                                        if (address.matches("[\\d]+[A-Za-z0-9\\s,.]+?[a-zA-Z]+[\\s]+[\\d{5}]+")) {
                                            try {
                                                ss.updateCustomer(userIdInt, firstName, lastName, phoneNumber, address);
                                                ss.writeDatabase();
                                                addCustomerFrame.dispose();
                                                JOptionPane.showMessageDialog(new JFrame(), "Success: Customer " +
                                                        "information was updated.");
                                                LOGGER.info("The customer information was updated in the database.");
                                            } catch (Exception exception) {
                                                LOGGER.severe("An error occurred while attempting to update the " +
                                                        "customer information");

                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for " +
                                                    "customer address. (Follow this format: 123 East St, 78666 TX)");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for phone " +
                                                "number. (Follow this format: 221-129-8762)");
                                    }

                                } else {
                                    JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for customer name " +
                                            "(Enter alpha characters only)");
                                }
                            }

                            });

                        cancelButton.addActionListener(new ActionListener() {
                            /**
                             * Closes add customer frame and returns to showing previous frame.
                             * @param e
                             */
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                addCustomerFrame.dispose();
                            }
                        });

                    }
                    else {
                        JFrame updateEmployeeFrame = new JFrame("Update Employee Information");
                        JPanel titlePanel = new JPanel();
                        JLabel titleLabel = new JLabel("Update Employee Menu");

                        titlePanel.add(titleLabel);
                        updateEmployeeFrame.add(titlePanel, BorderLayout.NORTH);

                        JPanel centerPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints c = new GridBagConstraints();

                        c.insets = new Insets(5, 5, 5, 5);

                        // get user information
                        User user = ss.findUser(userIdInt);

                        //first name
                        JLabel firstNameLabel = new JLabel("First Name: ");
                        JTextField firstNameField = new JTextField();
                        firstNameField.setPreferredSize(new Dimension(120, 20));
                        firstNameField.setText(user.getFirstName());
                        c.gridx = 0;
                        c.gridy = 0;
                        centerPanel.add(firstNameLabel, c);
                        c.gridx = 1;
                        c.gridy = 0;
                        centerPanel.add(firstNameField, c);

                        //last name
                        JLabel lastNameLabel = new JLabel("Last Name: ", JLabel.CENTER);
                        JTextField lastNameField = new JTextField();
                        lastNameField.setPreferredSize(new Dimension(120, 20));
                        lastNameField.setText(user.getLastName());
                        c.gridx = 0;
                        c.gridy = 1;
                        centerPanel.add(lastNameLabel, c);
                        c.gridx = 1;
                        c.gridy = 1;
                        centerPanel.add(lastNameField, c);

                        //social
                        JLabel socialLabel = new JLabel("Social (must be 9 digits): ");
                        JTextField socialField = new JTextField();
                        socialField.setPreferredSize(new Dimension(120, 20));
                        socialField.setText(((Employee) user).getSocialSecurityNumber() + "");
                        c.gridx = 0;
                        c.gridy = 2;
                        centerPanel.add(socialLabel, c);
                        c.gridx = 1;
                        c.gridy = 2;
                        centerPanel.add(socialField, c);

                        //salary
                        JLabel salaryLabel = new JLabel("Salary: ");
                        JTextField salaryField = new JTextField();
                        salaryField.setPreferredSize(new Dimension(120, 20));
                        salaryField.setText(((Employee) user).getMonthlySalary() + "");
                        c.gridx = 0;
                        c.gridy = 3;
                        centerPanel.add(salaryLabel, c);
                        c.gridx = 1;
                        c.gridy = 3;
                        centerPanel.add(salaryField, c);
                        updateEmployeeFrame.add(centerPanel, BorderLayout.WEST);

                        //bank number
                        JLabel bankNumberLabel = new JLabel("Bank # (must be less than 10-digits) : ");
                        JTextField bankNumberField = new JTextField();
                        bankNumberField.setPreferredSize(new Dimension(120, 20));
                        bankNumberField.setText(((Employee) user).getBankAccountNumber() + "");
                        c.gridx = 0;
                        c.gridy = 4;
                        centerPanel.add(bankNumberLabel, c);
                        c.gridx = 1;
                        c.gridy = 4;
                        centerPanel.add(bankNumberField, c);
                        updateEmployeeFrame.add(centerPanel, BorderLayout.WEST);

                        //create next and cancel buttons
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        JButton nextButton = new JButton("Save Changes");
                        JButton cancelButton = new JButton("Cancel");
                        buttonPanel.add(cancelButton);
                        buttonPanel.add(nextButton);
                        updateEmployeeFrame.add(buttonPanel, BorderLayout.SOUTH);

                        updateEmployeeFrame.pack();
                        updateEmployeeFrame.setVisible(true);

                        nextButton.addActionListener(new ActionListener() {
                            /**
                             * Validates user information if employee when next button is pressed. If input passes
                             * validation it updates user with corresponding user ID.
                             * @param e
                             */
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String firstName = firstNameField.getText();
                                String lastName = lastNameField.getText();
                                String social = socialField.getText();
                                String salary = salaryField.getText();
                                String bankNumber = bankNumberField.getText();

                                try {
                                    Integer socialInt = new Integer(Integer.valueOf(social));
                                    Float salaryFloat = new Float(Float.valueOf(salary));
                                    Integer bankNumberInt = new Integer(Integer.valueOf(bankNumber));

                                    // validate employee names
                                    if (firstName.matches("[a-zA-z\\s.]*") && lastName.matches("[a-zA-z\\s]*")) {

                                        //validate social security number
                                        if (social.matches("[0-9]{9}")) {

                                            //validate salary data
                                            if (salary.matches("[-+]?[0-9]*\\.?[0-9]+")) {

                                                //validate bank# data
                                                if (bankNumber.matches("[0-9]{1,10}")) {
                                                    try {
                                                        ss.updateEmployee(userIdInt, firstName, lastName, socialInt, salaryFloat, bankNumberInt);
                                                        ss.writeDatabase();
                                                        JOptionPane.showMessageDialog(new JFrame(), "Success: " +
                                                                "Employee information was  updated");
                                                        updateEmployeeFrame.dispose();
                                                        LOGGER.info("Employee information was updated");
                                                    } catch (Exception e3) {
                                                        JOptionPane.showMessageDialog(new JFrame(), "An error" +
                                                                " occurred while attempting to save the information.");
                                                        LOGGER.severe("An error occurred while attempting to save the employee information.");
                                                    }

                                                } else {
                                                    JOptionPane.showMessageDialog(new JFrame(), "Invalid entry " +
                                                            "for bank account number. (Enter numeric values only)");
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for" +
                                                        " salary data number. (Enter numeric values only)");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for social " +
                                                    "security number. (Enter exactly 9-digits)");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for employee " +
                                                "name. (Enter alpha characters only)");
                                    }
                                }catch (NumberFormatException nfe) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Empty Field or Invalid Entry " +
                                            "detected. Please check the information you entered and try again");
                                    LOGGER.severe("A NumberFormatException was encountered");
                                }
                            }
                        });
                        cancelButton.addActionListener(new ActionListener() {
                            /**
                             * Closes add employee frame and returns to showing previous frame.
                             * @param e
                             */
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                updateEmployeeFrame.dispose();
                            }
                        });

                        cancelButton.addActionListener(new ActionListener() {
                            /**
                             * Closes add user frame and returns to showing original menu options.
                             * @param e
                             */
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                updateUserFrame.dispose();
                            }
                        });
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please Enter a the User Id (Integer values only).");
                    LOGGER.warning("A method was called on a null object");
                }
            }
        });
    }
}
