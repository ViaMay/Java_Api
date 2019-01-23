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
        private final String BASE_URL = "https://petstore.swagger.io/v2/store/order";

    public Controller() {
        restTemplate = new RestTemplate();
    }

    private final String BASE_URL_https = "https://petstore.swagger.io/v2/store/order";

    public <T> T controllerPost(Object object, Class<T> classResponse) {
        try {
            T response = restTemplate.postForObject(BASE_URL, object, classResponse);
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

    public OrderResponse controllerPostOrder(Object object) {
            OrderResponse response = restTemplate.postForObject(BASE_URL, object, OrderResponse.class);
            System.out.println(response);
            return response;
    }

    public ErrorResponse controllerPostError(Object object) {
        try {
            ErrorResponse response = restTemplate.postForObject(BASE_URL, object, ErrorResponse.class);
            System.out.println(response);
            return response;
        } catch (HttpServerErrorException errorException) {
            String responseBody = errorException.getResponseBodyAsString();
            System.out.println(responseBody);
            try {
                ErrorResponse Response = new ObjectMapper().readValue(responseBody, ErrorResponse.class);
                System.out.println(Response);

                return Response;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
