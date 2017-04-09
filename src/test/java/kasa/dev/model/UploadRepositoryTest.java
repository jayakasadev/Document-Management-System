package kasa.dev.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Unit Testing of the model package
 */
public class UploadRepositoryTest {

    // test this when the controller is completed
    // bit pointless at the moment
    @Autowired
    private UploadRepository repository;
    private Upload test;

    @Before
    public void before(){
        // asserting that the db is empty
        assert (repository != null);
        assert (repository.findAll().size() == 0);

        //uploading some filler data
        test = new Upload("a", "b", "c", "d");
        repository.save(test);

        for(int a = 0; a < 20; a+=4)
            // going to print the returned value for later reference
            System.out.println(repository.save(new Upload("" + a , "" + a+1, "" + a+2, "" + a+3)));
    }

    @Test
    public void findByFilename(){
        assert (repository.findByFilename("a").get() != null);
    }

    @Test
    public void save(){
        assert (repository.save(test) == null);
    }

    @Test
    public void findByFileID(){
        assert (repository.findByFileId(0l).get() != null);
    }

    @Test
    public void findAll(){
        assert (repository.findAll().size() > 0);
    }

    @Test
    public void findByDateUploadedAfter(){
        assert (repository.findByDateUploadedAfter(Timestamp.valueOf(LocalDateTime.now().minusHours(1))).size() > 0);
    }
}
