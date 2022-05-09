package de.jollyday.tests;

import de.jollyday.tests.base.AbstractCountryTestBase;
import org.junit.jupiter.api.Test;

public class HolidayBGTest extends AbstractCountryTestBase {

  private static final String ISO_CODE = "bg";
  private static final int YEAR = 2010;

  @Test
  public void testManagerBGStructure() throws Exception {
    validateCalendarData(ISO_CODE, YEAR);
  }

}
