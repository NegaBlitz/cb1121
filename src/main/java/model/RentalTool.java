package model;

public class RentalTool {
    String toolCode;
    String toolType;
    String toolBrand;
    Double dailyCharge;
    String weekdayCharge;
    String weekendCharge;
    String holidayCharge;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public Double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(Double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public String getWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(String weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public String getWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(String weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public String getHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(String holidayCharge) {
        this.holidayCharge = holidayCharge;
    }
}