package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.custom.testapi.common.models.QNet;
import ru.apertum.qsystem.custom.testapi.common.models.QService;

import java.util.Date;

public class RpcGetAllServices extends JsonRPC20 {

    public RpcGetAllServices() {
    }

    public RpcGetAllServices(ServicesForWelcome result) {
        this.result = result;
    }

    public static class ServicesForWelcome {

        public ServicesForWelcome() {
        }

        /**
         * Структура для услуг и прочего.
         *
         * @param root корень услуг.
         * @param qnet прочие настройки из БД.
         */
        public ServicesForWelcome(QService root, QNet qnet) {
            this.root = root;
            this.startTime = qnet.getStartTime();
            this.finishTime = qnet.getFinishTime();
            this.buttonFreeDesign = qnet.getButtonFreeDesign();
        }

        @Expose
        @SerializedName("root")
        private QService root;

        public QService getRoot() {
            return root;
        }

        public void setRoot(QService root) {
            this.root = root;
        }

        @Expose
        @SerializedName("start_time")
        private Date startTime;

        public Date getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(Date finishTime) {
            this.finishTime = finishTime;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        @Expose
        @SerializedName("finish_time")
        private Date finishTime;

        /**
         * Свободное расположение кнопок на пункте регистрации.
         */
        @Expose
        @SerializedName("btn_free_dsn")
        private Boolean buttonFreeDesign;

        public Boolean getButtonFreeDesign() {
            return buttonFreeDesign;
        }

        public void setButtonFreeDesign(Boolean buttonFreeDesign) {
            this.buttonFreeDesign = buttonFreeDesign;
        }
    }

    @Expose
    @SerializedName("result")
    private ServicesForWelcome result;

    public void setResult(ServicesForWelcome result) {
        this.result = result;
    }

    public ServicesForWelcome getResult() {
        return result;
    }
}
