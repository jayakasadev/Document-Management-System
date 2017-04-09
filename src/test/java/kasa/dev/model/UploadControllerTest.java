package kasa.dev.model;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

/**
 * Unit Testing of the model package
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UploadControllerTest {

    // test this when the controller is completed
    // bit pointless at the moment
    private Upload test;
    private StringBuilder path;
    private RestTemplate template;
    private Resource resource;

    @Before
    public void init(){
        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Test
    public void t1(){
        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        resource = new FileSystemResource("C:\\MyWorkspace\\IntelliJ\\Algorithms\\src\\CH2\\insertionsort.txt");

        StringBuilder local = path.append("/upload/jaya kasa/Text file with runtimes");

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("file", resource);

        ResponseEntity<String> out = template.exchange(local.toString(),
                POST, new HttpEntity<MultiValueMap<String, Object>>(parts),
                String.class);

        // out.getBody().fields().forEachRemaining(System.out::println);
        System.out.println(out.getBody());
    }

    @Test
    public void t2(){
        StringBuilder local = path.append("/details/filename/insertionsort.txt");

        ObjectNode node = template.getForObject(local.toString(), ObjectNode.class);

        node.elements().forEachRemaining(System.out::println);

        assert node != null;
    }

    @Test
    public void t3(){
        StringBuilder local = path.append("/details/1");

        ObjectNode node = template.getForObject(local.toString(), ObjectNode.class);

        node.elements().forEachRemaining(System.out::println);

        assert node != null;
    }

    @Test
    public void t4(){
        StringBuilder local = path.append("/all");

        ObjectNode node = template.getForObject(local.toString(), ObjectNode.class);

        node.elements().forEachRemaining(x -> {
            if(x instanceof ObjectNode){
                System.out.println("Outer Objectnode");
                x.elements().forEachRemaining(System.out::println);
            }
            else if (x instanceof ArrayNode){
                System.out.println("Outer Arraynode");
                ((ArrayNode) x).forEach(y -> {
                    y.elements().forEachRemaining(z -> {
                        if(z instanceof ObjectNode){
                            z.elements().forEachRemaining(System.out::println);
                        }
                        else{
                            ((ArrayNode) z).forEach(System.out::println);
                        }
                    });
                });
            }
        });

        assert node != null;
    }

    @Test
    public void t5(){
        StringBuilder local = path.append("/recent");

        ObjectNode node = template.getForObject(local.toString(), ObjectNode.class);

        node.elements().forEachRemaining(x -> {
            if(x instanceof ObjectNode){
                System.out.println("Outer Objectnode");
                x.elements().forEachRemaining(System.out::println);
            }
            else if (x instanceof ArrayNode){
                System.out.println("Outer Arraynode");
                ((ArrayNode) x).forEach(y -> {
                    y.elements().forEachRemaining(z -> {
                        if(z instanceof ObjectNode){
                            z.elements().forEachRemaining(System.out::println);
                        }
                        else{
                            ((ArrayNode) z).forEach(System.out::println);
                        }
                    });
                });
            }
        });

        assert node != null;
    }
}
