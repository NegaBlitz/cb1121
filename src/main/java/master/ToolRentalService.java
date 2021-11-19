package master;

import model.RentalTool;
import util.ToolDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToolRentalService {

    static ToolDefinitions toolDefinitions = new ToolDefinitions();

    public static void main(String[] args){
        List<RentalTool> allTools = new ArrayList<>();
        try {
            allTools = toolDefinitions.getToolDefinitions();
        } catch (IOException e){
            System.out.println("Could not get data for tools");
            e.printStackTrace();
            System.exit(1);
        }
    }
}