package dev.mchu.demo.calendar;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface CalendarRepository extends ReactiveCrudRepository<Calendar, Long> {
}
