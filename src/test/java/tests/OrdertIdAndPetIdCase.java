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

public class OrdertIdAndPetIdCase
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
                {"9223372036854775807"},
                {"-9223372036854775808"},
                {null},
        };
    }

    @DataProvider
    public Object[][] setOfValueFail(){
        return new Object[][]{
                {"9223372036854775808"},
                {"-9223372036854775809"},
                {"qqq"},
                {"!@#$%^&*()_+№;%:?*()."}
        };
    }

    @Test(dataProvider = "setOfValueGood")
    public void Order_Id_Successful_Test(Object valueId) {
        Map<String, Object> values = new HashMap<>();
        values.put("id", valueId);
        values.put("petId", "0");
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        System.out.println(response);
        if (Objects.equals(valueId, "") || valueId == null)
            valueId = "0";
        else
            Assert.assertEquals(response.getId(), valueId);
    }

    @Test(dataProvider = "setOfValueGood")
    public void Order_PetId_Successful_Test(Object valuePetId) {
        Map<String, Object> values = new HashMap<>();
        values.put("id", "0");
        values.put("petId", valuePetId);
        OrderResponse response = controller.controllerPost(values, OrderResponse.class);
        System.out.println(response);
        if (Objects.equals(valuePetId, "") || valuePetId == null)
            valuePetId = "0";
        else
            Assert.assertEquals(response.getPetId(), valuePetId);
    }

    @Test(dataProvider = "setOfValueFail")
    public void Order_Id_Unsuccessful_Test(String valueId){
        Map<String, String> values = new HashMap<>();
        values.put("id", valueId);
        values.put("petId", "0");
        ErrorResponse response = controller.controllerPost(values, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 500);
        Assert.assertEquals(response.getType(), "unknown");
        Assert.assertEquals(response.getMessage(), "something bad happened");
    }

    @Test(dataProvider = "setOfValueFail")
    public void Order_PetId_Unsuccessful_Test(String valuePetId){
        Map<String, String> values = new HashMap<>();
        values.put("id", "0");
        values.put("petId", valuePetId);
        ErrorResponse response = controller.controllerPost(values, ErrorResponse.class);
        Assert.assertEquals(response.getCode(), 500);
        Assert.assertEquals(response.getType(), "unknown");
        Assert.assertEquals(response.getMessage(), "something bad happened");
    }
}