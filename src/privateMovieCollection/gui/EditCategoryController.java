/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import privateMovieCollection.be.Category;
import privateMovieCollection.dal.PmcDalException;

/**
 *This class lets the user edit categories.
 * @author lumby
 */
public class EditCategoryController {
    private AppModel appModel;
    private Category category;

    @FXML
    private TextField categoryName;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    /**
     * set the appModel object
     * @param app 
     */
    public void setAppModel(AppModel app) {
        appModel = app;
    }
     
    /**
     * set the selcted category
     * @param category 
     */
    public void setCategory(Category category) {
        this.category = category;
        categoryName.setText(category.getName());
    }

    /**
     * applyes the changes to an already existing category
     * @param event 
     */
    @FXML
    private void save(ActionEvent event) {
        try {
            String name = categoryName.getText();
            
            Category categoryToUpdate = new Category(category.getId(), name, category.getMovies());
            appModel.updateCategory(categoryToUpdate);
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
     * closes the window without doing anything else
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
