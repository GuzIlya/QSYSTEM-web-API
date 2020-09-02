package ru.apertum.qsystem.custom.testapi.common.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class QBreak implements IidGetter, Serializable {

    /**
     * Конструктор перерыва. Вызывается из админки при добавлении.
     *
     * @param fromTime пирерыв от.
     * @param toTime   перерыв до.
     * @param hint     подсказка.
     * @param breaks   в какой список входит.
     */
    public QBreak(Date fromTime, Date toTime, String hint, QBreaks breaks) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.hint = hint;
        this.breaks = breaks;
    }

    public QBreak() {
    }

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Время начала перерыва.
     */
    @Getter
    @Setter
    private Date fromTime;

    /**
     * Время конца перерыва.
     */
    @Getter
    @Setter
    private Date toTime;

    /**
     * Время конца перерыва.
     */
    @Getter
    @Setter
    private String hint;

    /**
     * Формат даты.
     */
    public final SimpleDateFormat formatHhMm = new SimpleDateFormat("HH:mm");

    @Override
    public String getName() {
        return formatHhMm.format(fromTime) + "-" + formatHhMm.format(toTime);
    }

    @Override
    public String toString() {
        return formatHhMm.format(fromTime) + "-" + formatHhMm.format(toTime);
    }

    @Getter
    @Setter
    private QBreaks breaks;

    public long diff() {
        return getToTime().getTime() - getFromTime().getTime();
    }

    /**
     * Проверить, не попало ли время сейчас в перерыв. Смотрим только на часы и минуты.
     *
     * @return Перерыв или нет.
     */
    public boolean isNow() {
        return isNow(new Date());
    }

    /**
     * Проверить, не попало ли время из даты в перерыв. Смотрим только на часы и минуты.
     *
     * @param date Смотрим только на часы и минуты.
     * @return Перерыв или нет.
     */
    public boolean isNow(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int now = gc.get(Calendar.HOUR_OF_DAY) * 60 + gc.get(Calendar.MINUTE);
        gc.setTime(fromTime);
        int from = gc.get(Calendar.HOUR_OF_DAY) * 60 + gc.get(Calendar.MINUTE);
        gc.setTime(toTime);
        int to = gc.get(Calendar.HOUR_OF_DAY) * 60 + gc.get(Calendar.MINUTE);
        return from <= now && now <= to;
    }

}

