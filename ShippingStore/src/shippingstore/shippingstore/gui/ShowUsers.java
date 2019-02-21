package shippingstore.shippingstore.gui;

import shippingstore.*;
import shippingstore.Box;
import shippingstore.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * @author Zac Golla and Kentessa Fanfair
 *
 * ShowUsers is a subclass of JFrame class. It shows all current users in database.
 */
public class ShowUsers extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    JTable usersTable;
    String[] columnNames = {"User ID", "First Name", "Last Name", "Other Details"};
    ArrayList<User> users;


    /**
     * Default Constructor. Fills the frame with defined components and sets default settings for the main frame.
     */
    ShowUsers() {
        setSize(920, 500);
        setTitle("Show All Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ShippingStore ss = new ShippingStore().readDatabase();
        users = (ArrayList) ss.getUsers();

        usersTable = new JTable();
        usersTable.setModel(new DefaultTableModel(new Object[][]{}, columnNames));
        usersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        usersTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        usersTable.getColumnModel().getColumn(3).setPreferredWidth(500);

        addRowToJTable();

        add(new JScrollPane(usersTable));

        setVisible(true);
    }


    /**
     * Populates the data from each user from the database onto the table in a formatted way.
     */
    public void addRowToJTable() {
        LOGGER.info("Generating users table");

        DefaultTableModel tableModel = (DefaultTableModel) usersTable.getModel();
        Object rowData[] = new Object[4];

        for (User u : users) {
            rowData[0] = u.getId();
            rowData[1] = u.getFirstName();
            rowData[2] = u.getLastName();
            rowData[3] = getOtherUserInfo(u);

            tableModel.addRow(rowData);
        }
    }


    /**
     * Adds additional information needed for user based on user type.
     * @param u
     * @return
     */
    public String getOtherUserInfo(User u) {
        if(u instanceof Employee)
            return "Social: " + ((Employee) u).getSocialSecurityNumber() + ", Salary: " + ((Employee) u).getMonthlySalary() + ", Bank #:" + ((Employee) u).getBankAccountNumber();
        else
            return "Phone Number: " + ((Customer) u).getPhoneNumber() + ", Address: " + ((Customer) u).getAddress();
    }
}

