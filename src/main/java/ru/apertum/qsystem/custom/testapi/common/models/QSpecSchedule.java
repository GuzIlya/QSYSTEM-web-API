package ru.apertum.qsystem.custom.testapi.common.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс для специального календаря, который на период в расписании расписания.
 *
 * @author Evgeniy Egorov
 */

public class QSpecSchedule implements IidGetter, Serializable {

    public QSpecSchedule() {
        // for gson marshalling.
    }

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return getId() == null ? "NEW" : schedule.getName();
    }

    @Override
    public String toString() {
        final SimpleDateFormat formatDdMmYyyy = new SimpleDateFormat("dd.MM.yyyy");
        return formatDdMmYyyy.format(getFrom()) + " - " + formatDdMmYyyy.format(getTo()) + "   " + getSchedule();
    }


    private QSchedule schedule;

    public QSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(QSchedule schedule) {
        this.schedule = schedule;
    }

    //MOSCOW1

    private QCalendar calendar;

    public QCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(QCalendar calendar) {
        this.calendar = calendar;
    }


    private Date from;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    private Date to;

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

}