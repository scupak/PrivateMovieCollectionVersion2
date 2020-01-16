/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privateMovieCollection.be.Category;
import privateMovieCollection.be.Movie;
import privateMovieCollection.bll.CategoryManager;
import privateMovieCollection.bll.VideoPlayer;
import privateMovieCollection.bll.MovieManager;
import privateMovieCollection.dal.PmcDalException;

/**
 *  Appmodel lets the GUI layer perform operations via the bll layer. 
 * @author andreasvillumsen
 */
public class AppModel {
    private VideoPlayer videoPlayer;
    private MovieManager movieManager;
    private CategoryManager categoryManager;
    private AppController appController;
    private ObservableList<Movie> allMovies;
    private ObservableList<Category> allCategories;
    private ObservableList<Movie> moviesInCategory;
    

    /**
     * AppModel constructor
     * @throws java.lang.Exception
     */
    public AppModel() throws Exception {
        videoPlayer = new VideoPlayer();
        
        movieManager = new MovieManager();
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(movieManager.getAllMovies());
        
        categoryManager = new CategoryManager();
        allCategories = FXCollections.observableArrayList();
        allCategories.addAll(categoryManager.getAllCategories());
        
        moviesInCategory = FXCollections.observableArrayList();
        
        if (!categoryManager.getAllCategories().isEmpty()) {
            moviesInCategory.addAll(categoryManager.getAllMoviesinCategory(categoryManager.getAllCategories().get(0)));
        }
    }
    
    /**
     * clears the list of movies then adds them all back by calling moviemanager.getAllMovies. 
     * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public ObservableList<Movie> getAllMovies() throws PmcDalException {
        allMovies.clear();
        allMovies.addAll(movieManager.getAllMovies());
        return allMovies;
    }
    
   /**
    * gives you acces to the videoplayer classes methods.
    * @return 
    */
    public VideoPlayer getVideoPlayer() {
        return videoPlayer;
    }
    
    /**
     * Creates a new movie by calling moviemanager
     * @param movieToAdd 
     * @return the created movie
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public Movie createMovie(Movie movieToAdd) throws PmcDalException {
        Movie movie;
        movie = movieManager.createMovie(movieToAdd);
        movieClearAdd();
        
        return movie;
    }
    /**
     * Deletes a movie by calling moviemanager
     * @param movie 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void deleteMovie(Movie movie) throws PmcDalException {
        movieManager.deleteMovie(movie);
        movieClearAdd();
    }
    /**
     * Updates a movie by calling moviemanager. 
     * @param movie 
     * @return result
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public boolean updateMovie(Movie movie) throws PmcDalException {
        boolean result;
        result =  movieManager.updateMovie(movie);
        movieClearAdd();
        
        return result;
    }
    
    /**
     * Refreshes the list of movies by clearing it then adding all the movies from the database via movieManger. 
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public void movieClearAdd() throws PmcDalException {
        allMovies.clear();
        allMovies.addAll(movieManager.getAllMovies());
    }
    
    /**
     * Refreshes the list of categories then adds movies back from the database. 
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public void categoriesClearAdd() throws PmcDalException {
        allCategories.clear();
        allCategories.addAll(categoryManager.getAllCategories());
    }
    
    /**
     * Gives you acces to a list of all categories. 
     * @return  allCategories
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public ObservableList<Category> getAllCategories() throws PmcDalException {
        allCategories.clear();
        allCategories.addAll(categoryManager.getAllCategories());
        return allCategories;
    }
    
    /**
     * Gives you acces to a list of movies that are in the specified category.
     * @param category
     * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public ObservableList<Movie>getAllMoviesInCategory(Category category) throws PmcDalException {
        moviesInCategoriesClearAdd(category);
        return moviesInCategory;
    }
    
    /**
     * Adds a specified movie to a specified category. 
     * @param category
     * @param movie 
     * @return  
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public boolean addToCategory(Category category, Movie movie)throws PmcDalException {
        boolean result = categoryManager.addToCategory(category, movie);
        moviesInCategoriesClearAdd(category);
        allCategories.clear();
        allCategories.addAll(categoryManager.getAllCategories());
        
        return result;
    }
    
    /**
     * Refreshes the list of movies in a speciefied category. 
     * @param category 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void moviesInCategoriesClearAdd(Category category) throws PmcDalException {
        moviesInCategory.clear();
        moviesInCategory.addAll(categoryManager.getAllMoviesinCategory(category));
    }
    
    /**
     * Creates a category. 
     * @param category 
     */
    void createCategory(Category category) throws PmcDalException {
        categoryManager.createCategory(category);
        categoriesClearAdd();
    }
    
    /**
     * Updates a category.
     * @param category 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void updateCategory(Category category) throws PmcDalException {
        categoryManager.updateCategory(category);
        categoriesClearAdd();
    }
    
    /**
     * Deletes a movie from a category. 
     * @param category
     * @param movie
     * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public boolean clearMovieFromCategory(Category category, Movie movie) throws PmcDalException {
        boolean result = categoryManager.clearMovieFromCategory(category, movie);
        moviesInCategoriesClearAdd(category);
        categoriesClearAdd();
      
        return result;
    }
    
    /**
     * Searches the list of all movies.Checks if any input was given then calls the moviemanager.search function based on the input.  
     * @param titleQuery
     * @param filterQuery
     * @param ratingQuery 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void search(String titleQuery, ArrayList<String> filterQuery, int ratingQuery) throws PmcDalException {
        if (filterQuery.get(0).isEmpty() && ratingQuery == 0 && titleQuery.isEmpty()) {
            System.out.println("its empty");
            allMovies.clear();
            allMovies.addAll(movieManager.getAllMovies());
        } else {
            System.out.println(filterQuery.size());
            allMovies.clear();
            allMovies.addAll(movieManager.search(titleQuery, filterQuery, ratingQuery));
        }
    }
    
    /**
     * Gives acces to a list of movies for deletion. 
     * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public List<Movie> moviesToDelete() throws PmcDalException {
        return movieManager.moviesToDelete();
    }
    
    /**
     * Deletes a category.
     * @param category 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void deleteCategory(Category category) throws PmcDalException{
        categoryManager.deleteCategory(category);
        categoriesClearAdd();
        movieClearAdd();
    }
}
