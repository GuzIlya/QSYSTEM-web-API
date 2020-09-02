package ru.apertum.qsystem.custom.testapi.common.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class QBreaks implements IidGetter, Serializable {

    public QBreaks() {
        // for marshall.
    }

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Наименование плана перерывов.
     */
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Set<QBreak> breaks = new HashSet<>();

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String delim = "";
        for (QBreak qBreak : breaks) {
            builder.append(delim).append(qBreak);
            delim = ", ";
        }
        return name + "(" + builder.toString() + ")";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QBreaks other = (QBreaks) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.breaks.size(), other.breaks.size());
    }

    /**
     * Проверить что сейчас. Уже перерыв?
     *
     * @return если да, то вернем перерыв, если нет, то null.
     */
    public QBreak isNow() {
        return isNow(new Date());
    }

    /**
     * Проверить что время из даты попало в перерыв.
     *
     * @param date время из даты на проверку попало в перерыв или нет.
     * @return если да, то вернем перерыв, если нет, то null.
     */
    public QBreak isNow(Date date) {
        for (QBreak brk : breaks) {
            if (brk.isNow(date)) {
                return brk;
            }
        }
        return null;
    }

}
