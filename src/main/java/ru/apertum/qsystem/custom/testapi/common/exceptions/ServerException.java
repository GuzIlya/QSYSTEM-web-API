package ru.apertum.qsystem.custom.testapi.common.exceptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServerException extends RuntimeException {

    public ServerException(String textException) {
        super(textException);
        log.error("Error! " + textException, this);
    }

    public ServerException(Exception ex) {
        super(ex);
        log.error("Error! " + ex.toString(), this);
    }

    public ServerException(String textException, Exception ex) {
        super(textException, ex);
        log.error("Error! " + textException + "\n" + ex.toString(), this);
    }
}

