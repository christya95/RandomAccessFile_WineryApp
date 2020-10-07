/*
    Java 2:
    Author: Josua Christyanton
    Date: April 11th 2020
       
    Description:
    This class deals with all the button actions, to add wine entries, update an 
    existing entry and to delete the last entry in the file. There is also an 
    exit button and a help button which opens up a readme.txt.
 */
package christya;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import models.Wine;

public class MainController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Menu mnuFile;
    @FXML
    private MenuItem itemExit;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    /*
    * This is something that you may have not seen before. The following two 
    * lines are written manually (not generated by Make Controller). Make sure 
    * that if you remake your controller the @FXML part is placed at the 
    * beggining of each line. The reason for these lines is to import the other
    * two controllers into this controller, so we can access their data including
    * GUI components. There are many other more complicated ways to do this, but 
    * this is by far the simpliest one. The way it is done is by concatinating 
    * the fx:id of the controller with the word Controller.*/
    @FXML
    private WineController pnlWineController;
    @FXML
    private WineTableViewController pnlViewController;

    /* TODO Part: 1 - to be repeated in WineTableViewController as well
    * Declare as constants the length of the String fields in characters and the 
    * length of the record in bytes
     */
    char STRING_LENGTH = 15;
    byte RECORD_LENGTH = 80;

    // END of Part 1
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        // show record that is selected in the tables view into the pnlWine view
        pnlViewController.getTableView().setOnMouseClicked(eh -> showWineRecord());
    }

    /**
     * This methods gets the wine record from the row selected in the TableView
     * and displays it in the other view in each text field
     */
    private void showWineRecord() {
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        Wine selectedWine = pnlViewController.getSelectedWine();
        pnlWineController.getTxtWineID().setText(String.valueOf(selectedWine.getWineID()));
        pnlWineController.getTxtEstate().setText(selectedWine.getEstate());
        pnlWineController.getTxtGrape().setText(selectedWine.getGrape());
        pnlWineController.getTxtYear().setText(String.valueOf(selectedWine.getYear()));
        pnlWineController.getTxtQuantity().setText(String.valueOf(selectedWine.getQuantity()));
        pnlWineController.getTxtPrice().setText(String.valueOf(selectedWine.getPrice()));
    }

    /**
     * Call writeRecord() method to write a Wine record on to the file
     */
    @FXML
    private void addNew(ActionEvent event) {
        writeRecord();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    /**
     * Method for writing a single wine record in the file. It uses writeWine()
     * to do the actual writing
     */
    private void writeRecord() {

        File f = new File("src/res/wines.dat");

        try {
            /* TODO Part: 6 - Write records on the file
            *  1. Create a RandomAccessFile object
            *  2. Use the length of the record to create the wineID. You need to do
            *  the calculation so the first record will have wine id = 0, the second
            *  will be 1 and so on. This way the user will not enter a value for the
            *  id avoiding having the same id multiple times.
            *  3. Create a Wine object using the readTextFields() method (Part 2)
            *  4. set the id to the wine returned from the previous step
            *  5. Write the content (properties) of the wine object on the file
            *  using writeWine (Part 3)
            *  6. Get the ObservableList from pnlViewController and add the wine to it
            *  7. Set the ObservableList onto the TableView of pnlViewController
            *  8. Handle multiple kind of exceptions thrown by above steps with an
            *  appropriate message. Display the error messages using showAlert()
             */

            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.seek(raf.length());
            int wineID = Integer.parseInt((raf.length() / RECORD_LENGTH) + "");
            Wine wineEntry = readTextFields();
            wineEntry.setWineID(wineID);
            writeWine(raf, wineEntry);
            pnlViewController.getObservableList().add(wineEntry);

            pnlViewController.getTableView().setItems(pnlViewController.getObservableList());
            raf.close();

            //END of Part 6
        } catch (FileNotFoundException ex) {
            showAlert("error", ex.getMessage());
        } catch (IOException ex) {
            showAlert("error", ex.getMessage());
        }
    }

    /**
     * This method is used by other method to write a wine record on the file
     */
    private void writeWine(RandomAccessFile raf, Wine w) throws IOException {

        /*TODO: Part 5 - Write one record on the file
        * Using the raf and the values from the wine object that are passed as
        * parameters write one record on the file opened/created by raf
         */
        raf.writeInt(w.getWineID());
        raf.writeChars(prepStringField(w.getEstate(), STRING_LENGTH));
        raf.writeChars(prepStringField(w.getGrape(), STRING_LENGTH));
        raf.writeInt(w.getYear());
        raf.writeInt(w.getQuantity());
        raf.writeDouble(w.getPrice());

        //END of Part 5
    }

    /**
     * Method for reading the data from the text fields and creating a wine
     * object without wineID
     *
     * @return a wine object that contains the values from the text fields as
     * properties.
     */
    private Wine readTextFields() {
        /* TODO Part: 4 - Create a wine object from the text field values
        *  Get values from all of the textfields (excluding txtWineID), create
        *  a wineobject and return it
         */

        String estateString = pnlWineController.getTxtEstate().getText();
        String grapeString = pnlWineController.getTxtGrape().getText();
        int year = Integer.parseInt(pnlWineController.getTxtYear().getText());
        int quantity = Integer.parseInt(pnlWineController.getTxtQuantity().getText());
        double price = Double.parseDouble(pnlWineController.getTxtPrice().getText());
        Wine wineEntry = null;
        try {
            wineEntry
                    = new Wine(estateString.trim(), grapeString.trim(), year, quantity, price);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        }

        return wineEntry; // to be replaced with the actual return
        //END of Part 4
    }

    /**
     * Method for displaying different alerts.
     *
     * @param alertType the type of alert (error, confirmation or information)
     * @param message the massage the alert will display
     * @return the alert object to the caller
     */
    private ButtonType showAlert(String alertType, String message) {
        Alert alert = null;
        Optional<ButtonType> result = null;
        if (alertType.equalsIgnoreCase("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else if (alertType.equalsIgnoreCase("confirmation")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you wish to exit?", ButtonType.YES, ButtonType.NO);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(alertType + " Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        result = alert.showAndWait();
        return result.get();
    }

    /**
     * This method is use by writeWine() method to make sure that all strings
     * are of the same size
     *
     * @param value the value of the string to be prepared
     * @param size the number of the characters the string must have
     * @return the string value that was passed as parameter, with added spaced
     * at the end if the string that was passed was less than the size, or a
     * truncated string if it was longer that size
     */
    private String prepStringField(String value, int size) {
        if (value.length() < size) {
            int numSpaces = size - value.length();
            for (int i = 0; i < numSpaces; i++) {
                value += " ";
            }
        } else {
            value = value.substring(0, size);
        }
        return value;
    }

    @FXML
    private void updateRecord(ActionEvent event) {

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        File f = new File("src/res/wines.dat");

        try {
            /* TODO Part 7 - Update a record
            *  1. Get the wine id number from the txtWineID field
            *  2. Use readTextFields() method (Part2) to create a Wine object
            *  3. Set the wineID to the wine created and returned by ReadTextFields()
            *  4. Create a RandomAccessFileObject in read and write mode
            *  5. Loop through each record in the file and compare the wineId with
            *  the the reading of the first part of the record that should be an
            *  integer. If they are equal, that's the record you need to update
            *  6. Reset the pointer to the beginning of the record (the pointer has
            *  four bytes ahead after reading the integer).
            *  7. Use writeWine() method to overwrite the record on the same raf
            *  pointer position to update the record. Don't forget to close raf
            *  8. Set the wine object in the ObservableList of pnlViewController
            *  in the same place. HINT: Use id as a position, because wine id is the
            *  same as the position of the object in the list (that's the way we
            *  created the wine id
            *  9. Set the observable list to the TableView. Remember in many of the
            *  parts here (6, 7, 8, 9) we are dealing with objects that are in a
            *  different controller.
            *  That's why the getters are in that controller for: to allow access to
            *  the components in that controller
             */

            RandomAccessFile randomFile = new RandomAccessFile(f, "rw");
            int wineID = Integer.parseInt(pnlWineController.getTxtWineID().getText());
            long entries = randomFile.length() / RECORD_LENGTH;
            Wine wineEntry = readTextFields();
            wineEntry.setWineID(wineID);

            for (int i = 0; i < entries; i++) {
                randomFile.seek(i * RECORD_LENGTH);
                if (randomFile.readInt() == wineID) {
                    randomFile.seek(i * RECORD_LENGTH);
                    writeWine(randomFile, wineEntry);
                }
            }
            randomFile.close();

            pnlViewController.getObservableList().set(wineID, wineEntry);
            pnlViewController.getTableView().setItems(pnlViewController.getObservableList());

        } catch (FileNotFoundException ex) {
            showAlert("error", ex.getMessage());
        } catch (IOException ex) {
            showAlert("error", ex.getMessage());
        }

        //END of Part 7
    }

    /**
     * This method will delete the last record in the file
     *
     */
    @FXML
    private void deleteRecord(ActionEvent event) {

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        int id = Integer.parseInt(pnlWineController.getTxtWineID().getText());
        Wine w = readTextFields();
        w.setWineID(id);
        File f = new File("src/res/wines.dat");

        try {
            /* TODO Part 8 - Delete the last record
            *  1. Create a RandomAccessFileObject in read and write mode
            *  2. Get the wine object from the ObservableList using the id as a
            *  position for that object in the list
            *  3. Set the length of the record to the length of existing one minus
            *  one record size. This will leave the data after tha point out,
            *  therefore deleting the last record.\
            *  4. Remove the wine from the ObservableList
            *  5. Set the observable list items on to the TableView.
            *  Note: The requiremet for this part is to delete the last record.
            *  Deleting a particular record that is selectd in the TableView requires
            *  a much more complex algorithm. If you can do it, you'll get double
            *  the points for this part.
             */
            RandomAccessFile randomFile = new RandomAccessFile(f, "rw");
            pnlViewController.getObservableList().get(id);
            long newRecordSize = randomFile.length() - RECORD_LENGTH;
            System.out.println("Removing id: " + id);
            pnlViewController.getObservableList().remove((int) (randomFile.length() / RECORD_LENGTH - 1));
            randomFile.setLength(newRecordSize);
            pnlViewController.getTableView().setItems(pnlViewController.getObservableList());
            System.out.println("Bytes: " + randomFile.length());
            System.out.println("Entries: " + randomFile.length() / RECORD_LENGTH);

            System.out.println(pnlViewController.getObservableList());
            randomFile.close();

            //END of Part 8
        } catch (FileNotFoundException ex) {
            showAlert("error", ex.getMessage());
        } catch (IOException ex) {
            showAlert("error", ex.getMessage());
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        /* TODO Part: 9 - Exit the application
        *  Use the showAlert() method to confirm before closing the application 
         */

        ButtonType result = showAlert("confirmation", "Do you wish to exit?");
        if (result == ButtonType.YES) {
            System.exit(0);
        }
        //END of Part 9
    }

    @FXML
    private void aboutHandler(ActionEvent event) throws IOException {
        /* TODO Part: 10 - Show the readme file
        *  Write the code that will open readme.txt file as an external file
        *  using your default text editor such as notpad or vi.
         */
        try {
            ProcessBuilder process = new ProcessBuilder("Notepad.exe", "readme.txt");
            process.start();
        } catch (Exception ex) {
            showAlert("error", ex.getMessage());
        }
        //END of Part 10
    }
}