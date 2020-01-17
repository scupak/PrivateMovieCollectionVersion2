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
import privateMovieCollection.be.Movie;
import privateMovieCollection.dal.PmcDalException;

/**
 * This class lets the user delete movies.
 * @author kacpe
 */
public class DeleteMovieController {

    private AppModel appModel;
    private Movie movie;
    @FXML
    private Button yes;
    @FXML
    private Button no;

    /**
     * Set the appModel
     *
     * @param app
     */
    public void setAppModel(AppModel app) {
        appModel = app;
    }

    /**
     * set the selcted moive
     * @param movie 
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    /**
     * delete the selcted song
     * @param event 
     */
    @FXML
    private void yes(ActionEvent event) {
        try {
            if ( movie.getCategoryArray().isEmpty() == false ) {
                for (Category category : movie.getCategoryArray()) {
                    appModel.clearMovieFromCategory(category,movie);
                    appModel.moviesInCategoriesClearAdd(category);
                }
            }
            
            appModel.deleteMovie(movie);
            appModel.movieClearAdd();
            appModel.categoriesClearAdd();
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
     * Closes the window without doing anything else
     * @param event 
     */
    @FXML
    private void no(ActionEvent event) {
        Stage stage = (Stage) no.getScene().getWindow();
        stage.close();
    }

}
