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
public interface CategoryFacade {
    /**
     * List over all categories in database
     * @return list of categories
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public List<Category> getAllCategories() throws PmcDalException;
    
     /**
     * Create a new category in the database
     * @param category
     * @return boolean
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public Category createCategory(Category category) throws PmcDalException;
    
    /**
     * Updates the database
     * @param category
     * @return boolean
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public boolean updateCategory(Category category) throws PmcDalException;
    
    /**
     * Deletes a category from the database
     * @param category
     * @return boolean
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public boolean deleteCategory(Category category) throws PmcDalException;
    
    /**
     * 
     * @param category
     * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
     public List<Movie> getAllMoviesInCategory(Category category) throws PmcDalException;
     
     /**
      * 
      * @param category
      * @param movie
      * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
      */
     public boolean addToCategory(Category category, Movie movie) throws PmcDalException;
     
     /**
      * 
      * @param category
      * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
      */
      public boolean clearCategory(Category category) throws PmcDalException;
      
      /**
       * 
       * @param category
       * @param movie
       * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
       */
      public boolean clearMovieFromCategory(Category category, Movie movie) throws PmcDalException;
}
