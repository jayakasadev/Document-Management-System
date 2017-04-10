package kasa.dev.controller;

import kasa.dev.model.Upload;
import kasa.dev.model.UploadRepository;
import kasa.dev.service.storage.StorageService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

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
    public Resource<String> greeting(){
        log.info("greeting has been accessed");
        return new Resource<>("Hello There! Welcome to the Upload Management System.\n\tBuilt By Jaya Kasa.");
    }

    /**
     * Method for getting a list of all files
     *
     * @return Collection containing record of all uploads
     */
    @RequestMapping(value = "/all", method = GET)
    public Resources<Upload> findAll(){
        log.info("findAll");
        Collection<Upload> out = repository.findAll();
        return new Resources<>(out);
    }

    /**
     * Method for uploading a file
     *
     * @param owner
     * @param description
     * @param file
     * @return Upload object
     */
    @RequestMapping(value = "/upload/{owner}/{description}", method = POST)
    public Resource<Upload> upload(@PathVariable("owner") String owner,
                         @PathVariable("description") String description,
                         @RequestParam MultipartFile file){

        log.info("uploading file owned by " + owner +" description: " + description);

        // log.info("uploading file owned by " + owner +" description: " + description);
        // log.info("uploading " + file.getOriginalFilename() + " to uploads directory");
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

        service.store(file.getOriginalFilename(), file);

        Upload out = repository.save(new Upload(file.getOriginalFilename(), owner, extension, description));

        // log.info("EXISTS: " + repository.findByFilename(file.getOriginalFilename()).toString());

        return new Resource<>(out);
    }

    /**
     * Method for searching for file information by fileid
     *
     * @param id
     * @return Upload object, else HttpStatus.NotFound
     */
    @RequestMapping(value = "/details/{id}", method = GET)
    public Resource<?> findByID(@PathVariable(value = "id") long id){
        log.info("findByID " + id);
        Upload out = repository.findByFileId(id).orElse(null);
        if (out == null) return new Resource<>(HttpStatus.NOT_FOUND);
        return new Resource<>(out);
    }

    /**
     * Method to get file details by filename
     *
     * @param filename
     * @return Upload object, else HttpStatus.NotFound
     */
    @RequestMapping(value = "/details/filename/{filename}/", method = GET)
    public Resource<?> findByFilename(@PathVariable(value = "filename") String filename){
        log.info("findByFilename: " + filename);
        Upload out = repository.findByFilename(filename).orElse(null);
        if(out == null) return new Resource<>(HttpStatus.NOT_FOUND);
        return new Resource<>(out);
    }

    /**
     * Method for getting all files uploaded in the last hour
     *
     * @return Collection containing Upload objects
     */
    @RequestMapping(value = "/recent", method = GET)
    public Resources<Upload> uploadedLastHour(){
        log.info("uploadedLastHour now: " + LocalDateTime.now() + " hour ago: " + LocalDateTime.now().minusHours(1));
        return new Resources<>(repository.findByDateUploadedAfter(Timestamp.valueOf(LocalDateTime.now().minusHours(1))));
    }

    /**
     * Method for getting file stream by fileid
     *
     * @param id
     * @return InputStream if file exists
     */
    @RequestMapping(value = "/stream/{id}", method = GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<org.springframework.core.io.Resource> streamByID(@PathVariable(value = "id") long id){
        log.info("streamByID " + id);
        Upload out = repository.findByFileId(id).get();

        if(out == null) return (ResponseEntity<org.springframework.core.io.Resource>) ResponseEntity.notFound();

        org.springframework.core.io.Resource file = service.loadAsResource(out.getFilename());

        return ResponseEntity.
                ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+out.getFilename()+"\"").
                body(file);
    }

    /**
     * Method for getting file stream by fileid
     *
     * @param filename
     * @return InputStream if file exists
     */
    @RequestMapping(value = "/stream/filename/{filename}/", method = GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<org.springframework.core.io.Resource> streamByFilename(@PathVariable(value = "filename") String filename){
        log.info("streamByFilename " + filename);
        Upload out = repository.findByFilename(filename).get();

        if(out == null) return (ResponseEntity<org.springframework.core.io.Resource>) ResponseEntity.notFound();

        org.springframework.core.io.Resource file = service.loadAsResource(out.getFilename());

        return ResponseEntity.
                ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+out.getFilename()+"\"").
                body(file);
    }

    /**
     * This bean is necessary for the entire program to run.
     * It creates the uploads directory where the files will be stored
     * It also deletes any existing files in the directory on start. SO BE CAREFUL
     *
     * @param service
     * @return
     */
    @Bean
    CommandLineRunner init(StorageService service){
        return (args -> {
            service.deleteAll();
            service.init();
        });
    }
}

