package dev.mchu.demo.calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Component
public class CalendarRouter {
    @Bean
    public RouterFunction<ServerResponse> calendarRoute(CalendarHandler handler){
        return route()
                .GET("/calendars", accept(MediaType.APPLICATION_JSON), handler::listCalendar)
                .GET("/calendars/{calendarId}/events", accept(MediaType.APPLICATION_JSON), handler::listEvents)
                .build();
    }
}
