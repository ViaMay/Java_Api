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

public class OrderQuantityCase
{
    private Controller controller;

   @BeforeTest
   public void SetUp(){
       controller = new Controller();
   }

    @DataProvider
    public Object[][] setOfValueGood(){
        return new Object[][]{
                {""},
                {"0"},
                {"1"},
                {"2147483647"},
                {"-2147483648"},
                {null},
        };
    }

    @DataProvider
    public Object[][] setOfValueFail(){
        return new Object[][]{
                {"2147483648"},
                {"-2147483649"},
                {"qqq"},
                {"!@#$%^&*()_+№;%:?*()."}
        };
    }

    @Test(dataProvider = "setOfValueGood")
    public void Order_Successful_Test(String value) {
        Map<String, String> values = new HashMap<>();
        values.put("quantity", value);
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        if (Objects.equals(value, "") || value == null)
            Assert.assertEquals(response.getQuantity(), "0");
        else
        Assert.assertEquals(response.getQuantity(), value);
    }

    @Test(dataProvider = "setOfValueFail")
    public void Order_Unsuccessful_Test(String value) {
        Map<String, String> values = new HashMap<>();
        values.put("id", "0");
        values.put("petId", "0");
        values.put("quantity", value);
        ErrorResponse response = controller.controllerPost(values, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 500);
        Assert.assertEquals(response.getType(), "unknown");
        Assert.assertEquals(response.getMessage(), "something bad happened");
    }

}