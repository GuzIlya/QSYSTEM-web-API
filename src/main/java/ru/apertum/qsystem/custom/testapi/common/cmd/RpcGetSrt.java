package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Задание со строковым результатом.
 *
 * @author Evgeniy Egorov
 */
public class RpcGetSrt extends JsonRPC20 {

    public RpcGetSrt() {
    }

    @Expose
    @SerializedName("result")
    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public RpcGetSrt(String result) {
        this.result = result;
    }
}