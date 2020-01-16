/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import privateMovieCollection.be.Category;
import privateMovieCollection.be.Movie;
import privateMovieCollection.dal.PmcDalException;
import privateMovieCollection.gui.AppModel;

/**
 *
 * @author andreasvillumsen
 */
public class AppController implements Initializable {

    
    enum ListSelection {
        MOVIES, MOVIESINCATEGORY, CATEGORY,
    }
    
    ListSelection listSelection = ListSelection.MOVIES;
    private AppModel appModel;
    private ArrayList<String> filterQuery;
    private Category currentlySelectedCategory;
    
    @FXML
    private Button newMoiveButton;
    @FXML
    private Button play;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Movie> moviesInCategory;
    @FXML
    private TextField searchField;
    @FXML
    private Button editMovieButton;
    @FXML
    private Button deleteSongButton;
    @FXML
    private Button exitButton;
    @FXML
    private TableView<Movie> movieList;
    @FXML
    private TableView<Category> categoryList;
    @FXML
    private TableColumn<Movie, String> movieTitelCol;
    @FXML
    private TableColumn<Movie, String> movieCategoryCol;
    @FXML
    private TableColumn<Movie, String> movieRaitingCol;
    @FXML
    private TableColumn<Category,  String> categoryNameCol;
    @FXML
    private TableColumn<Category, Integer> moviesInCategoryCol;
    @FXML
    private TableColumn<Movie, String> movieLastViewCol;
    @FXML
    private Button moveToCategoryButton;
    @FXML
    private Button newCategoryButton;
    @FXML
    private Button editCategoryButton;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private TextField filterField;
    @FXML
    private Slider minimumRatingSlider;
    @FXML
    private Label minimumRatingLabel;
    @FXML
    private Button deleteFromCategory;
  

    /**
     * Initialize
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movieCategoryCol.setCellValueFactory( 
            new PropertyValueFactory<Movie, String>("categories")
        );
        
        movieTitelCol.setCellValueFactory( 
                new PropertyValueFactory<Movie, String>("title")
        );
        
        movieLastViewCol.setCellValueFactory( 
                new PropertyValueFactory<Movie, String>("lastviewText")
        );
         
        movieRaitingCol.setCellValueFactory( 
                new PropertyValueFactory<Movie, String>("rating")
        );
         
        categoryNameCol.setCellValueFactory( 
                new PropertyValueFactory<Category, String>("name")
        ); 
        
        moviesInCategoryCol.setCellValueFactory( 
                new PropertyValueFactory<Category, Integer>("movies")
        ); 
         
         
        try {
            appModel = new AppModel();

            minimumRatingSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    minimumRatingLabel.setText(Math.round(minimumRatingSlider.getValue())+"");
                }
            });
        
            movieList.setItems(appModel.getAllMovies());
            categoryList.setItems(appModel.getAllCategories());
            moviesInCategory.setItems(appModel.getAllMovies());
            
        } catch (Exception ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            if(!appModel.moviesToDelete().isEmpty()){
               
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("You should take a look at your old movies");
                alert.setHeaderText("Please remove unwanted movies");
                alert.setContentText("Remember to delete movies, that have a personal rating under 6 "
                        + "and have not been opened from the application in more than 2 years."
                        + "\n movies pending deletion:"
                        + "\n" + appModel.moviesToDelete());

                alert.showAndWait();
            }
        } catch (PmcDalException ex) {
             Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("eror");
                alert.setHeaderText("eror");
                alert.setContentText(ex.toString());

                alert.showAndWait();
        }
    }
    
   /**
    * Calls the search funtion
    * @param event 
    */ 
    @FXML
    private void clickedsearchButton(ActionEvent event) {
        search();
    }
    
    /**
     * Sets listselction to MOVIESINCATEGORY
     * @param event 
     */
    @FXML
    private void clickedOnMovieInCategory(MouseEvent event) {
        listSelection = listSelection.MOVIESINCATEGORY;
    }
    
    /**
     * Sets the listSelection to MOVIES
     * @param event 
     */
    @FXML
    private void clickedOnMoive(MouseEvent event) {
        listSelection = ListSelection.MOVIES;
    }

    /**
     * Play the selected movie and updates lastview
     * @param event
     * @throws IOException 
     */
    @FXML
    private void play(ActionEvent event) throws IOException {
        Date lastview = new Date();
        
        if(listSelection == listSelection.MOVIES) {
            try {
                appModel.getVideoPlayer().playVideo(movieList.getSelectionModel().getSelectedItem().getPath());
                movieList.getSelectionModel().getSelectedItem().setLastview(lastview);
                appModel.updateMovie(movieList.getSelectionModel().getSelectedItem());
            } catch (PmcDalException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("eror");
                alert.setHeaderText("eror");
                alert.setContentText(ex.toString());

                alert.showAndWait();
            }
        } else if(listSelection == listSelection.MOVIESINCATEGORY) {
            try {
                appModel.getVideoPlayer().playVideo(moviesInCategory.getSelectionModel().getSelectedItem().getPath());
                Movie movieUpdate = moviesInCategory.getSelectionModel().getSelectedItem();
                movieUpdate.setLastview(lastview);
                appModel.updateMovie(movieUpdate);
            } catch (PmcDalException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("eror");
                alert.setHeaderText("eror");
                alert.setContentText(ex.toString());

                alert.showAndWait();
            }
        }
    }

    /**
     * Opens a new menu to create a new movie
     * @param event
     * @throws IOException 
     */
    @FXML
    private void newMovie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = (Parent) fxmlLoader.load(getClass().getResource("NewMovie.fxml").openStream());
        NewMovieController cont = (NewMovieController) fxmlLoader.getController();
        cont.setAppModel(appModel);
        Stage stage = new Stage();
        stage.setTitle("New/Edit Movie");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Opens a new menu to edit a already exsting movie
     * @param event
     * @throws IOException 
     */
    @FXML
    private void editMoive(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = (Parent) fxmlLoader.load(getClass().getResource("EditMovie.fxml").openStream());
        EditMovieController cont = (EditMovieController) fxmlLoader.getController();
        cont.setAppModel(appModel);
        cont.setMovie(movieList.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setTitle("New/Edit Movie");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
        
    }

    /**
     * Gives the user a pop-up asking if they want to delete the selcted movie
     * @param event
     * @throws IOException 
     */
    @FXML
    private void deleteMovie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = (Parent) fxmlLoader.load(getClass().getResource("DeleteMovie.fxml").openStream());
        DeleteMovieController cont = (DeleteMovieController) fxmlLoader.getController();
        cont.setAppModel(appModel);
        cont.setMovie(movieList.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setTitle("New/Edit Movie");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Closes the program
     * @param event 
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Shows the movies in a selected category in the moviesInCategory listview
     * @param event 
     */
    @FXML
    private void updateCategoryView(MouseEvent event) {
        if(categoryList.getSelectionModel().getSelectedItem() != null) {
            try {
                currentlySelectedCategory = categoryList.getSelectionModel().getSelectedItem();
                listSelection  = ListSelection.CATEGORY;
                moviesInCategory.setItems(appModel.getAllMoviesInCategory(categoryList.getSelectionModel().getSelectedItem()));
            } catch (PmcDalException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString());

                alert.showAndWait();
            }
        }
    }

    /**
     * Adds a chosen movie to a selcted category
     * @param event 
     */
    @FXML
    private void moveToCategory(ActionEvent event) {
        try {
            Movie currentlySelectedMovie = movieList.getSelectionModel().getSelectedItem();
            if (appModel.addToCategory(currentlySelectedCategory, currentlySelectedMovie) == false){
                throw new NullPointerException();
            }
            
            appModel.moviesInCategoriesClearAdd(currentlySelectedCategory);
            appModel.categoriesClearAdd();
            appModel.movieClearAdd();
           
        } catch (PmcDalException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString());

                alert.showAndWait();
             
        } catch(NullPointerException exception){
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(exception.toString() + " or you are adding a movie to a category twice");

                alert.showAndWait();
        }
    }

    /**
     * Opens a new window to add a new category
     * @param event
     * @throws IOException 
     */
    @FXML
    private void newCategory(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = (Parent) fxmlLoader.load(getClass().getResource("NewCategory.fxml").openStream());
        NewCategoryController cont = (NewCategoryController) fxmlLoader.getController();
        cont.setAppModel(appModel);
        Stage stage = new Stage();
        stage.setTitle("New/Edit Category");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Opens a window to edit an exsiting category
     * @param event
     * @throws IOException 
     */
    @FXML
    private void editCategory(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = (Parent) fxmlLoader.load(getClass().getResource("EditCategory.fxml").openStream());
        EditCategoryController cont = (EditCategoryController) fxmlLoader.getController();
        cont.setAppModel(appModel);
        cont.setCategory(currentlySelectedCategory);
        Stage stage = new Stage();
        stage.setTitle("New/Edit Category");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Gives the user a pop-up asking if they want to delete a selcted category
     * @param event
     * @throws IOException 
     */
    @FXML
    private void deleteCategory(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = (Parent) fxmlLoader.load(getClass().getResource("DeleteCategory.fxml").openStream());
        DeleteCategoryController cont = (DeleteCategoryController) fxmlLoader.getController();
        cont.setAppModel(appModel);
        cont.setCategory(currentlySelectedCategory);
        Stage stage = new Stage();
        stage.setTitle("New/Edit Movie");
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

   
    /**
     * Handels the search function for the program, making it poisblie
     * to filter between titel, raiting and categories at the same time
     */
    private void search() {
        try {
            String titleQuery = searchField.getText().trim();
            String CSV = filterField.getText();
            String[] values = CSV.split(",");
            filterQuery = new ArrayList(Arrays.asList(values));
            
            System.out.println(filterQuery);
            int ratingQuery = (int) Math.round(minimumRatingSlider.getValue());
            appModel.search(titleQuery, filterQuery, ratingQuery);
        } catch (PmcDalException ex) {
              Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString());

                alert.showAndWait();
        }
    }
    
    /**
     * Deletes a song from a category
     * @param event 
     */
    @FXML
    private void deleteFromCategory(ActionEvent event) {
        try {
            Movie currentlySelectedMovieInCategory = moviesInCategory.getSelectionModel().getSelectedItem();
            
            appModel.clearMovieFromCategory(currentlySelectedCategory, currentlySelectedMovieInCategory);
            appModel.moviesInCategoriesClearAdd(currentlySelectedCategory);
            appModel.categoriesClearAdd();
            appModel.movieClearAdd();
        } catch (PmcDalException ex) {
              Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("error");
                alert.setContentText(ex.toString());

                alert.showAndWait();
        }
    }

}
