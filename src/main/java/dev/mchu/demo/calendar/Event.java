package dev.mchu.demo.calendar;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class Event {
    private String summary;
    private LocalDate startDate;

    public Event(String summary, LocalDate startDate) {
        this.summary = summary;
        this.startDate = startDate;
    }
}
