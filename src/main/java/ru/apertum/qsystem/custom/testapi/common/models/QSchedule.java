package ru.apertum.qsystem.custom.testapi.common.models;

import lombok.Data;
import ru.apertum.qsystem.custom.testapi.common.exceptions.ServerException;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
public class QSchedule implements IidGetter, Serializable {

    public QSchedule() {
        // for Hibernate.
    }


    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = new Date().getTime();

    /**
     * Наименование плана.
     */
    private String name;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof QSchedule)) {
            throw new TypeNotPresentException("Неправильный тип сравнения для", new ServerException("Неправильный для сравнения тип"));
        }
        return id.equals(((QSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return (int) (this.id != null ? this.id : 0);
    }


    @Override
    public String toString() {
        return name;
    }

    /**
     * Тип плана 0 - недельный 1 - четные/нечетные дни.
     */
    private Integer type;

    /**
     * Начало и конец рабочего дня, к примеру.
     */
    public static class Interval {

        public final Date start;
        public final Date finish;
        public final int startHours;
        public final int startMin;
        public final int finishHours;
        public final int finishMin;

        /**
         * Начало и конец рабочего дня, к примеру.
         */
        public Interval(Date start, Date finish) {
            if (start == null || finish == null) {
                this.start = null;
                this.finish = null;
                startHours = -1;
                startMin = -1;
                finishHours = -1;
                finishMin = -1;
            } else {
                if (finish.before(start)) {
                    throw new ServerException("Finish date " + finish + " before than start date " + start);
                }
                this.start = start;
                this.finish = finish;
                final GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(start);
                startHours = gc.get(Calendar.HOUR_OF_DAY);
                startMin = gc.get(Calendar.MINUTE);
                gc.setTime(finish);
                finishHours = gc.get(Calendar.HOUR_OF_DAY);
                finishMin = gc.get(Calendar.MINUTE);
            }
        }

        public int diff() {
            return finishHours * 60 + finishMin - startHours * 60 - startMin;
        }

        @Override
        public String toString() {
            return startHours + ":" + startMin + "-" + finishHours + ":" + finishMin;
        }

        @Override
        public int hashCode() {
            return startHours + startMin + finishHours + finishMin + diff();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof Interval)) {
                throw new TypeNotPresentException("Неправильный тип для сравнения", new ServerException("Неправильный тип для сравнения"));
            }
            return startHours == ((Interval) o).startHours
                    && startMin == ((Interval) o).startMin
                    && finishHours == ((Interval) o).finishHours
                    && finishMin == ((Interval) o).finishMin;
        }
    }

    /**
     * Определим время начала и kонца работы на этот день.
     *
     * @param date на этот день
     * @return время начала и kонца работы
     */
    public Interval getWorkInterval(Date date) {
        // Определим время начала и kонца работы на этот день
        final GregorianCalendar gcDay = new GregorianCalendar();
        gcDay.setTime(date);
        final Interval in;
        if (getType() == 1) {
            if (0 == (gcDay.get(GregorianCalendar.DAY_OF_MONTH) % 2)) {
                in = new Interval(getTimeBegin1(), getTimeEnd1());
            } else {
                in = new Interval(getTimeBegin2(), getTimeEnd2());
            }
        } else {
            switch (gcDay.get(GregorianCalendar.DAY_OF_WEEK)) {
                case 2:
                    in = new Interval(getTimeBegin1(), getTimeEnd1());
                    break;
                case 3:
                    in = new Interval(getTimeBegin2(), getTimeEnd2());
                    break;
                case 4:
                    in = new Interval(getTimeBegin3(), getTimeEnd3());
                    break;
                case 5:
                    in = new Interval(getTimeBegin4(), getTimeEnd4());
                    break;
                case 6:
                    in = new Interval(getTimeBegin5(), getTimeEnd5());
                    break;
                case 7:
                    in = new Interval(getTimeBegin6(), getTimeEnd6());
                    break;
                case 1:
                    in = new Interval(getTimeBegin7(), getTimeEnd7());
                    break;
                default:
                    throw new ServerException("32-е мая!");
            }
        }// Определили начало и конец рабочего дня на сегодня
        final GregorianCalendar gc = new GregorianCalendar();
        if (in.start == null || in.finish == null) {
            return in;
        } else {
            gc.setTime(in.start);
            gcDay.set(GregorianCalendar.HOUR_OF_DAY, gc.get(GregorianCalendar.HOUR_OF_DAY));
            gcDay.set(GregorianCalendar.MINUTE, gc.get(GregorianCalendar.MINUTE));
            gcDay.set(GregorianCalendar.SECOND, 0);
            final Date ds = gcDay.getTime();
            gc.setTime(in.finish);
            gcDay.setTime(date);
            gcDay.set(GregorianCalendar.HOUR_OF_DAY, gc.get(GregorianCalendar.HOUR_OF_DAY));
            gcDay.set(GregorianCalendar.MINUTE, gc.get(GregorianCalendar.MINUTE));
            gcDay.set(GregorianCalendar.SECOND, 0);
            return new Interval(ds, gcDay.getTime());
        }
    }

    /**
     * Проверка на перерыв. К примеру. В перерывах нет возможности записываться, по этому это время не поедет в пункт регистрации. Есть расписание, у него на
     * каждый день список перерывов. Папало ли время в перерыв на ту дату
     *
     * @param date проверка этой даты на перерыв.
     * @return да или нет.
     */
    public boolean inBreak(Date date) {
        // Проверка на перерыв. В перерывах нет возможности записываться, по этому это время не поедет в пункт регистрации
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int ii = gc.get(GregorianCalendar.DAY_OF_WEEK) - 1;
        if (ii < 1) {
            ii = 7;
        }
        final QBreaks breaks;
        switch (ii) {
            case 1:
                breaks = getBreaks1();
                break;
            case 2:
                breaks = getBreaks2();
                break;
            case 3:
                breaks = getBreaks3();
                break;
            case 4:
                breaks = getBreaks4();
                break;
            case 5:
                breaks = getBreaks5();
                break;
            case 6:
                breaks = getBreaks6();
                break;
            case 7:
                breaks = getBreaks7();
                break;
            default:
                throw new AssertionError();
        }
        return breaks != null && breaks.isNow(date) != null;// может вообще перерывов нет
    }

    /**
     * Проверка на перерыв. К примеру. В перерывах нет возможности записываться, по этому это время не поедет в пункт регистрации. Есть расписание, у него на
     * каждый день список перерывов. Папал ли интервал(пересечение) в перерыв на ту дату
     *
     * @param interval проверка этго интервала на перерыв
     * @return да или нет
     */
    public boolean inBreak(Interval interval) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(interval.finish);
        gc.add(GregorianCalendar.SECOND, -3);
        return inBreak(interval.start) || inBreak(gc.getTime());
    }

    /**
     * Проверка на перерыв. К примеру. В перерывах нет возможности записываться, по этому это время не поедет в пункт регистрации. Есть расписание, у него на
     * каждый день список перерывов. Папал ли интервал(пересечение) в перерыв на ту дату
     *
     * @param start  начало этoго интервала на перерыв
     * @param finish конец этoго интервала на перерыв
     * @return да или нет
     */
    public boolean inBreak(Date start, Date finish) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(finish);
        gc.add(GregorianCalendar.SECOND, -3);
        return inBreak(start) || inBreak(gc.getTime());
    }

    /**
     * Время начала работы в первый день недели или в нечетный день, зависит от type.
     */
    private Date timeBegin1;

    /**
     * Время завершения работы в первый день недели или в нечетный день, зависит от type.
     */
    private Date timeEnd1;

    private Date timeBegin2;

    private Date timeEnd2;

    private Date timeBegin3;

    private Date timeEnd3;

    private Date timeBegin4;

    private Date timeEnd4;

    private Date timeBegin5;

    private Date timeEnd5;

    private Date timeBegin6;

    private Date timeEnd6;

    private Date timeBegin7;

    private Date timeEnd7;

    /**
     * Перерывы.
     *
     * @return Синглтон с перерывами.
     */
    public QBreaks getBreaks() {
        GregorianCalendar gc = new GregorianCalendar();
        final int day = gc.get(GregorianCalendar.DAY_OF_WEEK) - 1 < 1 ? 7 : (gc.get(GregorianCalendar.DAY_OF_WEEK) - 1);
        switch (day) {
            case 1:
                return getBreaks1();
            case 2:
                return getBreaks2();
            case 3:
                return getBreaks3();
            case 4:
                return getBreaks4();
            case 5:
                return getBreaks5();
            case 6:
                return getBreaks6();
            case 7:
                return getBreaks7();
            default:
                throw new AssertionError(day);
        }
    }

    private QBreaks breaks1;
    private QBreaks breaks2;
    private QBreaks breaks3;
    private QBreaks breaks4;
    private QBreaks breaks5;
    private QBreaks breaks6;
    private QBreaks breaks7;
}
