package ru.apertum.qsystem.custom.testapi.common.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.custom.testapi.common.Uses;

import java.io.Serializable;

/**
 * Это класс для загрузки набора сервисов обслуживаемых юзером. Ничего хитрого, связь многие-ко-многим + коэффициент участия. Так сделано потому что у сервисов
 * нет привязки к юзерам, эта привязка вроде как односторонняя и еще имеется поле "коэффициент участия", которое будет игнориться при связи "многие-ко-многим".
 * Текстовое название услуги подтягиваеццо отдельно.
 *
 * @author Evgeniy Egorov
 */
public class QPlanService implements Serializable {

    public QPlanService() {
    }

    /**
     * Это класс для загрузки набора сервисов обслуживаемых юзером.
     */
    public QPlanService(QService service, QUser user, Integer coefficient) {
        this.coefficient = coefficient;
        this.service = service;
        this.user = user;
    }

    //@Id
    @Expose
    @SerializedName("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Коэфф. степени участия. По умолчанию основной. низкий/основной/VIP.
     */
    @Expose
    @SerializedName("coeff")
    protected Integer coefficient = 1;

    public Integer getCoefficient() {
        return coefficient;
    }

    @Expose
    @SerializedName("flex")
    private Boolean flexibleCoef = false;

    public Boolean getFlexibleCoef() {
        return flexibleCoef;
    }

    public void setFlexibleCoef(Boolean flexibleCoef) {
        this.flexibleCoef = flexibleCoef;
    }

    @Expose
    @SerializedName("flex_invt")
    private Boolean flexibleInvitation = false;

    public Boolean getFlexibleInvitation() {
        return flexibleInvitation;
    }

    public void setFlexibleInvitation(Boolean flexibleInvitation) {
        this.flexibleInvitation = flexibleInvitation;
    }


    /**
     * Соответствие услуги.
     */
    @Expose
    @SerializedName("service")
    private QService service;

    public QService getService() {
        return service;
    }

    public void setService(QService service) {
        this.service = service;
    }

    /**
     * Соответствие пользователя.
     */
    private QUser user;

    //@ManyToOne()
    //@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    public QUser getUser() {
        return user;
    }

    public void setUser(QUser user) {
        this.user = user;
    }

    //******************************************************************************************************************
    //*******            Статистика             *******************************************
    //******************************************************************************************************************
    private int worked = 0;
    private long avgWork = 0;// в минутах
    private int killed = 0;
    private long avgWait = 0;// в минутах

    /**
     * В минутах.
     *
     * @return среднее время ожидания кастомером.
     */
    public long getAvgWait() {
        return avgWait;
    }

    private int waiters = 0;

    /**
     * Среднее.
     */
    public void setAvgWait(long avgWait) {
        if (avgWait == 0) {
            waiters = 0;
        }
        this.avgWait = avgWait;
    }

    /**
     * В минутах.
     *
     * @return среднее время работы с кастомерами.
     */
    public long getAvgWork() {
        return avgWork;
    }

    public void setAvgWork(long avgWork) {
        this.avgWork = avgWork;
    }

    public int getKilled() {
        return killed;
    }

    public void setKilled(int killed) {
        this.killed = killed;
    }

    public int getWorked() {
        return worked;
    }

    public void setWorked(int worked) {
        this.worked = worked;
    }

    public synchronized void inkKilled() {
        this.killed++;
    }

    /**
     * время работы с кастомером, с которым работали.
     *
     * @param workTime время работы с кастомером, с которым работали. в милисекундах.
     */
    public synchronized void inkWorked(long workTime) {
        this.worked++;
        avgWork = (avgWork * (worked - 1) + workTime / 60000) / worked;
        avgWork = avgWork == 0 ? 1 : avgWork;
    }

    /**
     * время ожидания кастомером, с которым начали работать.
     *
     * @param waitTime время ожидания кастомером, с которым начали работать. в милисекундах
     */
    public synchronized void upWait(long waitTime) {
        waiters++;
        avgWait = (avgWait * (waiters - 1) + waitTime / 60000) / waiters;
        avgWait = avgWait == 0 ? 1 : avgWait;
    }
}
