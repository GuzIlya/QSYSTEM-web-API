package ru.apertum.qsystem.custom.testapi.common.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.extern.log4j.Log4j2;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Модель данных для функционирования очереди. Включает в себя: - структуру хранения - методы доступа - методы манипулирования - логирование итераций Главный
 * класс модели данных. Содержит объекты всех кастомеров в очереди к этой услуге. Имеет все необходимые методы для манипулирования кастомерами в пределах одной
 * очереди
 *
 * @author Evgeniy Egorov
 */
@Log4j2
public class QService extends DefaultMutableTreeNode implements Transferable {

    /**
     * множество кастомеров, вставших в очередь к этой услуге.
     */
    private final PriorityQueue<QCustomer> customers = new PriorityQueue<>();

    private PriorityQueue<QCustomer> getCustomers() {
        return customers;
    }

    //@Expose
    //@SerializedName("clients")
    private final LinkedBlockingDeque<QCustomer> clients = new LinkedBlockingDeque<>(customers);

    /**
     * Это все кастомеры стоящие к этой услуге в виде списка Только для бакапа на диск.
     *
     * @return
     */
    public LinkedBlockingDeque<QCustomer> getClients() {
        return clients;
    }

    //@GeneratedValue(strategy = GenerationType.AUTO) авто нельзя, т.к. id нужны для формирования дерева.
    @Expose
    @SerializedName("id")
    private Long id = new Date().getTime();


    public Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * Если нужно для услуги что-то сохранять в системных параметрах, то это надо сохранять в секцию для этой конкретной услуги.
     *
     * @return Имя секции в системных параметах для конкретно этой услуги.
     */
    public String getSectionName() {
        return "srv_" + id.toString();
    }

    /**
     * признак удаления с проставленим даты.
     */
    private Date deleted;

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    /**
     * Состояние услуги. 1 - доступна, 0 - недоступна, -1 - невидима, 2 - только для предвариловки, 3 - заглушка, 4 - не для предвариловки, 5 - рулон.
     */
    @Expose
    @SerializedName("status")
    private Integer status;

    /**
     * Состояние услуги. Влияет на состояние кнопки на киоске, при редиректе.
     *
     * @return 1 - доступна, 0 - недоступна, -1 - невидима, 2 - только для предвариловки, 3 - заглушка, 4 - не для предвариловки, 5 - рулон.
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Набор статусов услуги, при которых услуга участвует в работе очереди, т.е. к ней могут стоять люди. Это не заглушка и не неактивная.
     */
    public static final Set<Integer> STATUS_FOR_USING = new HashSet<>(Arrays.asList(-1, 1, 2, 4, 5));//NOSONAR

    /**
     * Набор статусов услуги, при которых услуга НЕ участвует в работе очереди, т.е. к ней НЕ могут стоять люди. Это заглушка или неактивная.
     */
    public static final Set<Integer> STATUS_FOR_STUB = new HashSet<>(Arrays.asList(0, 3));//NOSONAR
    /**
     * Пунктов регистрации может быть много. Наборы кнопок на разных киосках могут быть разные. Указание для какого пункта регистрации услуга, 0-для всех, х-для
     * киоска х.
     */
    @Expose
    @SerializedName("point")
    private Integer point = 0;

    /**
     * Пунктов регистрации может быть много. Наборы кнопок на разных киосках могут быть разные. Указание для какого пункта регистрации услуга, 0-для всех, х-для
     * киоска х.
     *
     * @return Наборы кнопок на разных киосках могут быть разные.
     */
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * Норматив. Среднее время оказания этой услуги. Зачем надо? Не знаю. Пока для маршрутизации при медосмотре. Может потом тоже применем.
     */
    @Expose
    @SerializedName("duration")
    private Integer duration = 1;

    /**
     * Норматив. Среднее время оказания этой услуги. Зачем надо? Не знаю. Пока для маршрутизации при медосмотре. Может потом тоже применем.
     *
     * @return Норматив.
     */
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Время обязательного ожидания посетителя.
     */
    @Expose
    @SerializedName("exp")
    private Integer expectation = 0;

    /**
     * Время обязательного ожидания посетителя.
     *
     * @return в минутах
     */
    public Integer getExpectation() {
        return expectation;
    }

    public void setExpectation(Integer expectation) {
        this.expectation = expectation;
    }

    /**
     * шаблон звукового приглашения. null или 0... - использовать родительский. Далее что играем а что нет.
     */
    @Expose
    @SerializedName("sound_template")
    private String soundTemplate;

    /**
     * шаблон звукового приглашения. null или 0... - использовать родительский. Далее что играем а что нет.
     *
     * @return шаблон звукового приглашения.
     */
    public String getSoundTemplate() {
        return soundTemplate;
    }

    public void setSoundTemplate(String soundTemplate) {
        this.soundTemplate = soundTemplate;
    }

    @Expose
    @SerializedName("advance_limit")
    private Integer advanceLimit = 1;

    public Integer getAdvanceLimit() {
        return advanceLimit;
    }

    public void setAdvanceLinit(Integer advanceLimit) {
        this.advanceLimit = advanceLimit;
    }

    @Expose
    @SerializedName("day_limit")
    private Integer dayLimit = 0;

    public Integer getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
    }

    @Expose
    @SerializedName("person_day_limit")
    private Integer personDayLimit = 0;

    public Integer getPersonDayLimit() {
        return personDayLimit;
    }

    public void setPersonDayLimit(Integer personDayLimit) {
        this.personDayLimit = personDayLimit;
    }

    @Expose
    @SerializedName("inputed_as_number")
    private Integer inputedAsNumber = 0;

    /**
     * Если требуется использовать введенные пользователем данные как его номер в очереди. 0 - генерировать как обычно. enteredAsNumber - использовать первые
     * enteredAsNumber символов как номер в очереди
     *
     * @return 0 - генерировать как обычно. enteredAsNumber - использовать первые enteredAsNumber символов как номер в очереди.
     */
    public Integer getInputedAsNumber() {
        return inputedAsNumber;
    }

    public void setInputedAsNumber(Integer inputedAsNumber) {
        this.inputedAsNumber = inputedAsNumber;
    }

    /**
     * Это ограничение в днях, в пределах которого можно записаться вперед при предварительной записи может быть null или 0 если нет ограничения.
     */
    @Expose
    @SerializedName("advance_limit_period")
    private Integer advanceLimitPeriod = 0;

    public Integer getAdvanceLimitPeriod() {
        return advanceLimitPeriod;
    }

    public void setAdvanceLimitPeriod(Integer advanceLimitPeriod) {
        this.advanceLimitPeriod = advanceLimitPeriod;
    }

    /**
     * Деление сетки предварительной записи.
     */
    @Expose
    @SerializedName("advance_time_period")
    private Integer advanceTimePeriod = 60;

    public Integer getAdvanceTimePeriod() {
        return advanceTimePeriod;
    }

    public void setAdvanceTimePeriod(Integer advanceTimePeriod) {
        this.advanceTimePeriod = advanceTimePeriod;
    }

    /**
     * Способ вызова клиента юзером 1 - стандартно 2 - backoffice, т.е. вызов следующего без табло и звука, запершение только редиректом.
     */
    @Expose
    @SerializedName("enable")
    private Integer enable = 1;

    /**
     * Способ вызова клиента юзером 1 - стандартно 2 - backoffice, т.е. вызов следующего без табло и звука, запершение только редиректом.
     *
     * @return int index.
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    private Integer seqId = 0;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    /**
     * Требовать или нет от пользователя после окончания работы с клиентом по этой услуге обозначить результат этой работы выбрав пункт из словаря результатов.
     */
    @Expose
    @SerializedName("result_required")
    private Boolean resultRequired = false;

    /**
     * Требовать или нет от пользователя после окончания работы с клиентом по этой услуге обозначить результат этой работы выбрав пункт из словаря результатов.
     *
     * @return да или нет.
     */
    public Boolean getResultRequired() {
        return resultRequired;
    }

    public void setResultRequired(Boolean resultRequired) {
        this.resultRequired = resultRequired;
    }

    /**
     * Требовать или нет на пункте регистрации ввода от клиента каких-то данных перед постановкой в очередь после выбора услуги.
     */
    @Expose
    @SerializedName("input_required")
    private Boolean inputRequired = false;

    /**
     * Требовать или нет на пункте регистрации ввода от клиента каких-то данных перед постановкой в очередь после выбора услуги.
     *
     * @return да или нет.
     */
    public Boolean getInputRequired() {
        return inputRequired;
    }

    public void setInputRequired(Boolean inputRequired) {
        this.inputRequired = inputRequired;
    }

    /**
     * На главном табло вызов по услуге при наличии третьей колонке делать так, что эту третью колонку заполнять не стройкой у юзера, а введенной пользователем
     * строчкой.
     */
    @Expose
    @SerializedName("inputed_as_ext")
    private Boolean inputedAsExt = false;

    /**
     * На главном табло вызов по услуге при наличии третьей колонке делать так, что эту третью колонку заполнять не стройкой у юзера, а введенной пользователем
     * строчкой.
     *
     * @return Разрешение выводить на табло введеные посетителем на киоске(или еще как) данные.
     */
    public Boolean getInputedAsExt() {
        return inputedAsExt;
    }

    public void setInputedAsExt(Boolean inputedAsExt) {
        this.inputedAsExt = inputedAsExt;
    }

    /**
     * Заголовок окна при вводе на пункте регистрации клиентом каких-то данных перед постановкой в очередь после выбора услуги. Также печатается на талоне рядом
     * с введенными данными.
     */
    @Expose
    @SerializedName("input_caption")
    private String inputCaption = "";

    /**
     * Заголовок окна при вводе на пункте регистрации клиентом каких-то данных перед постановкой в очередь после выбора услуги. Также печатается на талоне рядом
     * с введенными данными.
     *
     * @return текст заголовка
     */
    public String getInputCaption() {
        return inputCaption;
    }

    public void setInputCaption(String inputCaption) {
        this.inputCaption = inputCaption;
    }

    /**
     * html текст информационного сообщения перед постановкой в очередь. Если этот параметр пустой, то не требуется показывать информационную напоминалку на
     * пункте регистрации.
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
     * @return строчеп из БД.
     */
    public String getTabloText() {
        return tabloText;
    }

    public void setTabloText(String tabloText) {
        this.tabloText = tabloText;
    }

    /**
     * Расположение кнопки на пункте регистрации.
     */
    @Expose
    @SerializedName("but_x")
    private Integer butX = 100;
    @Expose
    @SerializedName("but_y")
    private Integer butY = 100;
    @Expose
    @SerializedName("but_b")
    private Integer butB = 100;
    @Expose
    @SerializedName("but_h")
    private Integer butH = 100;

    public Integer getButB() {
        return butB;
    }

    public void setButB(Integer butB) {
        this.butB = butB;
    }

    public Integer getButH() {
        return butH;
    }

    public void setButH(Integer butH) {
        this.butH = butH;
    }

    public Integer getButX() {
        return butX;
    }

    public void setButX(Integer butX) {
        this.butX = butX;
    }

    public Integer getButY() {
        return butY;
    }

    public void setButY(Integer butY) {
        this.butY = butY;
    }

    /**
     * последний номер, выданный последнему кастомеру при номерировании клиентов обособлено в услуге. тут такой замут. когда услугу создаешь из json где-то на
     * клиенте, то там же спринг-контекст не поднят да и нужно это только в качестве данных.
     */
    private int lastNumber = Integer.MIN_VALUE;
    /**
     * последний номер, выданный последнему кастомеру при номерировании клиентов общем рядом для всех услуг. Ограничение самого минимально возможного номера
     * клиента при сквозном нумерировании происходит при определении параметров нумерации.
     */
    private static volatile int lastStNumber = Integer.MIN_VALUE;

    public QService() {
        super();
    }

    @Override
    public String toString() {
        return getName().trim().isEmpty() ? "<NO_NAME>" : getName();
    }


    // чтоб каждый раз в бд не лазить для проверки сколько предварительных сегодня по этой услуге

    private int dayY = -100; // для смены дня проверки

    private int dayAdvs = -100; // для смены дня проверки


    /**
     * Иссяк лимит на одинаковые введенные данные в день по услуге или нет.
     *
     * @param data что-то.
     * @return true - превышен, в очередь становиться нельзя; false - можно в очередь встать
     */




    /**
     * Сколько кастомеров уже прошло услугу сегодня.
     */
    @Expose
    @SerializedName("countPerDay")
    private int countPerDay = 0;

    public void setCountPerDay(int countPerDay) {
        this.countPerDay = countPerDay;
    }

    public int getCountPerDay() {
        return countPerDay;
    }

    /**
     * Текущий день, нужен для учета количества кастомеров обработанных в этой услуге в текущий день.
     */
    @Expose
    @SerializedName("day")
    private int day = 0;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    /**
     * Можно изменить текущий номер талона. Это нужно если у вас услуга-рулон. Повесили новый пулон, там на конце болтается какой-то номер, его выставили
     * текущим.
     *
     * @param newCurrent этот и будет текущим.
     */
    public void reinitNextNumber(int newCurrent) {
        lastNumber = newCurrent - 1;
    }



    // ***************************************************************************************
    // ********************  МЕТОДЫ УПРАВЛЕНИЯ ЭЛЕМЕНТАМИ И СТРУКТУРЫ ************************
    // ***************************************************************************************


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
     * Префикс услуги.
     */
    @Expose
    @SerializedName("service_prefix")
    private String prefix = "";

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? "" : prefix;
    }

    public String getPrefix() {
        return prefix == null ? "" : prefix;
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

    /**
     * Группировка услуг.
     */
    @Expose
    @SerializedName("parentId")
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    private QService link;

    public QService getLink() {
        return link;
    }

    public void setLink(QService link) {
        this.link = link;
    }


    private QSchedule schedule;

    public QSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(QSchedule schedule) {
        this.schedule = schedule;
    }


    private QCalendar calendar;

    public QCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(QCalendar calendar) {
        this.calendar = calendar;
    }

    @Expose
    @SerializedName("langs")
    private Set<QServiceLang> langs = new HashSet<>();

    public Set<QServiceLang> getLangs() {
        return langs;
    }

    public void setLangs(Set<QServiceLang> langs) {
        this.langs = langs;
    }

    private HashMap<String, QServiceLang> qslangs = null;


    public enum Field {

        /**
         * Надпись на кнопке.
         */
        BUTTON_TEXT,
        /**
         * заголовок ввода клиентом.
         */
        INPUT_CAPTION,
        /**
         * читаем перед тем как встать в очередь.
         */
        PRE_INFO_HTML,
        /**
         * печатаем подсказку перед тем как встать в очередь.
         */
        PRE_INFO_PRINT_TEXT,
        /**
         * текст на талоте персонально услуги.
         */
        TICKET_TEXT,
        /**
         * описание услуги.
         */
        DESCRIPTION,
        /**
         * имя услуги.
         */
        NAME
    }



    /**
     * Если не NULL и не пустая, то эта услуга недоступна и сервер обламает постановку в очередь выкинув причину из этого поля на пункт регистрации.
     */
    private String tempReasonUnavailable;

    public String getTempReasonUnavailable() {
        return tempReasonUnavailable;
    }

    public void setTempReasonUnavailable(String tempReasonUnavailable) {
        this.tempReasonUnavailable = tempReasonUnavailable;
    }
    //*******************************************************************************************************************
    //*******************************************************************************************************************
    //********************** Реализация методов узла в дереве ***********************************************************

    /**
     * По сути группа объединения услуг или коернь всего дерева. То во что включена данныя услуга.
     */

    private QService parentService;
    @Expose
    @SerializedName("inner_services")
    private final LinkedList<QService> childrenOfService = new LinkedList<>();

    @SuppressWarnings("squid:S1319")
    public LinkedList<QService> getChildren() {
        return childrenOfService;
    }



    @Override
    public QService getChildAt(int childIndex) {
        return childrenOfService.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return childrenOfService.size();
    }

    @Override
    public QService getParent() {
        return parentService;
    }

    @Override
    public int getIndex(TreeNode node) {
        return childrenOfService.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(childrenOfService);
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        child.setParent(this);
        this.childrenOfService.add(index, (QService) child);
    }

    @Override
    public void remove(int index) {
        this.childrenOfService.remove(index);
    }

    @Override
    public void remove(MutableTreeNode node) {
        this.childrenOfService.remove((QService) node);
    }

    @Override
    public void removeFromParent() {
        getParent().remove(getParent().getIndex(this));
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        parentService = (QService) newParent;
        if (parentService != null) {
            setParentId(parentService.id);
        } else {
            parentId = null;
        }
    }

    /**
     * data flavor used to get back a DnDNode from data transfer.
     */
    public static final DataFlavor DND_NODE_FLAVOR = new DataFlavor(QService.class, "Drag and drop Node");
    /**
     * list of all flavors that this DnDNode can be transfered as.
     */
    protected static DataFlavor[] flavors = {QService.DND_NODE_FLAVOR};

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return true;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (this.isDataFlavorSupported(flavor)) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
