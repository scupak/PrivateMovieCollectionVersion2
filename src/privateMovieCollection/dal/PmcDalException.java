/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.dal;

/**
 *
 * @author anton
 */
public class PmcDalException extends Exception{
    
    public PmcDalException() {
        super();
    }

    public PmcDalException(String message) {
        super(message);
    }

    public PmcDalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PmcDalException(String message, Exception ex) {
        super(message, ex);
    }
}
