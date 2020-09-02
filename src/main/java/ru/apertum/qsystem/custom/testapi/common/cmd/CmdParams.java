package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class CmdParams {

    public static final String CMD = "cmd";

    public CmdParams() {
    }

    @Expose
    @SerializedName("service_id")
    public Long serviceId;
    @Expose
    @SerializedName("user_id")
    public Long userId;
    @Expose
    @SerializedName("pass")
    public String parolcheg;
    @Expose
    @SerializedName("priority")
    public Integer priority;
    @Expose
    @SerializedName("text_data")
    public String textData;
    @Expose
    @SerializedName("result_id")
    public Long resultId;
    @Expose
    @SerializedName("request_back")
    public Boolean requestBack;
    @Expose
    @SerializedName("drop_tickets_cnt")
    public Boolean dropTicketsCounter;
    /**
     * В качестве признака персональности едет ID юзера, чей отложенный должен быть, иначе null для общедоступности.
     */
    @Expose
    @SerializedName("is_only_mine")
    public Long isMine;
    @Expose
    @SerializedName("strict")
    public Boolean strict;
    @Expose
    @SerializedName("coeff")
    public Integer coeff;
    @Expose
    @SerializedName("date")
    public Long date;
    @Expose
    @SerializedName("customer_id")
    public Long customerId;
    @Expose
    @SerializedName("response_id")
    public Long responseId;
    @Expose
    @SerializedName("client_auth_id")
    public String clientAuthId;
    @Expose
    @SerializedName("info_item_name")
    public String infoItemName;
    @Expose
    @SerializedName("postponed_period")
    public Integer postponedPeriod;
    @Expose
    @SerializedName("comments")
    public String comments;
    @Expose
    @SerializedName("first_in_roll")
    public Integer first;
    @Expose
    @SerializedName("lastt_in_roll")
    public Integer last;
    @Expose
    @SerializedName("currentt_in_roll")
    public Integer current;
    @Expose
    @SerializedName("lng")
    public String language;
    /**
     * услуги, в которые пытаемся встать. Требует уточнения что это за трехмерный массив. Это пять списков. Первый это вольнопоследовательные услуги. Остальные
     * четыре это зависимопоследовательные услуги, т.е. пока один не закончится на другой не переходить. Что такое элемент списка. Это тоже список. Первый
     * элемент это та самая комплексная услуга(ID). А остальные это зависимости, т.е. если есть еще не оказанные услуги но назначенные, которые в зависимостях,
     * то их надо оказать.
     */
    @Expose
    @SerializedName("complex_id")
    public LinkedList<LinkedList<LinkedList<Long>>> complexId; // NOSONAR

    /**
     * Просто ассоциированный массив строк для передачи каких-то параметров. Пригодится для плагинов.
     */
    @Expose
    @SerializedName("strings_map")
    public Map<String, String> stringsMap;
}
