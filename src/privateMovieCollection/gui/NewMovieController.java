/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*Here the package that this class is in is defined*/
package privateMovieCollection.gui;

/*All the imports are defined here,the class needs to know witch other classes, packages or libraries it has acces to,
this also defines how the class fits into the programs design structure.*/
import java.awt.FileDialog;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import privateMovieCollection.be.Movie;
import java.lang.NullPointerException;
import javafx.scene.control.Alert;
import privateMovieCollection.dal.PmcDalException;
/**
 * FXML Controller class
 *
 * @author lumby
 */
/**
 * NewMovieController is the class that controls the fxml page where the user can
 * create and add new movies to the app
 */
public class NewMovieController implements Initializable {
    
    private AppModel appModel;
    private String filename = "";
    private String directory = "";
    
    @FXML
    private Label TimeLabel;
    @FXML
    private Label fileLabel;
    @FXML
    private TextField movieTitleTextField;
    private Button cancel;
    @FXML
    private Button Save;
    @FXML
    private TextField fileTextField;
    @FXML
    private Button movieChoiceButton;
    @FXML
    private Button Cancel;
    @FXML
    private Slider raitingSlider;
    @FXML
    private Label zero;
    @FXML
    private Label hundred;
    @FXML
    private Label raitingLabel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
     * set appModel object
     */
    public void setAppModel(AppModel app) {
        appModel = app;
    }

 
    /**
     * takes the input from the user and creates the movie.
     * @param event 
     */
    @FXML
    private void Save(ActionEvent event) {
        String title = movieTitleTextField.getText(); 
        String moviePath = fileTextField.getText();
        String raiting = raitingLabel.getText();
        Date lastView = new Date();
        int intRaiting;
        
        try {
            intRaiting = Integer.parseInt(raiting);
        } catch(NumberFormatException e) {
            intRaiting = 0;
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(e.toString() + "invalid input or movie with same name already exists");
                alert.showAndWait();
        }
        
        try {
            Movie movieToAdd = new Movie(1, title, intRaiting,"","", moviePath, lastView); 
            if(appModel.createMovie(movieToAdd) == null){
                throw new NullPointerException();
            }  
            cancel(event);
            
        } catch(NullPointerException exeption) {
            System.out.println("samme titel");
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(exeption.toString() + "invalid input or movie with same name already exists");
                alert.showAndWait();
        
        } catch (PmcDalException ex) {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString() + "invalid input or movie with same name already exists");
                alert.showAndWait();
        }
       
    }

    /**
     * Lets the user indicate the video file to to asigned. 
     * @param event 
     */
    @FXML
    private void movieChoiceButton(ActionEvent event) {
        FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null);
        fd.setDirectory("C:\\");
        fd.setFile("*.mp4;*.mpg");
        fd.setVisible(true);
        filename = fd.getFile();
        directory = fd.getDirectory();
        if (filename == null) {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText( "add movie canceled");
                alert.showAndWait();
        } else {
            fileTextField.setText("movies/" + filename);
        }
    }

    /**
     * Closes the stage.
     * @param event 
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }
    
}
