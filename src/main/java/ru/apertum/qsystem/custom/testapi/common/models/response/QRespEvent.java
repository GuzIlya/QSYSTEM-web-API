package ru.apertum.qsystem.custom.testapi.common.models.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Событие оставления отзыва.
 *
 * @author Evgeniy Egorov
 */
public class QRespEvent implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Время оставления отзыва.
     */
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Какой отзыв оставили.
     */
    private Long respID;

    public Long getRespID() {
        return respID;
    }

    public void setRespID(Long respID) {
        this.respID = respID;
    }

    /**
     * По какому оператору отзыв оставили.
     */
    private Long userID;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * По Какой услуге отзыв оставили.
     */
    private Long serviceID;

    public Long getServiceID() {
        return serviceID;
    }

    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * По какому посетителю отзыв оставили.
     */
    private Long clientID;

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    /**
     * Данные кастомера, который отзыв оставил.
     */
    private String clientData = "";

    public String getClientData() {
        return clientData;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    /**
     * Комментарии кастомера, который отзыв оставил и его попросили их оставить в настройках отзыва.
     */
    private String comment = "";

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
