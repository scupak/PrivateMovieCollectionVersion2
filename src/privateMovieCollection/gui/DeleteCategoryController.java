/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import privateMovieCollection.be.Category;
import privateMovieCollection.dal.PmcDalException;

/**
 * This class lets the user delete categories.
 * @author lumby
 */
public class DeleteCategoryController {
    
    private AppModel appModel;
    private Category category;
    
    @FXML
    private Button yes;
    @FXML
    private Button no;

    /**
     * set the appModel object
     * @param app 
     */
    public void setAppModel(AppModel app) {
        appModel = app;
    }
     
    /**
     * set the selcted playlist 
     * @param category 
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * delete the chosen category
     * @param event 
     */
    @FXML
    private void yes(ActionEvent event) {
        try {
            appModel.deleteCategory(category);
            appModel.categoriesClearAdd();
            appModel.movieClearAdd();
            appModel.moviesInCategoriesClearAdd(category);
            no(event);
        } catch (PmcDalException ex) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString());

                alert.showAndWait();
        }
    }

    /**
     * close the window without doing anything else
     * @param event 
     */
    @FXML
    private void no(ActionEvent event) {
        Stage stage = (Stage) no.getScene().getWindow();
        stage.close();
    }
    
}
