package shippingstore.shippingstore.gui;

import java.io.IOException;
import java.util.logging.*;


/**
 * StoreLogger is a class that creates a logger to be implemented for the shipping store GUI.
 */
public class StoreLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    private static final String dirname = System.getProperty("user.dir");


    /**
     * Gets and sets the  global logger for the entire application. It also creates the file that stores all information
     * logged during runtime.
     * @throws IOException
     */
    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setUseParentHandlers(false); // set to false if you want to suppress the logging from the console.
                                           // set to true if you want the logging to print to the console.

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("ShippingStoreLogs.txt");

         // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}