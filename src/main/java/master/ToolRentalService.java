package master;

import model.RentalTool;
import util.ToolDefinitionsUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToolRentalService {

    static ToolDefinitionsUtil toolDefinitionsUtil = new ToolDefinitionsUtil();

    public static void main(String[] args){
        List<RentalTool> allTools = new ArrayList<>();
        try {
            allTools = toolDefinitionsUtil.getRentalOptions();
        } catch (IOException e){
            System.out.println("Could not get data for tools due to exception");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Exiting system");
    }
}