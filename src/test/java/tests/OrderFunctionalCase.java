package tests;

import core.Controller;
import core.Responses.ErrorResponse;
import core.Responses.OrderResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class OrderFunctionalCase
{
    private Controller controller;
    private String url = "store/order";

   @BeforeTest
   public void SetUp(){
       controller = new Controller(url);
   }

    @Test()
    public void Order_Successful_null_Test() {
        Map<String, Object> values = new HashMap<>();
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        Assert.assertEquals(response.getId(), "0");
        Assert.assertEquals(response.getPetId(), "0");
        Assert.assertEquals(response.getQuantity(), "0");
        Assert.assertEquals(response.getComplete(), false);
    }

    @Test()
    public void Order_Successful_Full_Test() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", "1");
        values.put("petId", "1");
        values.put("complete", true);
        values.put("quantity", "1");
        values.put("status", "approved");
        values.put("shipDate", "2019-01-22");
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        Assert.assertEquals(response.getId(), "1");
        Assert.assertEquals(response.getPetId(), "1");
        Assert.assertEquals(response.getQuantity(), "1");
        Assert.assertEquals(response.getComplete(), true);
        Assert.assertEquals(response.getStatus(), "approved");
        Assert.assertEquals(response.getShipDate(), "2019-01-22T00:00:00.000+0000");
    }

    @Test
    public void Order_Unsuccessful_EmptyFile_Test() {
        ErrorResponse response = controller.controllerPost(null, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 415);
        Assert.assertEquals(response.getType(), "unknown");
    }

    @DataProvider
    public Object[][] setOfValueFail(){
        return new String[][]{
                {"{\"id\": }"},
                {"{\"petId\": }"},
                {"{\"quantity\": }"},
                {"{\"shipDate\": }"},
                {"{\"status\": }"},
                {"{\"complete\": }"},
        };
    }

    @Test(dataProvider = "setOfValueFail")
    public void Order_Unsuccessful_BadJson_Test(String requestJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson,headers);
        ErrorResponse response = controller.controllerPost(entity, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 400);
        Assert.assertEquals(response.getType(), "unknown");
        Assert.assertEquals(response.getMessage(), "bad input");
    }
}