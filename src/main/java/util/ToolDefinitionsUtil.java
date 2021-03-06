package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.RentalTool;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToolDefinitionsUtil {

    private Gson gson = new Gson();
    private final String FILE_PATH_RENTAL_OPTIONS = "rentaloptions.json";
    private final String FILE_PATH_TOOL_DEFINITIONS = "tooldefinitions.json";
    private static FileUtil fileUtil = new FileUtil();

    public HashMap<String, RentalTool> getRentalOptions() throws IOException {
        InputStream stream = fileUtil.getFileFromResourceAsStream(FILE_PATH_RENTAL_OPTIONS);
        String allRentalsJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();
        Type allRentalsType = new TypeToken<ArrayList<RentalTool>>(){}.getType();
        List<RentalTool> allRentals = gson.fromJson(allRentalsJson, allRentalsType);
        return getToolDefinitionsForRentals(allRentals);
    }

    public HashMap<String, RentalTool> getToolDefinitionsForRentals(List<RentalTool> allRentals) throws IOException {
        InputStream stream = fileUtil.getFileFromResourceAsStream(FILE_PATH_TOOL_DEFINITIONS);
        String allToolDefinitionsJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();
        Type allToolDefinitionsType = new TypeToken<HashMap<String, RentalTool>>(){}.getType();
        HashMap<String, RentalTool> allToolDefinitions = gson.fromJson(allToolDefinitionsJson, allToolDefinitionsType);
        HashMap<String, RentalTool> toolRentalOptions = new HashMap<>();
        for (RentalTool tool : allRentals) {
            RentalTool toolDefinition = allToolDefinitions.get(tool.getToolType());
            tool.setDailyCharge(toolDefinition.getDailyCharge());
            tool.setHolidayCharge(toolDefinition.isHolidayCharge());
            tool.setWeekdayCharge(toolDefinition.isWeekdayCharge());
            tool.setWeekendCharge(toolDefinition.isWeekendCharge());
            tool.setToolCode(tool.getToolCode().toUpperCase());
            toolRentalOptions.put(tool.getToolCode(), tool);
        }
        return toolRentalOptions;
    }
}