package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilTest {
    private DateUtil dateUtil = new DateUtil();

    @Test
    @DisplayName("Test valid dates in MM/dd/yy format")
    void testValidDates(){
        assertTrue(dateUtil.checkIfDateIsValid("01/01/21"));
        assertTrue(dateUtil.checkIfDateIsValid("02/29/20"));
        assertFalse(dateUtil.checkIfDateIsValid("02/29/21"));
        assertTrue(dateUtil.checkIfDateIsValid("12/30/90"));
        assertFalse(dateUtil.checkIfDateIsValid("aosdkfgincfgas"));
        assertFalse(dateUtil.checkIfDateIsValid("99/99/99"));
        assertFalse(dateUtil.checkIfDateIsValid("13/10/10"));
        assertFalse(dateUtil.checkIfDateIsValid("2015-02-20"));
    }

    @Test
    @DisplayName("Test holiday dates")
    void testHolidayDates() throws IOException {
        // First Monday tests
        assertTrue(dateUtil.checkIfHoliday(LocalDate.parse("2015-09-07")));
        assertFalse(dateUtil.checkIfHoliday(LocalDate.parse("2016-09-07")));

        // Weekend rollover tests
        assertTrue(dateUtil.checkIfHoliday(LocalDate.parse("2021-07-05")));
        assertTrue(dateUtil.checkIfHoliday(LocalDate.parse("2020-07-03")));
        assertTrue(dateUtil.checkIfHoliday(LocalDate.parse("2016-09-05")));

        // Weekend exemptions
        assertFalse(dateUtil.checkIfHoliday(LocalDate.parse("2021-07-04")));
        assertFalse(dateUtil.checkIfHoliday(LocalDate.parse("2015-07-04")));
    }

    @Test
    @DisplayName("Test if date is a weekend")
    void testWeekendDates(){
        assertTrue(dateUtil.checkIfWeekend(LocalDate.parse("2021-07-04")));
        assertFalse(dateUtil.checkIfWeekend(LocalDate.parse("2021-12-31")));
        assertTrue(dateUtil.checkIfWeekend(LocalDate.parse("2022-01-01")));
        assertTrue(dateUtil.checkIfWeekend(LocalDate.parse("2022-01-02")));
        assertFalse(dateUtil.checkIfWeekend(LocalDate.parse("2021-07-05")));
    }
}