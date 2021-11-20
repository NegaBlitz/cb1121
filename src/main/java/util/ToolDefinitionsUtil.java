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

    public List<RentalTool> getRentalOptions() throws IOException {
        InputStream stream = fileUtil.getFileFromResourceAsStream(FILE_PATH_RENTAL_OPTIONS);
        String allRentalsJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();
        Type allRentalsType = new TypeToken<ArrayList<RentalTool>>(){}.getType();
        List<RentalTool> allRentals = gson.fromJson(allRentalsJson, allRentalsType);
        return getToolDefinitionsForRentals(allRentals);
    }

    public List<RentalTool> getToolDefinitionsForRentals(List<RentalTool> allRentals) throws IOException {
        InputStream stream = fileUtil.getFileFromResourceAsStream(FILE_PATH_TOOL_DEFINITIONS);
        String allToolDefinitionsJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();
        Type allToolDefinitionsType = new TypeToken<HashMap<String, RentalTool>>(){}.getType();
        HashMap<String, RentalTool> allToolDefinitions = gson.fromJson(allToolDefinitionsJson, allToolDefinitionsType);

        for (RentalTool tool : allRentals) {
            RentalTool toolDefinition = allToolDefinitions.get(tool.getToolType());
            tool.setDailyCharge(toolDefinition.getDailyCharge());
            tool.setHolidayCharge(toolDefinition.getHolidayCharge());
            tool.setWeekdayCharge(toolDefinition.getWeekdayCharge());
            tool.setWeekendCharge(toolDefinition.getWeekendCharge());
        }
        return allRentals;
    }
}