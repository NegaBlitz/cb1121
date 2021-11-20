package master;

import model.RentalOrder;
import model.RentalTool;
import util.ConsolePromptUtil;
import util.DateUtil;
import util.ToolDefinitionsUtil;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class ToolRentalService {

    private static ToolDefinitionsUtil toolDefinitionsUtil = new ToolDefinitionsUtil();
    private static DateUtil dateUtil = new DateUtil();
    private static ConsolePromptUtil consolePromptUtil = new ConsolePromptUtil();
    private static String DATE_CHECK_REGEX = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    public static void main(String[] args){
        try {
            HashMap<String, RentalTool> rentalTools = new HashMap<>();
            RentalOrder rentalOrder = new RentalOrder();
            try {
                rentalTools = toolDefinitionsUtil.getRentalOptions();
            } catch (IOException e) {
                System.out.println("Could not get data for tools due to exception");
                e.printStackTrace();
                System.exit(1);
            }

            Scanner scanner = new Scanner(System.in);
            String toolCode = getToolCodeFromUser(scanner, rentalTools);
            RentalTool selectedTool = rentalTools.get(toolCode);
            String dateString = getDateStringFromUser(scanner);
            Integer discountPercentage = getDiscountPercentageFromUser(scanner);
            Integer daysToRent = getTotalRentalDaysFromUser(scanner);
            fillRentalOrderFieldsForTool(selectedTool, rentalOrder);
            scanner.close();

            consolePromptUtil.printCalculationMessage();

            LocalDate startDate = dateUtil.getDateFromString(dateString);
            Double preDiscountCharge = 0.00;
            int chargeDays = 0;

            for(int i = 0; i < daysToRent; i++) {
                boolean isHoliday = dateUtil.checkIfHoliday(startDate.plusDays(i));
                boolean isWeekend = dateUtil.checkIfWeekend(startDate.plusDays(i));
                double priceForDay = calculateDayRent(selectedTool, isHoliday, isWeekend);
                if(priceForDay > 0){
                    chargeDays++;
                    preDiscountCharge += priceForDay;
                }
            }

            double discount = preDiscountCharge * (discountPercentage/100.0);
            double finalCharge = preDiscountCharge - discount;

            // Make these conversions in the print out and calculate within a different method
            rentalOrder.setRentalDays(Integer.toString(daysToRent));
            rentalOrder.setCheckoutDate(dateUtil.getFormattedDate(startDate));
            rentalOrder.setDueDate(dateUtil.getFormattedDate(startDate.plusDays(daysToRent)));
            rentalOrder.setChargeDays(Integer.toString(chargeDays));
            rentalOrder.setDiscountPercent(discountPercentage+"%");
            rentalOrder.setDiscountAmount(currencyFormatter.format(discount));
            rentalOrder.setPreDiscountCharge(currencyFormatter.format(preDiscountCharge));
            rentalOrder.setFinalCharge(currencyFormatter.format(finalCharge));

            System.out.println();

            consolePromptUtil.printReport(rentalOrder);
            System.out.println("Exiting system");
        } catch (Exception e) {
            System.out.println("Fatal exception in program; exiting system.");
            e.printStackTrace();
        }
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

    private static void fillRentalOrderFieldsForTool(RentalTool tool, RentalOrder rentalOrder){
        rentalOrder.setDailyRentalCharge(currencyFormatter.format(tool.getDailyCharge()));
        rentalOrder.setToolBrand(tool.getToolBrand());
        rentalOrder.setToolType(tool.getToolType());
        rentalOrder.setToolCode(tool.getToolCode());
    }

    private static Double calculateDayRent(RentalTool tool, boolean isHoliday, boolean isWeekend){
        Double totalPriceForDay = 0.00;
        if(tool.isHolidayCharge() && isHoliday || tool.isWeekendCharge() && isWeekend || tool.isWeekdayCharge() && !isWeekend){
            totalPriceForDay += tool.getDailyCharge();
        }
        return totalPriceForDay;
    }
}