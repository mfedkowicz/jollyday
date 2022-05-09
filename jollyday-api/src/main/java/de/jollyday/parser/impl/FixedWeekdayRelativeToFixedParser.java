package de.jollyday.parser.impl;

import de.jollyday.Holiday;
import de.jollyday.parser.functions.CreateHoliday;
import de.jollyday.parser.functions.FindWeekDayRelativeToDate;
import de.jollyday.parser.functions.FixedToLocalDate;
import de.jollyday.parser.predicates.ValidLimitation;
import de.jollyday.spi.FixedWeekdayRelativeToFixed;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Parses fixed weekday relative to fixed date.
 *
 * @author Sven Diedrichsen
 * @version $Id: $
 */
public class FixedWeekdayRelativeToFixedParser implements Function<Integer, List<Holiday>> {

  private final List<FixedWeekdayRelativeToFixed> fixedWeekdayRelativeToFixed;

  public FixedWeekdayRelativeToFixedParser(List<FixedWeekdayRelativeToFixed> fixedWeekdayRelativeToFixed) {
    this.fixedWeekdayRelativeToFixed = fixedWeekdayRelativeToFixed;
  }

  @Override
  public List<Holiday> apply(Integer year) {
    return fixedWeekdayRelativeToFixed.stream()
      .filter(new ValidLimitation(year))
      .map(fwrf -> new DescribedDateHolder(fwrf, new FindWeekDayRelativeToDate(new FixedToLocalDate(year).apply(fwrf.day())).apply(fwrf)))
      .map(holder -> new CreateHoliday(holder.getDate()).apply(holder.getDescribed()))
      .collect(toList());
  }
}

