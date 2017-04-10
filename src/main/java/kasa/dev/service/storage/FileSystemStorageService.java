package kasa.dev.service.storage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path root;
    private final Log log;

    /**
     * Constructor to get the service started
     */
    @Autowired
    public FileSystemStorageService() {
        root = Paths.get("uploads");
        log = LogFactory.getLog(getClass());
        log.info("All files to be stored in: " + root.toString());
    }

    @Override
    public void init() throws IOException {
        log.info("init");
        Files.createDirectory(root);
    }

    @Override
    public void store(String filename, MultipartFile file) {
        log.info("store : " + root.toString()+ "\\" + file.getOriginalFilename());
        if(file.isEmpty()) throw new StorageServiceException("Failed to store empty file: " + file.getName());
        if(Files.exists(Paths.get(root.toString()+ "\\" + file.getOriginalFilename()))) throw new StorageServiceException("File Already Exists");
        try {
            Files.copy(file.getInputStream(), root.resolve(filename));
        } catch (IOException e) {
            throw new StorageServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() throws IOException {
        log.info("loadAll");
        return Files.walk(root).filter(p -> !p.equals(root)).map(p -> root.relativize(p));
    }

    @Override
    public Path load(String filename) {
        log.info("load");
        return root.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename){
        log.info("loadAsResource");
        Path path = root.resolve(filename);
        // log.info(path.toUri());
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            // log.info(resource.getFilename());
        } catch (MalformedURLException e) {
            throw new StorageServiceException("Could not read file " + filename, e);
        }
        if(resource.exists() || resource.isReadable())
            return resource;
        else
            throw new StorageServiceException("Could not read file " + filename +
                    " exists: " + resource.exists() + " isreadable: " + resource.isReadable());
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
