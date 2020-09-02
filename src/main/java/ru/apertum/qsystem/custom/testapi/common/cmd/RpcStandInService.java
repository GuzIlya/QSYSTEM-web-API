package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;

/**
 * Поставить в очередь к услуге.
 *
 * @author Evgeniy Egorov
 */
public class RpcStandInService extends JsonRPC20 {

    public RpcStandInService() {
    }

    @Expose
    @SerializedName("result")
    private AdvResult result;

    public void setResult(AdvResult result) {
        this.result = result;
    }

    public AdvResult getResult() {
        return result;
    }

    public RpcStandInService(AdvResult result) {
        this.result = result;
    }

    /**
     * Спецкостыль что-бы передать текст ошибки при постановки в очередь предварительного.
     */
    public RpcStandInService(String error) {
        this.result = new AdvResult(error);
    }

    public RpcStandInService(QCustomer customer) {
        this.result = new AdvResult(customer);
    }

    public static class AdvResult {
        @Expose
        @SerializedName("customer")
        private QCustomer customer;

        @Expose
        @SerializedName("error")
        private String error;

        public AdvResult() {
            // for marshaling.
        }

        public AdvResult(QCustomer customer) {
            this.customer = customer;
        }

        AdvResult(String error) {
            this.error = error;
        }

        public QCustomer getCustomer() {
            return customer;
        }

        public String getError() {
            return error;
        }
    }
}
