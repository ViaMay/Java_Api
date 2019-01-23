package tests;

import core.Controller;
import core.Responses.OrderResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusCase
{
    private Controller controller;

   @BeforeTest
   public void SetUp(){
       controller = new Controller();
   }

    @DataProvider
    public Object[][] setOfValueGood(){
        return new Object[][]{
                {"placed"},
                {"approved"},
                {"delivered"},
                {11111}, //это значение не должно проходить
                {""}, //это значение не должно проходить
                {"qqq"},//это значение не должно проходить
                {"!@#$%^&*()_+№;%:?*()."}, //это значение не должно проходить
                {null}, //это значение не должно проходить
        };
    }

    @Test(dataProvider = "setOfValueGood")
    public void Order_Successful_Test(Object value) {
        Map<String, Object> values = new HashMap<>();
        values.put("status", value);
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        if(value == null)
            Assert.assertEquals(response.getStatus(), null);
        else
           Assert.assertEquals(response.getStatus(), value.toString());
    }
}