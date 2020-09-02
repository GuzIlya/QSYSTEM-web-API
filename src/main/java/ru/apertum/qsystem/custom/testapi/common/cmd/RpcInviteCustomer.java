package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;

public class RpcInviteCustomer extends JsonRPC20 {

    public RpcInviteCustomer() {
    }

    @Expose
    @SerializedName("result")
    private QCustomer result;

    public void setResult(QCustomer result) {
        this.result = result;
    }

    public QCustomer getResult() {
        return result;
    }

    public RpcInviteCustomer(QCustomer result) {
        this.result = result;
    }
}
