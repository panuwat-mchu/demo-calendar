DROP TABLE calendars IF EXISTS;
CREATE TABLE `calendars` (
    `id` varchar(24) not null,
    `title` varchar(190) not null,
    `ics` text null
);