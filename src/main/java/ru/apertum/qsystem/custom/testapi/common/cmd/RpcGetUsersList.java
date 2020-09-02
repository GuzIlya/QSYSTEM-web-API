package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ru.apertum.qsystem.custom.testapi.common.models.QUser;

import java.util.LinkedList;
import java.util.List;

/**
 * Получить список пользователей.
 *
 * @author Evgeniy Egorov
 */
@SuppressWarnings("squid:S1319")
public class RpcGetUsersList extends JsonRPC20 {

    public RpcGetUsersList() {
    }

    @Expose
    @SerializedName("result")
    private List<QUser> result;

    public void setResult(List<QUser> result) {
        this.result = result;
    }

    public List<QUser> getResult() {
        return result;
    }

    public RpcGetUsersList(List<QUser> result) {
        this.result = result;
    }
}

