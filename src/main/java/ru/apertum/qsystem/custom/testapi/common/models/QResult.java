package ru.apertum.qsystem.custom.testapi.common.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Возможный результатов обработки кастомера.
 *
 * @author Evgeniy Egorov
 */
public class QResult implements IidGetter, Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Текст результата работы клиента с пользователем.
     */
    @Expose
    @SerializedName("name")
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
