package ru.apertum.qsystem.custom.testapi.services;

import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;

public interface WelcomeService {
    QCustomer standInService(long serviceId, String password, int priority, String inputData) throws Exception;
}
