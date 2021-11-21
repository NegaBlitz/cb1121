package master;

import com.google.gson.Gson;
import exceptions.InvalidUserInputException;
import model.RentalOrder;
import model.RentalTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ToolDefinitionsUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ToolRentalServiceTest {
    Gson gson = new Gson();
    RentalOrder rentalOrder = new RentalOrder();
    RentalTool rentalTool = new RentalTool();
    ToolRentalService toolRentalService = new ToolRentalService();
    ToolDefinitionsUtil toolDefinitionsUtil = new ToolDefinitionsUtil();

    @BeforeEach
    void setup(){
        rentalOrder = new RentalOrder();
        rentalTool = new RentalTool();
    }

    @Test
    @DisplayName("Required Test 1")
    void requiredTestCase1() throws IOException {
        rentalOrder.setCheckoutDate(LocalDate.parse("2015-09-03"));
        rentalOrder.setRentalDays(5);
        rentalOrder.setDiscountPercent(101);
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        rentalTool = allTools.get("JAKR");
        assertNotNull(rentalTool);

        Throwable invalidUserInputException = assertThrows(InvalidUserInputException.class, () ->{
            toolRentalService.rentalCheckout(rentalTool, rentalOrder);
        });

        String expectedMessage = "Discount Percentage must be between 0 and 100; Found 101";
        String actualMessage = invalidUserInputException.getMessage();
        assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    @DisplayName("Required Test 2")
    void requiredTestCase2() throws IOException, InvalidUserInputException {
        rentalOrder.setCheckoutDate(LocalDate.parse("2020-07-02"));
        rentalOrder.setRentalDays(3);
        rentalOrder.setDiscountPercent(10);
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        rentalTool = allTools.get("LADW");
        assertNotNull(rentalTool);

        toolRentalService.rentalCheckout(rentalTool, rentalOrder);
        assertTrue(rentalOrder.getFinalCharge().equals(3.58));
        assertTrue(rentalOrder.getChargeDays().equals(2));
    }

    @Test
    @DisplayName("Required Test 3")
    void requiredTestCase3() throws IOException, InvalidUserInputException {
        rentalOrder.setCheckoutDate(LocalDate.parse("2015-07-02"));
        rentalOrder.setRentalDays(5);
        rentalOrder.setDiscountPercent(25);
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        rentalTool = allTools.get("CHNS");
        assertNotNull(rentalTool);

        toolRentalService.rentalCheckout(rentalTool, rentalOrder);
        assertTrue(rentalOrder.getFinalCharge().equals(3.35));
        assertTrue(rentalOrder.getChargeDays().equals(3));
    }

    @Test
    @DisplayName("Required Test 4")
    void requiredTestCase4() throws InvalidUserInputException, IOException {
        rentalOrder.setCheckoutDate(LocalDate.parse("2015-09-03"));
        rentalOrder.setRentalDays(6);
        rentalOrder.setDiscountPercent(0);
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        rentalTool = allTools.get("JAKD");
        assertNotNull(rentalTool);

        toolRentalService.rentalCheckout(rentalTool, rentalOrder);
        assertTrue(rentalOrder.getFinalCharge().equals(8.97));
        assertTrue(rentalOrder.getChargeDays().equals(3));
    }

    @Test
    @DisplayName("Required Test 5")
    void requiredTestCase5() throws InvalidUserInputException, IOException {
        rentalOrder.setCheckoutDate(LocalDate.parse("2015-07-02"));
        rentalOrder.setRentalDays(9);
        rentalOrder.setDiscountPercent(0);
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        rentalTool = allTools.get("JAKR");
        assertNotNull(rentalTool);

        toolRentalService.rentalCheckout(rentalTool, rentalOrder);
        assertTrue(rentalOrder.getFinalCharge().equals(17.94));
        assertTrue(rentalOrder.getChargeDays().equals(6));
    }

    @Test
    @DisplayName("Required Test 6")
    void requiredTestCase6() throws InvalidUserInputException, IOException {
        rentalOrder.setCheckoutDate(LocalDate.parse("2020-07-02"));
        rentalOrder.setRentalDays(4);
        rentalOrder.setDiscountPercent(50);
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        rentalTool = allTools.get("JAKR");
        assertNotNull(rentalTool);

        toolRentalService.rentalCheckout(rentalTool, rentalOrder);
        assertTrue(rentalOrder.getFinalCharge().equals(1.50));
        assertTrue(rentalOrder.getChargeDays().equals(1));
    }
}