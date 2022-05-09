package de.jollyday.tests.parsers;

import de.jollyday.Holiday;
import de.jollyday.config.*;
import de.jollyday.parser.impl.RelativeToFixedParser;
import de.jollyday.util.CalendarUtil;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sven
 */
public class RelativeToFixedParserTest {

  private RelativeToFixedParser rtfp = new RelativeToFixedParser();
  private CalendarUtil calendarUtil = new CalendarUtil();

  @Test
  public void testEmpty() {
    Set<Holiday> holidays = new HashSet<>();
    Holidays config = new Holidays();
    rtfp.parse(2010, holidays, config);
    assertTrue(holidays.isEmpty(), "Expected to be empty.");
  }

  @Test
  public void testInvalid() {
    Set<Holiday> holidays = new HashSet<>();
    Holidays config = new Holidays();
    RelativeToFixed rule = new RelativeToFixed();
    rule.setValidFrom(2011);
    config.getRelativeToFixed().add(rule);
    rtfp.parse(2010, holidays, config);
    assertTrue(holidays.isEmpty(), "Expected to be empty.");
  }

  @Test
  public void testWeekday() {
    Set<Holiday> holidays = new HashSet<>();
    Holidays config = new Holidays();
    RelativeToFixed rule = new RelativeToFixed();
    rule.setWeekday(Weekday.THURSDAY);
    rule.setWhen(When.AFTER);
    Fixed date = new Fixed();
    date.setDay(5);
    date.setMonth(Month.AUGUST);
    rule.setDate(date);
    config.getRelativeToFixed().add(rule);
    rtfp.parse(2011, holidays, config);
    assertEquals(1, holidays.size(), "Number of holidays wrong.");
    assertEquals(calendarUtil.create(2011, 8, 11), holidays.iterator().next().getDate(), "Wrong date.");
  }

  @Test
  public void testSameWeekday() {
    Set<Holiday> holidays = new HashSet<>();
    Holidays config = new Holidays();
    RelativeToFixed rule = new RelativeToFixed();
    rule.setWeekday(Weekday.WEDNESDAY);
    rule.setWhen(When.BEFORE);
    Fixed date = new Fixed();
    date.setDay(23);
    date.setMonth(Month.NOVEMBER);
    rule.setDate(date);
    config.getRelativeToFixed().add(rule);
    rtfp.parse(2016, holidays, config);
    assertEquals(1, holidays.size(), "Number of holidays wrong.");
    assertEquals(calendarUtil.create(2016, 11, 16), holidays.iterator().next().getDate(), "Wrong date.");
  }

  @Test
  public void testNumberOfDays() {
    Set<Holiday> holidays = new HashSet<>();
    Holidays config = new Holidays();
    RelativeToFixed rule = new RelativeToFixed();
    rule.setDays(3);
    rule.setWhen(When.BEFORE);
    Fixed date = new Fixed();
    date.setDay(5);
    date.setMonth(Month.AUGUST);
    rule.setDate(date);
    config.getRelativeToFixed().add(rule);
    rtfp.parse(2011, holidays, config);
    assertEquals(1, holidays.size(), "Number of holidays wrong.");
    assertEquals(calendarUtil.create(2011, 8, 2), holidays.iterator().next().getDate(), "Wrong date.");
  }

}
