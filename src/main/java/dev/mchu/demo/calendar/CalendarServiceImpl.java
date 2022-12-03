package dev.mchu.demo.calendar;

import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.filter.FilterExpression;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CalendarServiceImpl implements CalendarService{
    @Override
    public Flux<Event> getEventsByCalendar(Calendar calendar) {
        try {
            InputStream calendarIs = new ClassPathResource("/calendars/th_th.ics").getInputStream();
            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar cal = builder.build(calendarIs);

            // get all event
            List<CalendarComponent> events = cal.getComponents(Component.VEVENT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            List<Event>  eventList = events.stream()
                    .map(calendarComponent -> {
                        Optional<Property> summaryProp = calendarComponent.getProperty(Property.SUMMARY);
                        Optional<Property> startDateProp = calendarComponent.getProperty(Property.DTSTART);

                        String summary = summaryProp.get().getValue();
                        LocalDate startDate = LocalDate.parse(startDateProp.get().getValue(), formatter);
                        log.debug("start date: {}", startDate);

                        return new Event(summary,startDate);
                    }).collect(Collectors.toList());

            return Flux.fromIterable(eventList);

        } catch (Exception e){
            log.error("{}", e);
        }
        return null;
    }
}
