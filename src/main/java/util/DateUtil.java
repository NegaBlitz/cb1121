package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Holiday;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateUtil {
    private static FileUtil fileUtil = new FileUtil();
    private Gson gson = new Gson();
    private static final String FILE_HOLIDAY_DEFINITIONS = "holidaydefinitions.json";

    public boolean checkIfHoliday(LocalDate date) throws IOException {
        List<Holiday> holidays = getHolidays();
        for(Holiday holiday : holidays){
            String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
            if(month.equals(holiday.getMonth())) {
                if (holiday.getDayOfWeekPosition() != null) {
                    return isHolidayForDayOfWeek(holiday, date);
                } else {
                    return isHolidayForDate(holiday, date);
                }
            }
        }
        return false;
    }

    private boolean isHolidayForDayOfWeek(Holiday holiday, LocalDate date){
        String dayOfWeek = getDayStringNew(date);
        if(dayOfWeek.equalsIgnoreCase(holiday.getDayOfWeek())){
            return isDayFirstOrLastOfMonth(holiday.getDayOfWeekPosition(), date);
        }
        return false;
    }

    private boolean isDayFirstOrLastOfMonth(String dayOfWeekPosition, LocalDate date){
        if(dayOfWeekPosition.equalsIgnoreCase("First")){
            if(date.getDayOfMonth() <= 7){
                return true;
            }
        } else {
            if(date.getDayOfMonth() >= date.lengthOfMonth() - 6){
                return true;
            }
        }
        return false;
    }

    private boolean isHolidayForDate(Holiday holiday, LocalDate date){
        if(!holiday.isObservedOnWeekend()){
            if(date.getDayOfWeek() == DayOfWeek.FRIDAY &&
                    (holiday.getDay() == date.getDayOfMonth() + 1 || holiday.getDay() == date.getDayOfMonth())) {
                return true;
            } else if(date.getDayOfWeek() == DayOfWeek.MONDAY &&
                    (holiday.getDay() == date.getDayOfMonth() - 1 || holiday.getDay() == date.getDayOfMonth())){
                return true;
            } else if(checkIfWeekend(date)){
                return false;
            }
        }
        return holiday.getDay() == date.getDayOfMonth();
    }

    public boolean checkIfWeekend(LocalDate date){
        if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) return true;
        return false;
    }

    public LocalDate getDateFromString(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    public static String getDayStringNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, Locale.US);
    }

    public List<Holiday> getHolidays() throws IOException {
        InputStream stream = fileUtil.getFileFromResourceAsStream(FILE_HOLIDAY_DEFINITIONS);
        String allHolidaysJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();
        Type allHolidaysType = new TypeToken<ArrayList<Holiday>>(){}.getType();
        List<Holiday> allHolidays = gson.fromJson(allHolidaysJson, allHolidaysType);
        return allHolidays;
    }

    public static boolean checkIfDateIsValid(String dateString) {
        if (dateString == null || !dateString.matches("\\d{2}\\/\\d{2}\\/\\d{2}"))
            return false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public String getFormattedDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return date.format(formatter);
    }
}