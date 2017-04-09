package kasa.dev.controller;

import kasa.dev.model.Upload;
import kasa.dev.model.UploadRepository;
import kasa.dev.service.storage.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Entry point into the web service.
 *
 * Using @RestController, so no need to autowire anything.
 */
@RestController
@RequestMapping(value = "")
public class UploadController {
    private final Log log = LogFactory.getLog(UploadController.class);
    private final UploadRepository repository;
    private final StorageService service;

    /**
     * Required if I want the UploadRespository to be autowired.
     *
     * @param repository
     * @param service
     */
    public UploadController(UploadRepository repository, StorageService service){
        this.repository = repository;
        this.service = service;
    }

    /**
     * Landing Page/ Welcome Message
     *
     * @return String greeting
     */
    @RequestMapping(method = GET, produces = "application/json")
    public String greeting(){
        log.info("greeting has been accessed");
        return "Hello There! Welcome to the Upload Management System.\n\tBuilt By Jaya Kasa.";
    }

    /**
     * Method for getting a list of all files
     *
     * @return Collection containing record of all uploads
     */
    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    public Collection<Upload> findAll(){
        log.info("findAll");
        return repository.findAll();
    }

    /**
     * Method for uploading a file
     *
     * @param owner
     * @param description
     * @param filename
     * @param file
     * @return Upload object
     */
    @RequestMapping(value = "/upload/{owner}/{description}", method = POST, produces = "application/json")
    public Upload upload(@PathVariable(value = "owner") String owner,
                         @PathVariable(value = "description") String description,
                         @PathVariable(value = "filename") String filename,
                         @RequestParam MultipartFile file){
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        // log.info("uploading file owned by " + owner +" description: " + description);
        log.info("uploading " + file.getOriginalFilename() + " to uploads directory");

        service.store(file.getOriginalFilename(), file);

        return repository.save(new Upload(file.getOriginalFilename(), owner, extension, description));
    }

    /**
     * Method for searching for file information by fileid
     *
     * @param id
     * @return Upload object if it exists
     */
    @RequestMapping(value = "/details/{id}", method = GET, produces = "application/json")
    public Optional<Upload> findByID(@PathVariable(value = "id") long id){
        log.info("findByID " + id);
        return repository.findByFileId(id);
    }

    /**
     * Method for searching for file information by filename.
     *
     * @param filename
     * @return Upload object if it exists
     */
    @RequestMapping(value = "/details/{filename:.*}", method = GET, produces = "application/json")
    public Optional<Upload> findByFilename(@PathVariable(value = "filename") String filename){
        log.info("findByFilename " + filename);
        return repository.findByFilename(filename);
    }

    /**
     * Method for getting all files uploaded in the last hour
     *
     * @return Collection containing Upload objects
     */
    @RequestMapping(value = "/lasthour", method = GET, produces = "application/json")
    public Collection<Upload> uploadedLastHour(){
        log.info("uploadedLastHour now: " + LocalDateTime.now() + " hour ago: " + LocalDateTime.now().minusHours(1));
        return repository.findByDateUploadedAfter(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));
    }

    /**
     * Method for getting file stream by fileid
     *
     * @param id
     * @return InputStream if file exists
     */
    @RequestMapping(value = "/stream/{id}", method = GET)
    public Resource<?> streamByID(@PathVariable(value = "id") long id){
        log.info("streamByID " + id);
        Upload out = repository.findByFileId(id).get();

        if(out == null){
            return new Resource<>(HttpStatus.NOT_FOUND);
        }
        InputStreamSource source = service.loadAsResource(out.getFilename());

        return new Resource<>(source);
    }

    /**
     * Method for getting file stream by fileid
     *
     * @param filename
     * @return InputStream if file exists
     */
    @RequestMapping(value = "/stream/{filename:.*}", method = GET, produces = "application/json")
    public Resource<?> streamByFilename(@PathVariable(value = "filename") String filename){
        log.info("streamByFilename " + filename);
        Upload out = repository.findByFilename(filename).get();

        if(out == null){
            return new Resource<>(HttpStatus.NOT_FOUND);
        }
        InputStreamSource source = service.loadAsResource(out.getFilename());

        return new Resource<>(source);
    }
}
