package master;

import model.RentalTool;
import util.DateUtil;
import util.ToolDefinitionsUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToolRentalService {

    static ToolDefinitionsUtil toolDefinitionsUtil = new ToolDefinitionsUtil();
    static DateUtil dateUtil = new DateUtil();

    public static void main(String[] args){
        List<RentalTool> allTools = new ArrayList<>();
        try {
            allTools = toolDefinitionsUtil.getRentalOptions();
        } catch (IOException e){
            System.out.println("Could not get data for tools due to exception");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            LocalDate date = dateUtil.getDateFromString("2019-07-04");
            boolean isHoliday = dateUtil.checkIfHoliday(date);
            System.out.println(isHoliday);
        } catch (Exception e) {
            System.out.println("Could not check date due to exception");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Exiting system");
    }
}