package ru.apertum.qsystem.custom.testapi.common.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Тут локализация на разные языки текста услуги.
 *
 * @author Evgeniy Egorov
 */
public class QServiceLang implements Serializable, IidGetter {


    @Expose
    @SerializedName("id")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * Заголовок окна при вводе на пункте регистрации клиентом каких-то
     * данных перед постановкой в очередь после выбора услуги.
     * Также печатается на талоне рядом с введенными данными.
     */
    @Expose
    @SerializedName("input_caption")
    private String inputCaption = "";

    public String getInputCaption() {
        return inputCaption;
    }

    public void setInputCaption(String inputCaption) {
        this.inputCaption = inputCaption;
    }

    /**
     * html текст информационного сообщения перед постановкой в очередь.
     * Если этот параметр пустой, то не требуется показывать информационную напоминалку на пункте регистрации
     */
    @Expose
    @SerializedName("pre_info_html")
    private String preInfoHtml = "";

    public String getPreInfoHtml() {
        return preInfoHtml;
    }

    public void setPreInfoHtml(String preInfoHtml) {
        this.preInfoHtml = preInfoHtml;
    }

    /**
     * текст для печати при необходимости перед постановкой в очередь.
     */
    @Expose
    @SerializedName("pre_info_print_text")
    private String preInfoPrintText = "";

    public String getPreInfoPrintText() {
        return preInfoPrintText;
    }

    public void setPreInfoPrintText(String preInfoPrintText) {
        this.preInfoPrintText = preInfoPrintText;
    }

    /**
     * текст для печати при необходимости перед постановкой в очередь.
     */
    @Expose
    @SerializedName("ticket_text")
    private String ticketText = "";

    public String getTicketText() {
        return ticketText;
    }

    public void setTicketText(String ticketText) {
        this.ticketText = ticketText;
    }

    /**
     * текст для вывода на главное табло в шаблоны панели вызванного и третью колонку пользователя.
     */
    @Expose
    @SerializedName("tablo_text")
    private String tabloText = "";

    /**
     * текст для вывода на главное табло в шаблоны панели вызванного и третью колонку пользователя.
     *
     * @return строчеп из БД
     */
    public String getTabloText() {
        return tabloText;
    }

    public void setTabloText(String tabloText) {
        this.tabloText = tabloText;
    }

    public QServiceLang() {
        super();
    }

    @Override
    public String toString() {
        return getLang() + " " + getName();
    }

    /**
     * Описание услуги.
     */
    @Expose
    @SerializedName("description")
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Идентификатор языка, вообще любой текст, хоть кaрело-финский заводи.
     */
    @Expose
    @SerializedName("lang")
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Наименование услуги.
     */
    @Expose
    @SerializedName("name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Надпись на кнопке услуги.
     */
    @Expose
    @SerializedName("buttonText")
    private String buttonText;

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    private QService service;

    public QService getService() {
        return service;
    }

    public void setService(QService service) {
        this.service = service;
    }
}

