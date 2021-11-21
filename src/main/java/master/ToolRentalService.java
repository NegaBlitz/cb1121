package master;

import exceptions.InvalidUserInputException;
import model.RentalOrder;
import model.RentalTool;
import util.ConsolePromptUtil;
import util.DateUtil;
import util.ToolDefinitionsUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class ToolRentalService {

    private static ToolDefinitionsUtil toolDefinitionsUtil = new ToolDefinitionsUtil();
    private static DateUtil dateUtil = new DateUtil();
    private static ConsolePromptUtil consolePromptUtil = new ConsolePromptUtil();

    public static void main(String[] args){
        try {
            RentalOrder rentalOrder = new RentalOrder();

            Scanner scanner = new Scanner(System.in);
            RentalTool rentedTool = getRentedToolFromUser(scanner);
            String dateString = getDateStringFromUser(scanner);
            Integer discountPercentage = getDiscountPercentageFromUser(scanner);
            Integer daysToRent = getTotalRentalDaysFromUser(scanner);
            scanner.close();

            LocalDate startDate = dateUtil.getDateFromString(dateString);

            rentalOrder.setDailyRentalCharge(rentedTool.getDailyCharge());
            rentalOrder.setToolBrand(rentedTool.getToolBrand());
            rentalOrder.setToolType(rentedTool.getToolType());
            rentalOrder.setToolCode(rentedTool.getToolCode());
            rentalOrder.setRentalDays(daysToRent);
            rentalOrder.setCheckoutDate(startDate);
            rentalOrder.setDueDate(startDate.plusDays(daysToRent));
            rentalOrder.setDiscountPercent(discountPercentage);

            consolePromptUtil.printCalculationMessage();
            rentalCheckout(rentedTool, rentalOrder);
            consolePromptUtil.printReport(rentalOrder);
            System.out.println("Exiting system");
        } catch (Exception e) {
            System.out.println("Fatal exception in program; exiting system.");
            e.printStackTrace();
        }
    }

    private static RentalTool getRentedToolFromUser(Scanner scanner) throws IOException {
        HashMap<String, RentalTool> allTools = toolDefinitionsUtil.getRentalOptions();
        consolePromptUtil.promptUserForToolCode();
        String toolCode = scanner.nextLine().toUpperCase();

        while(!allTools.containsKey(toolCode)){
            consolePromptUtil.warnUserForInvalidToolCode();
            consolePromptUtil.displayAvailableTools(allTools);
            consolePromptUtil.promptUserForToolCode();
            toolCode = scanner.nextLine().toUpperCase();
        }
        return allTools.get(toolCode);
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

    protected static void rentalCheckout(RentalTool rentedTool, RentalOrder rentalOrder) throws IOException, InvalidUserInputException {
        Integer daysToRent = rentalOrder.getRentalDays();
        LocalDate startDate = rentalOrder.getCheckoutDate();
        Integer discountPercentage = rentalOrder.getDiscountPercent();
        Double preDiscountCharge = 0.00;
        int chargeDays = 0;
        if(daysToRent <= 0){
            throw new InvalidUserInputException("Days to rent must be greater than zero; Found " + daysToRent);
        }
        if(discountPercentage > 100 || discountPercentage < 0){
            throw new InvalidUserInputException("Discount Percentage must be between 0 and 100; Found " + discountPercentage);
        }

        for(int i = 0; i < daysToRent; i++) {
            boolean isHoliday = dateUtil.checkIfHoliday(startDate.plusDays(i));
            boolean isWeekend = dateUtil.checkIfWeekend(startDate.plusDays(i));
            double priceForDay = calculateDayRent(rentedTool, isHoliday, isWeekend);
            if(priceForDay > 0){
                chargeDays++;
                preDiscountCharge += priceForDay;
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");
        Double discount = preDiscountCharge * (discountPercentage/100.0);
        Double finalCharge = preDiscountCharge - discount;
        rentalOrder.setChargeDays(chargeDays);
        rentalOrder.setDiscountAmount(Double.valueOf(df.format(discount)));
        rentalOrder.setPreDiscountCharge(Double.valueOf(df.format(preDiscountCharge)));
        rentalOrder.setFinalCharge(Double.valueOf(df.format(finalCharge)));
    }

    private static Double calculateDayRent(RentalTool rentedTool, boolean isHoliday, boolean isWeekend){
        Double totalPriceForDay = 0.00;
        if((!rentedTool.isHolidayCharge() && isHoliday) || (!rentedTool.isWeekendCharge() && isWeekend)){
            return totalPriceForDay;
        }
        if((rentedTool.isWeekdayCharge() && !isWeekend) || (rentedTool.isWeekendCharge() && isWeekend)){
            totalPriceForDay += rentedTool.getDailyCharge();
        }
        return totalPriceForDay;
    }
}