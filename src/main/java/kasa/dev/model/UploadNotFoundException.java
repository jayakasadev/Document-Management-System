package kasa.dev.model;

/**
 * Class defines a runtime exception for the Upload Class
 */
public class UploadNotFoundException extends RuntimeException{

    /**
     * Main Constructor for UploadNotFoundException
     *
     * @param filename
     */
    public UploadNotFoundException(String filename){
        super(filename);
    }
}
