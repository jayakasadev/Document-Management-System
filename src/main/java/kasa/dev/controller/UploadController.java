package kasa.dev.controller;

import kasa.dev.model.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    /**
     * Required if I want the UploadRespository to be autowired.
     *
     * @param repository
     */
    public UploadController(UploadRepository repository){
        this.repository = repository;
    }

    @RequestMapping(method = GET, produces = "application/json")
    public String greeting(){
        log.info("greeting has been accessed");
        return "Hello There! Welcome to the Upload Management System.\n\tBuilt By Jaya Kasa.";
    }

    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    public Collection<Upload> findAll(){
        log.info("findAll");
        return repository.findAll();
    }

    // TODO change this to POST when you upload the file
    // TODO get rid of the filename path variable when you upload the file
    @RequestMapping(value = "/upload/{owner}/{description}/{filename}", method = GET, produces = "application/json")
    public Upload upload(@PathVariable(value = "owner") String owner,
                         @PathVariable(value = "description") String description,
                         @PathVariable(value = "filename") String filename){
        log.info("uploading file owned by " + owner +" description: " + description);
        Upload out = new Upload(filename, owner, "ext", description);
        log.info("uploaded: " + out);
        repository.save(out);
        return out;
    }

    @RequestMapping(value = "/details/{id}", method = GET, produces = "application/json")
    public Optional<Upload> findByID(@PathVariable(value = "id") long id){
        log.info("findByID " + repository != null);
        return repository.findByFileId(id);
    }

    @RequestMapping(value = "/details/{filename}", method = GET, produces = "application/json")
    public Optional<Upload> findByFilename(@PathVariable(value = "filename") String filename){
        log.info("findByFilename " + repository != null);
        return repository.findByFilename(filename);
    }
}
