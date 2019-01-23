package core;

import core.Responses.ErrorResponse;
import core.Responses.OrderResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Controller {
    private RestTemplate restTemplate;
    private final String BASE_URL = "https://petstore.swagger.io/v2/";
    private String url;

    public Controller(String url) {
        restTemplate = new RestTemplate();
        this.url = url;
    }

    public <T> T controllerPost(Object object, Class<T> classResponse) {
        try {
            T response = restTemplate.postForObject(BASE_URL + url, object, classResponse);
            System.out.println(response);
            return response;
        } catch (HttpServerErrorException errorException) {
            String responseBody = errorException.getResponseBodyAsString();
            System.out.println(responseBody);
            return returnClassFromJson(responseBody, classResponse);
        }
        catch (HttpClientErrorException error) {
            String responseBody = error.getResponseBodyAsString();
            System.out.println(responseBody);
            return returnClassFromJson(responseBody, classResponse);
        }
    }

    private  <T> T returnClassFromJson(String responseBody, Class<T> classResponse)
    {
        try {
            T response = new ObjectMapper().readValue(responseBody, classResponse);
            System.out.println(response);
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
