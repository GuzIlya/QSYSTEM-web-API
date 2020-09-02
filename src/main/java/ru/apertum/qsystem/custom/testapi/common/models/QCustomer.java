package ru.apertum.qsystem.custom.testapi.common.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.extern.log4j.Log4j2;
import ru.apertum.qsystem.custom.testapi.common.CustomerState;
import ru.apertum.qsystem.custom.testapi.common.exceptions.ServerException;
import ru.apertum.qsystem.custom.testapi.common.models.response.QRespEvent;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * Реализация клиета Наипростейший "очередник". Используется для организации простой очереди. Если используется СУБД, то сохранение
 * происходит при смене ссостояния. ВАЖНО! Всегда изменяйте статус кастомера при его изменении, особенно при его удалении.
 *
 * @author Evgeniy Egorov
 */

@SuppressWarnings("squid:S1319")
@Log4j2
public final class QCustomer implements Serializable {

    public QCustomer() {
        id = new Date().getTime();
    }

    /**
     * создаем клиента имея только его номер в очереди. Префикс не определен, т.к. еще не знаем об услуге куда его поставить. Присвоем кастомену услугу -
     * присвоются и ее атрибуты.
     *
     * @param number номер клиента в очереди
     */
    public QCustomer(int number) {
        this.number = number;
        id = new Date().getTime();
        setStandTime(new Date()); // действия по инициализации при постановке
        // все остальные всойства кастомера об услуге куда попал проставятся в самой услуге при помещении кастомера в нее
        log.debug("Создали кастомера с номером " + number);
    }

    @Expose
    @SerializedName("id")
    private Long id = new Date().getTime();

    public void setUser(QUser user) {
        this.user = user;
        log.debug("Клиенту \"" + (user == null ? " юзера нету, еще он его не вызывал\"" : " опредилили юзера \"" + user.getName() + "\""));
    }
    //@GeneratedValue(strategy = GenerationType.AUTO) простаяляем уникальный номер времени создания.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * АТРИБУТЫ "ОЧЕРЕДНИКА" персональный номер, именно по нему система ведет учет и управление очередниками номер - целое число Номер клиента. Вообще, он
     * выдается по порядку. Но если номером является нечто введенное пользователем, то этот номер равен -1.
     */
    @Expose
    @SerializedName("number")
    private Integer number;

    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * Номер клиента. Вообще, он выдается по порядку. Но если номером является нечто введенное пользователем, то этот номер равен -1.
     *
     * @return Номер попорядку, либо -1 в случае номера как введенного пользователем текста.
     */
    public int getNumber() {
        return number;
    }

    @Expose
    @SerializedName("stateIn")
    private Integer stateIn;

    public Integer getStateIn() {
        return stateIn;
    }

    public void setStateIn(Integer stateIn) {
        this.stateIn = stateIn;
    }

    /**
     * АТРИБУТЫ "ОЧЕРЕДНИКА" состояние кастомера, именно по нему система знает что сейчас происходит с кастомером Это состояние менять только если кастомер уже
     * готов к этому и все другие параметры у него заполнены. Если данные пишутся в БД, то только по состоянию завершенности обработки над ним. Так что если
     * какая-то итерация закончена и про кастомера должно занестись в БД, то как и надо выставлять что кастомер ЗАКОНЧИЛ обрабатываться, а уж потом менять ,
     * если надо, его атрибуты и менять состояние, например на РЕДИРЕКТЕННОГО.
     * <br>
     * состояние клиента
     *
     * @see ru.apertum.qsystem.custom.testapi.common.Uses
     */
    @Expose
    @SerializedName("state")
    private CustomerState state;

    private final LinkedList<QRespEvent> resps = new LinkedList<>();

    public void addNewRespEvent(QRespEvent event) {
        resps.add(event);
    }

    public CustomerState getState() {
        return state;
    }

    /**
     * ПРИОРИТЕТ "ОЧЕРЕДНИКА".
     */
    @Expose
    @SerializedName("priority")
    private Integer priority;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QCustomer) {
            final QCustomer cust = (QCustomer) obj;
            return this.getSemiNumber().equals(cust.getSemiNumber()) && this.getId().equals(cust.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (getSemiNumber() + getId().toString()).hashCode();
    }

    /**
     * К какой услуге стоит. Нужно для статистики.
     */
    @Expose
    @SerializedName("to_service")
    private QService service;

    public QService getService() {
        return service;
    }




    /**
     * Результат работы с пользователем.
     */
    private QResult result;

    public QResult getResult() {
        return result;
    }

    /**
     * Результат раюоты с кастомером.
     *
     * @param result сам результат из списка результатов.
     */
    public void setResult(QResult result) {
        this.result = result;
        if (result == null) {
            log.debug("Обозначать результат работы с кастомером не требуется");
        } else {
            log.debug("Обозначили результат работы с кастомером: \"" + result.getName() + "\"");
        }
    }

    /**
     * Кто его обрабатывает. Нужно для статистики.
     */
    @Expose
    @SerializedName("from_user")
    private QUser user;

    public QUser getUser() {
        return user;
    }


    /**
     * Префикс услуги, к которой стоит кастомер.
     * Строка префикса.
     */
    @Expose
    @SerializedName("prefix")
    private String prefix;

    public String getPrefix() {
        return prefix;
    }



    /**
     * Номер талона без разделителя.
     *
     * @return Номер талона без разделителя.
     */
    @Transient()
    public String getSemiNumber() {
        return getPrefix() + (getNumber() < 1 ? "" : (getNumber()));
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? "" : prefix;
    }

    @Expose
    @SerializedName("stand_time")
    private Date standTime;

    public Date getStandTime() {
        return standTime;
    }

    public void setStandTime(Date date) {
        this.standTime = date;
    }

    @Expose
    @SerializedName("start_time")
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date date) {
        this.startTime = date;
    }

    private Date callTime;

    public void setCallTime(Date date) {
        this.callTime = date;
    }

    @Transient
    public Date getCallTime() {
        return callTime;
    }

    @Expose
    @SerializedName("finish_time")
    private Date finishTime;

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date date) {
        this.finishTime = date;
    }

    @Expose
    @SerializedName("inputData")
    private String inputData = "";

    /**
     * Введенные кастомером данные на пункте регистрации.
     *
     * @return
     */
    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    /**
     * Список услуг в которые необходимо вернуться после редиректа Новые услуги для возврата добвляются в начало списка. При возврате берем первую из списка и
     * удаляем ее.
     */
    private final LinkedList<QService> serviceBack = new LinkedList<>();

    /**
     * При редиректе если есть возврат. то добавим услугу для возврата
     *
     * @param service в эту услугу нужен возврат
     */
    public void addServiceForBack(QService service) {
        serviceBack.addFirst(service);
        needBack = !serviceBack.isEmpty();
    }

    /**
     * Куда вернуть если работу закончили но кастомер редиректенный.
     *
     * @return вернуть в эту услугу.
     */
    @Transient
    public QService getServiceForBack() {
        needBack = serviceBack.size() > 1;
        return serviceBack.pollFirst();
    }

    @Expose
    @SerializedName("need_back")
    private boolean needBack = false;

    public boolean needBack() {
        return needBack;
    }

    /**
     * Комментариии юзеров о кастомере при редиректе и отправки в отложенные.
     */
    @Expose
    @SerializedName("temp_comments")
    private String tempComments = "";

    @Transient
    public String getTempComments() {
        return tempComments;
    }

    public void setTempComments(String tempComments) {
        this.tempComments = tempComments;
    }

    /**
     * Статус отложенного.
     */
    @Expose
    @SerializedName("post_status")
    private String postponedStatus = "";

    @Transient
    public String getPostponedStatus() {
        return postponedStatus;
    }

    public void setPostponedStatus(String postponedStatus) {
        this.postponedStatus = postponedStatus;
    }

    /**
     * Период отложенности в минутах. 0 - бессрочно;
     */
    @Expose
    @SerializedName("postpone_period")
    private int postponPeriod = 0;

    @Transient
    public int getPostponPeriod() {
        return postponPeriod;
    }

    /**
     * ID того кто видит отложенного, NULL для всех.
     */
    @Expose
    @SerializedName("is_mine")
    private Long isMine = null;

    @Transient
    public Long getIsMine() {
        return isMine;
    }

    public void setIsMine(Long userId) {
        this.isMine = userId;
    }

    /**
     * Количество повторных вызовов этого клиента.
     */
    @Expose
    @SerializedName("recall_cnt")
    private Integer recallCount = 0;

    @Transient
    public Integer getRecallCount() {
        return recallCount;
    }

    public void setRecallCount(Integer recallCount) {
        this.recallCount = recallCount;
    }

    public void upRecallCount() {
        this.recallCount++;
    }

    /**
     * Установим период на который отложили.
     *
     * @param postponPeriod в минутах.
     */
    public void setPostponPeriod(int postponPeriod) {
        this.postponPeriod = postponPeriod;
        startPontpone = System.currentTimeMillis();
        finishPontpone = startPontpone + postponPeriod * 60 * 1000;
    }

    /**
     * Когда был отложен в милисекундах.
     */
    @Expose
    @SerializedName("start_postpone_period")
    private long startPontpone = 0;
    @Expose
    @SerializedName("finish_postpone_period")
    private long finishPontpone = 0;

    @Transient
    public long getStartPontpone() {
        return startPontpone;
    }

    @Transient
    public long getFinishPontpone() {
        return finishPontpone;
    }


    @Expose
    @SerializedName("complex_id")
    public LinkedList<LinkedList<LinkedList<Long>>> complexId = new LinkedList<>();

    @Transient
    public LinkedList<LinkedList<LinkedList<Long>>> getComplexId() {
        return complexId;
    }

    public void setComplexId(LinkedList<LinkedList<LinkedList<Long>>> complexId) {
        this.complexId = complexId;
    }

    @Transient
    public Integer getWaitingMinutes() {
        return Math.toIntExact((System.currentTimeMillis() - getStandTime().getTime()) / 1000 / 60 + 1);
    }

    /**
     * Та локаль, которую кастомер выбрал при выборе услуги на киоске.
     */
    @Expose
    @SerializedName("lng")
    private String language;

    @Transient
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

