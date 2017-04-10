package kasa.dev.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kasa.dev.model.Upload;
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

import java.io.*;

import static org.springframework.http.HttpMethod.GET;
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

        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder local = path.append("/details/filename/insertionsort.txt/");

        ResponseEntity<String> out = template.getForEntity(local.toString(), String.class);

        System.out.println(out);

        assert out != null;
    }

    @Test
    public void t3(){

        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder local = path.append("/details/1");

        ResponseEntity<String> out = template.getForEntity(local.toString(), String.class);

        System.out.println(out);

        assert out != null;
    }

    @Test
    public void t4(){

        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder local = path.append("/all");

        ResponseEntity<String> node = template.getForEntity(local.toString(), String.class);

        System.out.println(node);

        assert node != null;
    }

    @Test
    public void t5(){

        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder local = path.append("/recent");

        ResponseEntity<String> node = template.getForEntity(local.toString(), String.class);

        System.out.println(node);

        assert node != null;
    }

    @Test
    public void t6(){
        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder local = path.append("/stream/1");

        ResponseEntity<Resource> node = template.getForEntity(local.toString(), Resource.class);

        System.out.println(node);

        try {
            BufferedReader stream = new BufferedReader(new InputStreamReader(node.getBody().getInputStream()));

            String temp = "";
            while((temp = stream.readLine()) != null){
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert node != null;
    }

    @Test
    public void t7(){
        path = new StringBuilder("http://localhost:8080");
        template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder local = path.append("/stream/filename/insertionsort.txt/");

        ResponseEntity<Resource> node = template.getForEntity(local.toString(), Resource.class);

        System.out.println(node);

        try {
            BufferedReader stream = new BufferedReader(new InputStreamReader(node.getBody().getInputStream()));

            String temp = "";
            while((temp = stream.readLine()) != null){
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert node != null;
    }

}
