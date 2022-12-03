package dev.mchu.demo.calendar;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("CALENDARS")
public class Calendar {
    @Id
    @Column("ID")
    private Long id;
    @Column("TITLE")
    private String title;
    @Column("ICS")
    private String ics;
}
