package ru.apertum.qsystem.custom.testapi.common.cmd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonRPC20Error extends AJsonRPC20 {

    public enum ErrorMsg {
        UNKNOWN_ERROR(-1, "Unknown error."),
        RESPONCE_NOT_SAVE(2, "Не сохранили отзыв в базе."),
        POSTPONED_NOT_FOUND(3, "Отложенный пользователь не найден по его ID."),
        ADVANCED_NOT_FOUND(4, "Не верный номер предварительной записи."),
        REQUIRED_CUSTOMER_NOT_FOUND(5, "Customer not found but required.");

        final String message;
        final Integer code;

        ErrorMsg(Integer code, String message) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public Integer getCode() {
            return code;
        }
    }

    public static class ErrorRPC {

        public ErrorRPC() {
        }

        /**
         * Создать ответ с ошибкой.
         *
         * @param error тип ошибки.
         * @param data  сопутствующая информация.
         */
        public ErrorRPC(ErrorMsg error, Object data) {
            this.code = error == null ? ErrorMsg.UNKNOWN_ERROR.getCode() : error.getCode();
            this.message = error == null ? ErrorMsg.UNKNOWN_ERROR.getMessage() : error.getMessage();
            this.data = data;
        }

        public Integer getCode() {
            return code;
        }

        public Object getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }

        @Expose
        @SerializedName("code")
        private Integer code;
        @Expose
        @SerializedName("message")
        private String message;
        @Expose
        @SerializedName("data")
        private Object data;
    }

    public JsonRPC20Error() {
    }

    public JsonRPC20Error(ErrorMsg err) {
        error = new ErrorRPC(err, null);
    }

    public JsonRPC20Error(ErrorMsg err, Object data) {
        error = new ErrorRPC(err, data);
    }

    public JsonRPC20Error(Integer code, Object data) {
        error = new ErrorRPC(null, data);
        error.code = code;
    }

    public JsonRPC20Error(ErrorMsg err, String message, Object data) {
        error = new ErrorRPC(err, data);
        error.message = message;
    }

    @Expose
    @SerializedName("error")
    private ErrorRPC error;

    public void setError(ErrorRPC error) {
        this.error = error;
    }

    public ErrorRPC getError() {
        return error;
    }
}
