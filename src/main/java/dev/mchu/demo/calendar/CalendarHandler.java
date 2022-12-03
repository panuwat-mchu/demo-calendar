package dev.mchu.demo.calendar;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class CalendarHandler {

    private final CalendarRepository repository;
    private final CalendarService calendarService;

    public CalendarHandler(CalendarRepository repository, CalendarService calendarService) {
        this.repository = repository;
        this.calendarService = calendarService;
    }

    public Mono<ServerResponse> listCalendar(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll(), Calendar.class);
    }

    public Mono<ServerResponse> listEvents(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(calendarService.getEventsByCalendar(null), Event.class);
    }
}
