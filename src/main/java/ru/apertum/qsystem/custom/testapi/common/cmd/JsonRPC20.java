package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonRPC20 extends AJsonRPC20 {

    public JsonRPC20() {
    }

    public JsonRPC20(String method, CmdParams params) {
        this.method = method;
        this.params = params;
    }

    @Expose
    @SerializedName("params")
    private CmdParams params;

    public void setParams(CmdParams params) {
        this.params = params;
    }

    public CmdParams getParams() {
        return params;
    }
}

