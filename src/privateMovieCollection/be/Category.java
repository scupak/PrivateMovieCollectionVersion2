
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.be;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author andreasvillumsen
 */
public class Category {
    private int id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty movies;
    
    /**
     * Movie constructor
     * @param id
     * @param name
     */
    public Category(int id, String name , int movies) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.movies = new SimpleIntegerProperty(movies);
    }
    
    /**
     * Get the id of the category
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the category
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Get the name of the category
     *
     * @return name
     */
    public String getName() {
        return name.get().trim();
    }

    /**
     * Set the name of the category
     *
     * @param name
     */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * Get movies
     * 
     * @return movies
     */
     public int getMovies() {
        return movies.get();
    }

    /**
     * Set the title of the song
     *
     * @param movies
     */
    public void setMovies(int movies) {
        this.movies.set(movies);
    }

    /**
     * Class as string
     * 
     * @return string
     */
    @Override
    public String toString() {
        return name.get().trim();
    }
    
}
