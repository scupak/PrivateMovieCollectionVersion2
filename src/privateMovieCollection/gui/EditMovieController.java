/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.gui;

import java.awt.FileDialog;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import privateMovieCollection.be.Movie;
import privateMovieCollection.dal.PmcDalException;

/**
 * FXML Controller class
 *
 * @author lumby
 */
/* This class controls the fxml that lets the user edit movies*/
public class EditMovieController implements Initializable {

    private AppModel appModel;
    private Movie movie;
    private String filename = "";
    private String directory = "";
    
    @FXML
    private Label CategoryLabel;
    @FXML
    private Label TimeLabel;
    @FXML
    private Label fileLabel;
    @FXML
    private TextField movieTitleTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button movieChoiceButton;
    @FXML
    private TextField fileTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private Slider raitingSlider;
    @FXML
    private Label raitingLabel;


    /**
     * Initializes the controller class. Creates a list of categories and sets
     * it to the choiceBox.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        raitingSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                raitingLabel.setText(Math.round(raitingSlider.getValue())+"");
            }
        });
    }

    /**
     * Set the AppModel
     *
     * @param app
     */
    public void setAppModel(AppModel app) {
        appModel = app;
    }
    
    /**
     * set the selcted movie
     * @param movie 
     */
    public void setMovie(Movie movie) {
        this.movie = movie;

        movieTitleTextField.setText(movie.getTitle());
        categoryTextField.setText(movie.getCategories());
        raitingSlider.setValue(movie.getRating());
        fileTextField.setText(movie.getPath());
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
    

    /**
     * applyes the changes to an already existing movie
     * @param event 
     */
    @FXML
    private void save(ActionEvent event) {
        String title = movieTitleTextField.getText(); 
        String category = categoryTextField.getText();
        String moviePath = fileTextField.getText();
        String raiting = raitingLabel.getText();
        int intRaiting;
        
        try {
            intRaiting = Integer.parseInt(raiting);
        } catch(NumberFormatException e) {
            intRaiting = 0;
            JFrame jf=new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "invalid input or movie with same name already exists");
        }
        
        Movie movieToUpdate = new Movie(movie.getId(), title, intRaiting,"","", moviePath, movie.getLastview());
        try{ 
            if(appModel.updateMovie(movieToUpdate) == false){
                throw new NullPointerException();
            } 
            cancel(event);
        }
        catch(NullPointerException exeption){
            JFrame jf=new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "invalid input or movie with same name already exists");
        } catch (PmcDalException ex) {
            JFrame jf=new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, ex);
        }
    }

    /**
     * opens a window to find the movies file
     * @param event 
     */
    @FXML
    private void movieChoiceButton(ActionEvent event) {
        FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null);
        fd.setDirectory("C:\\");
        fd.setFile("*.mp4;*.mpeg4");
        fd.setVisible(true);
        filename = fd.getFile();
        directory = fd.getDirectory();
        if (filename == null) {
            JOptionPane.showMessageDialog(null, "Add song canceled");
        } else {
            fileTextField.setText("movies/" + filename);
        }
    } 
}
