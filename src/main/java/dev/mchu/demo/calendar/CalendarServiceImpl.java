package dev.mchu.demo.calendar;

import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.FilterExpression;
import net.fortuna.ical4j.filter.predicate.PeriodRule;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CalendarServiceImpl implements CalendarService{
    @Override
    public Flux<Event> getEventsByCalendar(Calendar cal) {
        try {
            InputStream calendarIs = new ClassPathResource("/calendars/th_th.ics").getInputStream();
            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar calendar = builder.build(calendarIs);

//            // filter events in the next week..
//            Predicate<VEvent> filter = FilterExpression.parse("dtstart > now() and dtstart <= endOfDay(+P1W)").;
//            List<VEvent> events = calendar.getComponents("vevent").stream().filter(filter).collect(Collectors.toList());


            // get all event
            List<CalendarComponent> events = calendar.getComponents(Component.VEVENT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            List<Event>  eventList = events.stream()
                    .map(calendarComponent -> {
                        Optional<Property> summaryProp = calendarComponent.getProperty(Property.SUMMARY);
                        Optional<Property> startDateProp = calendarComponent.getProperty(Property.DTSTART);

                        String summary = summaryProp.get().getValue();
                        LocalDate startDate = LocalDate.parse(startDateProp.get().getValue(), formatter);
                        log.debug("start date: {}", startDate);

                        return new Event(summary,startDate);
                    })
                    .filter(event -> {
                        LocalDate startDate = LocalDate.of(2023,12,4);
                        LocalDate endDate = LocalDate.of(2023,12,31);

                        return event.getStartDate().isAfter(startDate) && event.getStartDate().isBefore(endDate);
                    }).collect(Collectors.toList());

            return Flux.fromIterable(eventList);

        } catch (Exception e){
            log.error("{}", e);
        }
        return null;
    }
}
