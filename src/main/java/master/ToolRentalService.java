package master;

import model.RentalTool;
import util.ConsolePromptUtil;
import util.DateUtil;
import util.ToolDefinitionsUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ToolRentalService {

    private static ToolDefinitionsUtil toolDefinitionsUtil = new ToolDefinitionsUtil();
    private static DateUtil dateUtil = new DateUtil();
    private static ConsolePromptUtil consolePromptUtil = new ConsolePromptUtil();
    private static String DATE_CHECK_REGEX = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";

    public static void main(String[] args){
        HashMap<String, RentalTool> allTools = new HashMap<>();
        try {
            allTools = toolDefinitionsUtil.getRentalOptions();
        } catch (IOException e){
            System.out.println("Could not get data for tools due to exception");
            e.printStackTrace();
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        String toolCode = getToolCodeFromUser(scanner, allTools);
        String dateString = getDateStringFromUser(scanner);
        Integer discountPercentage = getDiscountPercentageFromUser(scanner);
        Integer daysToRent = getTotalRentalDaysFromUser(scanner);

        /*try {
            System.out.println("Please insert a date");
            LocalDate date = dateUtil.getDateFromString("2019-07-04");
            boolean isHoliday = dateUtil.checkIfHoliday(date);
            System.out.println(isHoliday);
        } catch (Exception e) {
            System.out.println("Could not check date due to exception");
            e.printStackTrace();
            userInput.close();
            System.exit(1);
        }*/

        scanner.close();
        System.out.println("Exiting system");
    }

    private static String getToolCodeFromUser(Scanner scanner, HashMap<String, RentalTool> allTools){
        consolePromptUtil.promptUserForToolCode();
        String toolCode = scanner.nextLine().toUpperCase();

        while(!allTools.containsKey(toolCode)){
            consolePromptUtil.warnUserForInvalidToolCode();
            consolePromptUtil.displayAvailableTools(allTools);
            consolePromptUtil.promptUserForToolCode();
            toolCode = scanner.nextLine().toUpperCase();
        }
        return toolCode;
    }

    private static String getDateStringFromUser(Scanner scanner){
        consolePromptUtil.promptUserForDate();
        String dateString = scanner.nextLine().toUpperCase();

        while(!dateUtil.checkIfDateIsValid(dateString)){
            consolePromptUtil.warnUserForInvalidDate();
            consolePromptUtil.promptUserForDate();
            dateString = scanner.nextLine();
        }
        return dateString;
    }

    private static Integer getDiscountPercentageFromUser(Scanner scanner){
        Integer discountPercentage = null;
        while(null == discountPercentage){
            try {
                consolePromptUtil.promptUserForDiscountPercentage();
                String discountPercentageString = scanner.nextLine();
                discountPercentage = Integer.parseInt(discountPercentageString);
                if (discountPercentage < 0 || discountPercentage > 100) {
                    consolePromptUtil.warnUserForInvalidDiscountPercentage();
                    discountPercentage = null;
                }
            } catch (NumberFormatException e){
                consolePromptUtil.warnUserForInvalidDiscountPercentage();
                discountPercentage = null;
            }
        }
        return discountPercentage;
    }

    private static Integer getTotalRentalDaysFromUser(Scanner scanner){
        Integer daysToRent = null;
        while(null == daysToRent){
            try {
                consolePromptUtil.promptUserForRentalDayCount();
                String daysToRentString = scanner.nextLine();
                daysToRent = Integer.parseInt(daysToRentString);
                if(daysToRent <= 0){
                    consolePromptUtil.warnUserForInvalidRentalDayCount();
                    daysToRent = null;
                }
            } catch (NumberFormatException e){
                consolePromptUtil.warnUserForInvalidRentalDayCount();
                daysToRent = null;
            }
        }
        return daysToRent;
    }
}