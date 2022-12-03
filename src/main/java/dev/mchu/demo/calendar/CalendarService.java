package dev.mchu.demo.calendar;

import reactor.core.publisher.Flux;

public interface CalendarService {
    Flux<Event> getEventsByCalendar(Calendar calendar);
}
