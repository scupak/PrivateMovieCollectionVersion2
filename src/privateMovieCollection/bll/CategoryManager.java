/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.bll;

import java.io.IOException;
import java.util.List;
import privateMovieCollection.be.Category;
import privateMovieCollection.be.Movie;
import privateMovieCollection.dal.CategoryFacade;
import privateMovieCollection.dal.PmcDalException;
import privateMovieCollection.dal.database.CategoryDBDAO;

/**
 *
 * @author anton
 */
public class CategoryManager {
    
    private CategoryFacade CategoryDBDAO;
    
    /**
     * Category Manager Constructor
     * @throws java.io.IOException
     */
    public CategoryManager() throws IOException{
        CategoryDBDAO = new CategoryDBDAO();
    }
    
    /**
     * Get a list of categories
     * 
     * @return categories
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public List<Category> getAllCategories() throws PmcDalException{
        List<Category> categories = CategoryDBDAO.getAllCategories();
        
        for (Category category : categories) {
            category.setMovies(CategoryDBDAO.getAllMoviesInCategory(category).size());
        }
                
        return categories;
    }
    
    /**
     * Create a category
     * 
     * @param category
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public void createCategory(Category category) throws PmcDalException{
        CategoryDBDAO.createCategory(category);
    }
    
    /**
     * Delete a category
     * 
     * @param category 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void deleteCategory(Category category)throws PmcDalException{
        CategoryDBDAO.deleteCategory(category);
    }
    
    /**
     * Update a category
     * 
     * @param category 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void updateCategory(Category category)throws PmcDalException   {
        CategoryDBDAO.updateCategory(category);
    }
    
    /**
     * Get all movies in a category
     * 
     * @param category
     * @return movies
     * @throws privateMovieCollection.dal.PmcDalException
     */
    public List<Movie> getAllMoviesinCategory(Category category) throws PmcDalException{
        return CategoryDBDAO.getAllMoviesInCategory(category);
    }
    /**
     * Add a movie to a category
     * 
     * @param category
     * @param movie 
     * @return  
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public boolean addToCategory(Category category, Movie movie)throws PmcDalException{
        for (Movie movie1 : getAllMoviesinCategory(category)) {
            if(movie1.getTitle().equals(movie.getTitle())){
                return false;
            }
        }
        
       return CategoryDBDAO.addToCategory(category, movie);
    }
    
    /**
     * Clear all movies from a category
     * 
     * @param category 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public void clearCategory(Category category)throws PmcDalException{
        CategoryDBDAO.clearCategory(category);
    }
    
    /**
     * Remove a movie from a category
     * 
     * @param category
     * @param movie
     * @return 
     * @throws privateMovieCollection.dal.PmcDalException 
     */
    public boolean clearMovieFromPlayList(Category category, Movie movie)throws PmcDalException{
        return CategoryDBDAO.clearMovieFromCategory(category, movie);
    }
    
}
