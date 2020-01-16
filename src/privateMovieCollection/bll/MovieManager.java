/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.bll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import privateMovieCollection.be.Category;
import privateMovieCollection.be.Movie;
import privateMovieCollection.dal.MovieFacade;
import privateMovieCollection.dal.PmcDalException;
import privateMovieCollection.dal.database.MovieDBDAO;

/**
 *
 * @author anton
 * @author kacpe
 */
public class MovieManager {
    
    private MovieFacade movieDBDAO;

    /**
     * Movie Manager Constructor
     * @throws java.io.IOException
     */
    public MovieManager() throws IOException {
        movieDBDAO = new MovieDBDAO();
    }
    
    /**
     * Get the movies and assign their categories
     * 
     * @return list of movies
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public List<Movie>getAllMovies() throws PmcDalException {
        List<Movie> result = movieDBDAO.getAllMovies();
        
        for (Movie movie : result) {
            if(!movieDBDAO.GetAllCategoriesWithMovie(movie).isEmpty()) {
                movie.getCategoryArray().addAll(movieDBDAO.GetAllCategoriesWithMovie(movie));
            }
        }
        
        return result;
    }
    
    /**
     * Search in movies
     * 
     * @param titleQuery
     * @param filterQuery
     * @param ratingQuery
     * @return movies
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public List<Movie> search(String titleQuery, ArrayList<String> filterQuery, int ratingQuery) throws PmcDalException {
        List<Movie> searchBase = getAllMovies();
        List<Movie> titleResult = new ArrayList<>();
        List<Movie> filterResult = new ArrayList<>();
        List<Movie> finalResult = new ArrayList<>();
        
        if (titleQuery == null) {
            titleResult.addAll(searchBase);
        } else {
            for (Movie movie : searchBase) {
                if (movie.getTitle().toLowerCase().contains(titleQuery.toLowerCase())) {
                    titleResult.add(movie);
                }
            }
        }
        
        if (filterQuery.get(0).isEmpty()) {
            filterResult.addAll(titleResult);
        } else {
            for (Movie movie : titleResult) {
                boolean containsCategory = false;
                for (String string : filterQuery) {
                    for (Category category : movie.getCategoryArray()) {
                        if (category.getName().toLowerCase().contains(string.toLowerCase())) {
                            containsCategory = true;
                        }
                    }
                }
                if(containsCategory) {
                    filterResult.add(movie);
                }
            }
        }
        
        if (ratingQuery == 0) {
            finalResult.addAll(filterResult);
        } else {
            for (Movie movie : filterResult) {
                if (movie.getRating() >= ratingQuery ) {
                    finalResult.add(movie);
                }
            }
        }
        
        return finalResult;
    }
    public List<Movie> moviesToDelete() throws PmcDalException{
        final int daysin2years = 730;
        Date currentdate = new Date();
        List<Movie> finalResult = new ArrayList<>();

         for (Movie movie : getAllMovies()) {
             boolean fordeletion = false;
             
             if (movie.getRating() < 60 ) {
                 fordeletion = true;
             }

             long diff = currentdate.getTime() - movie.getLastview().getTime();
             

             if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= daysin2years){
                 fordeletion = true;
             }

             if(fordeletion){
                 finalResult.add(movie);
             }

         }
         
        return finalResult;
    }
     
     
     
    /**
     * Pass a movie to be created.
     * 
     * @param movie 
     * @return  
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public Movie createMovie(Movie movie) throws PmcDalException {
       return movieDBDAO.createMovie(movie) ;
    }
    
    /**
     * Delete a movie from the database
     * 
     * @param movie 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void deleteMovie(Movie movie) throws PmcDalException {
        movieDBDAO.deleteMovie(movie);
    }
    
    /**
     * Update a movie
     * 
     * @param movie 
     * @return  
     * @throws privateMovieCollection.dal.PmcDalException  
     */
    public boolean updateMovie(Movie movie) throws PmcDalException {
        return movieDBDAO.updateMovie(movie);
    }
    
  
    
}
