package dev.mchu.demo;


import net.fortuna.ical4j.model.WeekDay;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeekDayTest {

    private Logger log = LoggerFactory.getLogger(WeekDayTest.class);

    @Test
    public void testGetWeekDay(){
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(2022, 11, 7, 12, 0);
        assertEquals(WeekDay.WE, WeekDay.getWeekDay(cal));
    }

    @Test
    public void testGetMonthlyOffset() {
        Calendar cal = Calendar.getInstance();
        log.info("Monthly offset: " + WeekDay.getMonthlyOffset(cal));

        cal.add(Calendar.DAY_OF_MONTH, 15);
        log.info("Monthly offset: " + WeekDay.getMonthlyOffset(cal));
    }
}
