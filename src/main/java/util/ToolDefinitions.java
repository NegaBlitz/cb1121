package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.RentalTool;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ToolDefinitions {

    private Gson gson = new Gson();

    private final String FILE_PATH = "tooldefinitions.json";

    public List<RentalTool> getToolDefinitions() throws IOException {
        InputStream stream = getFileFromResourceAsStream(FILE_PATH);
        String allToolsJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);;
        Type allToolsType = new TypeToken<ArrayList<RentalTool>>(){}.getType();
        List<RentalTool> allTools = gson.fromJson(allToolsJson, allToolsType);
        return allTools;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}