package util;

import model.RentalTool;

import java.util.HashMap;

public class ConsolePromptUtil {
    private final String PRINT_GREETING_FOR_USER = "Welcome to the tool rental application!";
    private final String PROMPT_FOR_TOOL_CODE = "Please insert the tool code for the tool that the customer wishes to rent: ";
    private final String PROMPT_FOR_RENTAL_DAY_COUNT = "Please specify the amount of days that the customer will rent the tool: ";
    private final String PROMPT_FOR_DISCOUNT_PERCENTAGE = "Please specify a discount to apply (As a whole number, 0-100): ";
    private final String PROMPT_FOR_DATE = "Please specify the date that the customer will check out the tool (use format 'YYYY-MM-DD'): ";
    private final String WARN_USER_INVALID_TOOLCODE = "Tool code is not recognized; Please insert a valid tool code.";
    private final String WARN_USER_INVALID_DATE = "Date inserted is not in the right format ('YYYY-MM-DD') or is not a valid date!";
    private final String WARN_USER_INVALID_DISCOUNT_PERCENTAGE = "Please insert a valid discount percentage (0-100)";
    private final String WARN_USER_INVALID_RENTAL_DAY_COUNT = "Please specify a valid number of days to rent (Greater than zero)";

    public void printGreeting(){
        System.out.println(PRINT_GREETING_FOR_USER);
    }

    public void promptUserForToolCode(){
        System.out.println(PROMPT_FOR_TOOL_CODE);
    }

    public void warnUserForInvalidToolCode(){
        System.out.println(WARN_USER_INVALID_TOOLCODE);
    }

    public void promptUserForRentalDayCount(){
        System.out.println(PROMPT_FOR_RENTAL_DAY_COUNT);
    }

    public void warnUserForInvalidRentalDayCount(){
        System.out.println(WARN_USER_INVALID_RENTAL_DAY_COUNT);
    }

    public void promptUserForDiscountPercentage(){
        System.out.println(PROMPT_FOR_DISCOUNT_PERCENTAGE);
    }

    public void warnUserForInvalidDiscountPercentage(){
        System.out.println(WARN_USER_INVALID_DISCOUNT_PERCENTAGE);
    }

    public void promptUserForDate(){
        System.out.println(PROMPT_FOR_DATE);
    }

    public void warnUserForInvalidDate(){
        System.out.println(WARN_USER_INVALID_DATE);
    }

    public void displayAvailableTools (HashMap<String, RentalTool> rentalTools){
        for(RentalTool rentalTool : rentalTools.values()){
            System.out.print(rentalTool.getToolCode() + " ");
        }
        System.out.print("\n");
    }
}