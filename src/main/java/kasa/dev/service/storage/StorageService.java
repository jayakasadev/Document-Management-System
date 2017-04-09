package kasa.dev.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Interface to abstract away implementation details of storage service.
 */
public interface StorageService {

    /**
     * Method to create root directory
     *
     * @throws IOException
     */
    void init() throws IOException;

    /**
     * Method to store file
     *
     * @param filename
     * @param file
     */
    void store(String filename, MultipartFile file);

    /**
     * Method to get all files
     *
     * @return Stream of Path objects
     * @throws IOException
     */
    Stream<Path> loadAll() throws IOException;

    /**
     * Method to load file
     *
     * @param filename
     * @return Path object for file
     */
    Path load(String filename);

    /**
     * Method to load a file into memory
     *
     * @param filename
     * @return Resource object containing file data
     */
    Resource loadAsResource(String filename);

    /**
     * Method to delete all files in storage root directory only
     */
    void deleteAll();
}
