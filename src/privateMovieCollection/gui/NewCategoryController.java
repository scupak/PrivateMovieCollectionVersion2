/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import privateMovieCollection.be.Category;
import privateMovieCollection.dal.PmcDalException;

/**
 * FXML Controller class
 *This class lets the user make new categories
 * @author lumby
 */
public class NewCategoryController implements Initializable { 
    private AppModel appModel;

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField categoryName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Allows the appControler to pass the appmodel to this class. 
     * @param app 
     */
    public void setAppModel(AppModel app) {
        appModel = app;
    }

    /**
     * Names the category based on input then creates a new category. 
     * @param event 
     */
    @FXML
    private void save(ActionEvent event) {
        try {
            String title = categoryName.getText();
            Category categoryToAdd = new Category(0, title, 0);
            appModel.createCategory(categoryToAdd);
            cancel(event);
        } catch (PmcDalException ex) {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString());

                alert.showAndWait();
        }
    }

    /**
     * Closes the stage.
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
