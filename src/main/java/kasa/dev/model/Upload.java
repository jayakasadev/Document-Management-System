package kasa.dev.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class represents the Object Model of an uploaded file
 */
@Data // lombok
@Entity
public class Upload {
    @Id
    @GeneratedValue
    private Long fileId;

    private String filename;
    private String owner;
    private String extension;
    private String description;

    private Timestamp dateUploaded;
    private Timestamp lastAccessed;

    /**
     * Upload Constructor
     * For use to create instances of Upload to be saved in the database
     *
     * @param filename
     * @param owner
     * @param extension
     * @param description
     */
    public Upload(String filename, String owner, String extension, String description){
        this.filename = filename;
        this.extension = extension;
        this.description = description;
        this.owner = owner;

        this.dateUploaded = Timestamp.valueOf(LocalDateTime.now());
        this.lastAccessed = dateUploaded;
    }

    // this default constructor is created for the sake of JPA, will not be used directly
    protected Upload(){}

    public void updateLastAccessed(){
        this.lastAccessed = Timestamp.valueOf(LocalDateTime.now());
    }
}
