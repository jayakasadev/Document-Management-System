package kasa.dev.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

/**
 * UploadRepository interface represents the entry point into the database.
 * All database related queries and operations will go through this interface.
 *
 */
public interface UploadRepository extends CrudRepository<Upload, Long> {

    /**
     * Method for finding files by filename
     *
     * @param filename
     * @return Optional containing Upload if it exists
     */
    Optional<Upload> findByFilename(String filename);

    /**
     * Method for finding files by filename
     *
     * @param id
     * @return Optional containing Upload if it exists
     */
    Optional<Upload> findByFileId(Long id);

    /**
     * Method for searching for files based on upload date
     *
     * @param timestamp
     * @return Collection of Uploads
     */
    Collection<Upload> findByDateUploadedAfter(Timestamp timestamp);

    Collection<Upload> findAll();
}
