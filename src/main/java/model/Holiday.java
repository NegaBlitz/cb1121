package model;

public class Holiday {
    int day;
    String month;
    String dayOfWeek;
    String dayOfWeekPosition; // Either 'First' or 'Last'
    boolean observedOnWeekend;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfWeekPosition() {
        return dayOfWeekPosition;
    }

    public void setDayOfWeekPosition(String dayOfWeekPosition) {
        this.dayOfWeekPosition = dayOfWeekPosition;
    }

    public boolean isObservedOnWeekend() {
        return observedOnWeekend;
    }

    public void setObservedOnWeekend(boolean observedOnWeekend) {
        this.observedOnWeekend = observedOnWeekend;
    }
}
