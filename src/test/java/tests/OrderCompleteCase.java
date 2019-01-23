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

public class OrderCompleteCase
{
    private Controller controller;
    private String url = "store/order";

   @BeforeTest
   public void SetUp(){
       controller = new Controller(url);
   }

    @DataProvider
    public Object[][] setOfValueGood(){
        return new Object[][]{
                {false},
                {null},
                {0},
                {""},
                {true},
                {1}
        };
    }

    @DataProvider
    public Object[][] setOfValueFail(){
        return new Object[][]{
                {"qqq"},
                {"!@#$%^&*()_+№;%:?*()."},
                {"0"},
                {"1"},
        };
    }

    @Test(dataProvider = "setOfValueGood")
    public void Order_Successful_Test(Object value) {
        Map<String, Object> values = new HashMap<>();
        values.put("complete", value);
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        if(value == null)
            Assert.assertEquals(response.getComplete(), false);
        else if (value.toString().equals("true") || value.toString().equals("1") )
            Assert.assertEquals(response.getComplete(), true);
        else
            Assert.assertEquals(response.getComplete(), false);
    }

    @Test(dataProvider = "setOfValueFail")
    public void Order_Unsuccessful_Test(Object value) {
        Map<String, Object> values = new HashMap<>();
        values.put("complete", value);
        ErrorResponse response = controller.controllerPost(values, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 500);
        Assert.assertEquals(response.getType(), "unknown");
        Assert.assertEquals(response.getMessage(), "something bad happened");
    }
}