/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.dal;

import java.util.List;
import privateMovieCollection.be.Category;
import privateMovieCollection.be.Movie;

/**
 *
 * @author andreasvillumsen
 */
public interface MovieFacade {
    /**
     * List over all movies in database
     * @return list of movies
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public List<Movie> getAllMovies() throws PmcDalException;
    
     /**
     * Create a new movie in the database
     * @param movie
     * @return boolean
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public Movie createMovie(Movie movie) throws PmcDalException;
    
    /**
     * Updates the database
     * @param movie
     * @return boolean
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public boolean updateMovie(Movie movie) throws PmcDalException;
    
    /**
     * Deletes a movie from the database
     * @param movie
     * @return boolean
     * @throws privateMovieCollection.dal.PmcDalException
     */
   public boolean deleteMovie(Movie movie) throws PmcDalException;
   
   /**
    * 
    * @param movie
    * @return list of categories that include a specifc movie
     * @throws privateMovieCollection.dal.PmcDalException 
    */
    public List<Category>GetAllCategoriesWithMovie(Movie movie) throws PmcDalException;
}
