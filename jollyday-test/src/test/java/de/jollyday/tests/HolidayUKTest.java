package de.jollyday.tests;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameters;
import de.jollyday.tests.base.AbstractCountryTestBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HolidayUKTest extends AbstractCountryTestBase {

  private static final int YEAR = 2010;
  private static final String ISO_CODE = "gb";

  @Test
  public void testManagerUKStructure() throws Exception {
    validateCalendarData(ISO_CODE, YEAR);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnSunday() {
    doChristmasContainmentTest(2011, 26, 27);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnSaturday() {
    doChristmasContainmentTest(2010, 27, 28);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnFriday() {
    doChristmasContainmentTest(2009, 25, 28);
  }

  private void doChristmasContainmentTest(int year, int dayOfChristmas, int dayOfBoxingday) {
    LocalDate christmas = LocalDate.of(year, 12, dayOfChristmas);
    LocalDate boxingday = LocalDate.of(year, 12, dayOfBoxingday);
    HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(HolidayCalendar.UNITED_KINGDOM));
    Set<Holiday> holidays = holidayManager.getHolidays(year);
    assertTrue(contains(christmas, holidays), "There should be christmas on " + christmas);
    assertTrue(contains(boxingday, holidays), "There should be boxing day on " + boxingday);
  }

  private boolean contains(LocalDate localDate, Set<Holiday> holidays) {
    for (Holiday h : holidays) {
      if (localDate.equals(h.getDate())) {
        return true;
      }
    }
    return false;
  }

}
