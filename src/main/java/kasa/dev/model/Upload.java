package kasa.dev.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class represents the Object Model of an uploaded file
 */
@Data
public class Upload {
    private Long fileId;

    private String filename;
    private String owner;
    private String extension;
    private String description;

    private Timestamp dateUploaded;
    private Timestamp lastAccessed;

    /**
     * Upload Constructor
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

    public void updateLastAccessed(){
        this.lastAccessed = Timestamp.valueOf(LocalDateTime.now());
    }
}
