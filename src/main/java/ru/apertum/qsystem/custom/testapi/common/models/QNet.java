package ru.apertum.qsystem.custom.testapi.common.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Сетевые настройки системы. Класс работает как с XML, так и с hibernate.
 *
 * @author Evgeniy Egorov
 */
public class QNet extends IdAutoGen implements Serializable {

    public QNet() {
        // for Hibernate.
    }

    /**
     * Порт сервера для приема команд.
     */
    private Integer serverPort;

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    /**
     * Порт сервера через который передается web содержимое отчетов.
     */
    private Integer webServerPort;

    public void setWebServerPort(Integer webServerPort) {
        this.webServerPort = webServerPort;
    }

    public Integer getWebServerPort() {
        return webServerPort;
    }

    /**
     * UDP Порт клиента, на который идет рассылка широковещательных пакетов.
     */
    private Integer clientPort;

    public void setClientPort(Integer clientPort) {
        this.clientPort = clientPort;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    /**
     * Время начала приема заявок на постановку в очередь.
     */
    private Date startTime;

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    /**
     * Время завершения приема заявок на постановку в очередь.
     */
    private Date finishTime;

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * Версия БД или конфигурационного файла. Для определения совместимости и возможности вариантов ардейта.
     */
    private String version = "Не присвоена";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /////////////////////////////////////////////////////////
    // Numeration
    /////////////////////////////////////////////////////////
    /**
     * Для настроек нурациии. Сдесь будут имеццо настройки для ведения нумерирования клиентов и формирования для них индикации на табло.
     */

    /**
     * Ограничение по максимально возможному номеру.
     */
    private Integer lastNumber;

    public Integer getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Integer lastNumber) {
        this.lastNumber = lastNumber;
    }

    /**
     * Количество доп. приоритетов
     */
    private Integer extPriorNumber;

    public Integer getExtPriorNumber() {
        return extPriorNumber;
    }

    public void setExtPriorNumber(Integer extPriorNumber) {
        this.extPriorNumber = extPriorNumber;
    }

    /**
     * Ограничение по минимально возможному номеру.
     */
    private Integer firstNumber;

    public Integer getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(Integer firstNumber) {
        this.firstNumber = firstNumber;
    }

    /**
     * 0 - общая нумерация, 1 - для каждой услуги своя нумерация.
     */
    private Boolean numering;

    public Boolean getNumering() {
        return numering;
    }

    public void setNumering(Boolean numering) {
        this.numering = numering;
    }

    /**
     * 0 - кабинет, 1 - окно, 2 - стойка.
     */
    private Integer point;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * 0 - нет оповещения, 1 - только сигнал, 2 - сигнал + голос.
     */
    private Integer sound;

    public Integer getSound() {
        return sound;
    }

    public void setSound(Integer sound) {
        this.sound = sound;
    }

    /**
     * 0 - по умолчанию, ну и т.д. по набору звуков.
     */
    private Integer voice = 0;

    public Integer getVoice() {
        return voice;
    }

    public void setVoice(Integer voice) {
        this.voice = voice;
    }

    /**
     * Время нахождения в блеклисте в минутах. 0 - попавшие в блекслист не блокируются.
     */
    private Integer blackTime = 0;

    public Integer getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(Integer blackTime) {
        this.blackTime = blackTime;
    }

    /**
     * Это ID филиала в котором установлена система. Нужно для идентификации в облачном сервисе.
     */
    private Long branchOfficeId;

    public Long getBranchOfficeId() {
        return branchOfficeId;
    }

    public void setBranchOfficeId(Long branchOfficeId) {
        this.branchOfficeId = branchOfficeId;
    }

    /**
     * URL облачного сервиса, к которому будет коннектится плагин Зачем это в БД? Да чо-бы проще было настраивать, а то придется как-то плагин отдельно
     * админить. не все догадаются.
     */
    private String skyServerUrl;

    public String getSkyServerUrl() {
        return skyServerUrl;
    }

    public void setSkyServerUrl(String skyServerUrl) {
        this.skyServerUrl = skyServerUrl;
    }

    /**
     * адрес зонного сервера отображения хода очереди, к которому будет коннектится плагин Зачем это в БД? Да чо-бы проще было настраивать, а то придется как-то
     * плагин отдельно админить. не все догадаются.
     */
    private String zoneBoardServAddr;

    public String getZoneBoardServAddr() {
        return zoneBoardServAddr;
    }


    private String[] zbsal = null;

    /**
     * Адреса зональничков.
     *
     * @return массив.
     */
    public String[] getZoneBoardServAddrList() {
        if (zbsal == null || zbsal.length == 0) {
            String boardServAddr = getZoneBoardServAddr();
            boardServAddr = boardServAddr.replaceAll("  ", " ");
            zbsal = boardServAddr.split(", |; |,|;| ");
        }
        return zbsal;
    }

    public void setZoneBoardServAddr(String zoneBoardServAddr) {
        this.zoneBoardServAddr = zoneBoardServAddr;
    }

    /**
     * Это порт зонального сервера отображения очереди на котором он будет принимать данные Нужно для идентификации в облачном сервисе.
     */
    private Integer zoneBoardServPort;

    public Integer getZoneBoardServPort() {
        return zoneBoardServPort;
    }

    public void setZoneBoardServPort(Integer zoneBoardServPort) {
        this.zoneBoardServPort = zoneBoardServPort;
    }

    /**
     * Это количество повторных вызовов посетителя перед тем как при очередном повторном вызове клиент будет удален.
     */
    private Integer limitRecall;

    public Integer getLimitRecall() {
        return limitRecall;
    }

    public void setLimitRecall(Integer limitRecall) {
        this.limitRecall = limitRecall;
    }

    /**
     * Свободное расположение кнопок на пункте регистрации.
     */
    private Boolean buttonFreeDesign;

    public Boolean getButtonFreeDesign() {
        return buttonFreeDesign;
    }

    public void setButtonFreeDesign(Boolean buttonFreeDesign) {
        this.buttonFreeDesign = buttonFreeDesign;
    }
}

