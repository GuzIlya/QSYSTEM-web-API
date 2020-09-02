package ru.apertum.qsystem.custom.testapi.common.models;

import ru.apertum.qsystem.custom.testapi.common.exceptions.ServerException;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс календаря для расписания.
 *
 * @author Evgeniy Egorov
 */
public class QCalendar implements IidGetter, Serializable {

    public QCalendar() {
        //for HB.
    }

    /**
     * Создаем вместе с ID.
     *
     * @param id ID салендаря.
     */
    public QCalendar(Long id) {
        this.id = id;
    }

    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = new Date().getTime();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Наименование плана.
     */
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //MOSCOW1

    private List<QSpecSchedule> specSchedules = new LinkedList<>();

    public List<QSpecSchedule> getSpecSchedules() {
        return specSchedules;
    }

    public void setSpecSchedules(List<QSpecSchedule> specSchedules) {
        this.specSchedules = specSchedules;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof QCalendar)) {
            throw new TypeNotPresentException("Неправильный тип для сравнения", new ServerException("Неправильный тип для сравнения"));
        }
        return id.equals(((QCalendar) o).id);
    }

    @Override
    public int hashCode() {
        return (int) (this.id != null ? this.id : 0);
    }

    /**
     * Расписание на дату.
     *
     * @param forDate на эту дату.
     * @return расписание.
     */
    public QSchedule getSpecSchedule(Date forDate) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(forDate);
        gc.set(GregorianCalendar.HOUR_OF_DAY, 12);
        gc.set(GregorianCalendar.MINUTE, 0);
        forDate = gc.getTime();
        for (QSpecSchedule sps : getSpecSchedules()) {
            gc.setTime(sps.getFrom());
            gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc.set(GregorianCalendar.MINUTE, 0);
            final Date f = gc.getTime();
            gc.setTime(sps.getTo());
            gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc.set(GregorianCalendar.MINUTE, 59);
            if (f.before(forDate) && gc.getTime().after(forDate)) {
                return sps.getSchedule();
            }
        }
        return null;
    }
}
