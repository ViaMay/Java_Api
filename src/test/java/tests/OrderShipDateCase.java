package tests;

import core.Controller;
import core.Responses.ErrorResponse;
import core.Responses.OrderResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderShipDateCase
{
    private Controller controller;

   @BeforeTest
   public void SetUp(){
       controller = new Controller();
   }

    @DataProvider
    public Object[][] setOfValueGood(){
        return new Object[][]{
                {"0"}, //не должно проходить
                {"17"}, //не должно проходить
                {"2019-01-22T11:37:05.988Z"},
                {"2019-01-22"},
                {""}
        };
    }

    @DataProvider
    public Object[][] setOfValueFail(){
        return new String[][]{
                {"22.01.2019"},
                {"2019.01.22"},
                {"qqq"},
                {"!@#$%^&*()_+№;%:?*()."}
        };
    }

    @Test(dataProvider = "setOfValueGood")
    public void Order_Successful_Test(Object value) {
        Map<String, Object> values = new HashMap<>();
        values.put("shipDate", value);
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        if(value.toString().contains("2019-01-22"))
            Assert.assertTrue(response.getShipDate().contains("2019-01-22"));
        else if (value.toString().contains("0") || value.toString().contains("17"))
            Assert.assertTrue(response.getShipDate().contains("1970-01-01"));
        else if (Objects.equals(value, ""))
            Assert.assertEquals(response.getShipDate(), null);
        else
            Assert.assertEquals(response.getShipDate(), "");
    }

    @Test(dataProvider = "setOfValueFail")
    public void Order_Unsuccessful_Test(String value) {
        Map<String, String> values = new HashMap<>();
        values.put("shipDate", value);
        ErrorResponse response = controller.controllerPost(values, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 500);
        Assert.assertEquals(response.getType(), "unknown");
        Assert.assertEquals(response.getMessage(), "something bad happened");
    }
}