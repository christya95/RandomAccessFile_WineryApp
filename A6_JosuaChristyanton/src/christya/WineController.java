/*
    WineController.java
    Author: H.D
    Date: n / a

    Description
    A controller class for FXMLWine.fxml file
*/
package christya;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author hdvor
 */
public class WineController implements Initializable {

    @FXML private TextField txtWineID;
    @FXML private TextField txtEstate;
    @FXML private TextField txtGrape;
    @FXML private TextField txtYear;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtPrice;
    @FXML private GridPane pnlWine;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtWineID.setEditable(false);
        Platform.runLater(() -> txtEstate.requestFocus());
    }  

    /**
     * Accessor for getting the txtWine() from other controllers
     * @return the textField object used for wineID property
     */
    public TextField getTxtWineID() {
        return txtWineID;
    }

    /**
     * Accessor for getting the txtEstate() from other controllers
     * @return the textField object used for estate property
     */
    public TextField getTxtEstate() {
        return txtEstate;
    }

    /**
     * Accessor for getting the txtGrape() from other controllers
     * @return the textField object used for grape property
     */
    public TextField getTxtGrape() {
        return txtGrape;
    }

    /**
     * Accessor for getting the txtYear() from other controllers
     * @return the textField object used for year property
     */
    public TextField getTxtYear() {
        return txtYear;
    }

    /**
     * Accessor for getting the txtQuantity() from other controllers
     * @return the textField object used for quantity property
     */
    public TextField getTxtQuantity() {
        return txtQuantity;
    }
    
    /**
     * Accessor for getting the txtPrice() from other controllers
     * @return the textField object used for price property
     */
    public TextField getTxtPrice() {
        return txtPrice;
    }
}
