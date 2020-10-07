/*  Java 2:
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Wine;

public class WineTableViewController implements Initializable {

    @FXML
    private TableView<Wine> tblWines;
    @FXML
    private TableColumn<Wine, Integer> idCol;
    @FXML
    private TableColumn<Wine, String> estCol;
    @FXML
    private TableColumn<Wine, String> grapeCol;
    @FXML
    private TableColumn<Wine, Integer> yearCol;
    @FXML
    private TableColumn<Wine, Integer> qtyCol;
    @FXML
    private TableColumn<Wine, Double> priceCol;

    private ObservableList<Wine> olWines;

    // TODO Part: 1 - to be repeated in MainController as well
    // Declare as constants the length of the String fields in characters and the 
    // length of the record in bytes
    char STRING_LENGTH = 15;
    byte RECORD_LENGTH = 80;

    //END of Part 1
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("wineID"));
        estCol.setCellValueFactory(new PropertyValueFactory<>("estate"));
        grapeCol.setCellValueFactory(new PropertyValueFactory<>("grape"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //load values from file here
        loadWines();
    }

    /**
     * Method for loading the content of the file on to the TableView
     */
    private void loadWines() {
        olWines = FXCollections.observableArrayList();
        /**
         * TODO: Part 3 - Load the records on to the TableView 1. Create a
         * RandomAccessFile object in read only mode. It will create/open a file
         * located at "src/res/wines.dat"
         */
        /* 2. Position the pointer at the beginning of the file and read each
            * value in each record considering the order and type of data in the file
            * (int, String(15 characters), String(15 characters), int, int, double.
         */
        File loadFile = new File("src/res/wines.dat");

        try {
            RandomAccessFile raf = new RandomAccessFile(loadFile, "r");
            raf.seek(0);

            long entries = (raf.length() / RECORD_LENGTH);

            for (int i = 0; i < entries; i++) {

                int wineID = raf.readInt();
                String estateString = readString(raf, STRING_LENGTH);
                String grapeString = readString(raf, STRING_LENGTH);
                int year = raf.readInt();
                int quantity = raf.readInt();
                double price = raf.readDouble();

                Wine wineEntry
                        = new Wine(estateString.trim(), grapeString.trim(), year, quantity, price);

                wineEntry.setWineID(wineID);

                olWines.add(wineEntry);
            }

            tblWines.setItems(olWines);

        } catch (FileNotFoundException ex) {
            System.out.println("Error in loading file!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        } catch (IOException ex2) {
            System.out.println("Error in length of record! " + ex2.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(ex2.getMessage());
            alert.showAndWait();
        } catch (NullPointerException ex3) {
            System.out.println("Error in loading selected wine!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Error in loading selected wine! " + ex3.getMessage());
            alert.showAndWait();
        }

        /* 3. Use readString() method (Part 2) to read the strings of the required
            * length.
            * 4. Create a wine object using the multi-parameter constructor. You can
            * trim the String values to remove the spaced added to the end when it
            * was written.
            * 5. Set the wineID since it was not set by the constructor
            * 6. Add the wine object on to the ObservableLiist. Remember TableViews
            * are just like ListViews we covered during our course.
            * 7. Add the ObservableList to the TableView so it can be displayed.
         */
        //END of Part 3
    }

    /**
     * Method for reading a String of a length size using a RandomAccessFile
     * object that is passes as a parameter. It reads the string one character
     * at a time and concatinates them into a string that is returned back
     *
     * @param raf RandomAccessFile object
     * @param size the length od the string that needs to be read
     * @return the String that was read from the file
     * @throws IOException throws back the IOExceptin thrown by readChar()
     * method
     */
    private String readString(RandomAccessFile raf, int size) throws IOException {
        //TODO: Part 2:
        //Read the record one character at a time, concatinate them and 
        // return back as String

        String str = "";
        for (int i = 0; i < size; i++) {
            str += String.valueOf(raf.readChar());
        }

        if (str.length() < size) {
            int numSpaces = size - str.length();
            for (int i = 0; i < numSpaces; i++) {
                str += " ";
            }
        } else {
            str = str.substring(0, size);
        }

        return str; // to be replaced by the actual return
        //END of Part 2
    }

    /**
     * Accessor method for getting the item (row) that is selected in the
     * TableView and return the wine object that correspond to that table row.
     * This method can be used in other controller to find out which row/wine
     * was selected
     *
     * @return the wine object represented by the tableView selection
     */
    public Wine getSelectedWine() {
        Wine w = tblWines.getSelectionModel().getSelectedItem();
        return w;
    }

    /**
     * Accessor for getting the observable list that is used by the TableView.
     * This method can be used in other controllers
     *
     * @return the ObservableList with Wine objects used in the TableView
     */
    public ObservableList<Wine> getObservableList() {
        return olWines;
    }

    /**
     * Accessor for getting the TableView from other classes (controllers)
     *
     * @return the TableView object used in this controller
     */
    public TableView<Wine> getTableView() {
        return tblWines;
    }
}
