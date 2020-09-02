package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Получить состояние услуги.
 *
 * @author Evgeniy Egorov
 */
public class RpcGetServiceState extends JsonRPC20 {

    public RpcGetServiceState() {
    }

    public RpcGetServiceState(int code, int lenght, String message) {
        this.result = new ServiceState(code, lenght, message);
    }

    public RpcGetServiceState(LinkedBlockingDeque<QCustomer> line) {
        this.result = new ServiceState(line);
    }

    @Expose
    @SerializedName("result")
    private ServiceState result;

    /**
     * Результат выполнения.
     *
     * @return состояние услуги.
     */
    public ServiceState getResult() {
        return result;
    }

    public void setResult(ServiceState result) {
        this.result = result;
    }

    public static class ServiceState {

        public ServiceState() {
        }

        /**
         * Состояние услуги.
         */
        public ServiceState(int code, int lenght, String message) {
            this.code = code;
            this.message = message;
            this.lenghtLine = lenght;
        }

        /**
         * Состояние услуги.
         *
         * @param line толпа кастомеров.
         */
        public ServiceState(LinkedBlockingDeque<QCustomer> line) {
            this.code = 0;
            this.lenghtLine = line == null ? 0 : line.size();
            this.message = null;
            this.clients = line;
        }

        @Expose
        @SerializedName("code")
        private int code;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Expose
        @SerializedName("lenghtLine")
        private int lenghtLine;

        public int getLenghtLine() {
            return lenghtLine;
        }

        public void setLenghtLine(int lenght) {
            this.lenghtLine = lenght;
        }

        @Expose
        @SerializedName("message")
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Expose
        @SerializedName("clients")
        private LinkedBlockingDeque<QCustomer> clients;

        public LinkedBlockingDeque<QCustomer> getClients() {
            return clients;
        }

        public void setClients(LinkedBlockingDeque<QCustomer> clients) {
            this.clients = clients;
        }
    }
}

