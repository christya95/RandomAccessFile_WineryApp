/*
    Java 2:
    Author: H.D
    Modified By: Josua Christyanton
    Date: April 11th 2020
    
    Description:
    This is the main class which launches the program GUI.
*/
package christya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author H.D
 */
public class WineryApp extends Application{
    
    public static void main(String[] args) {
        launch(args);        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
        stage.setTitle("Winery Application");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
