package ru.apertum.qsystem.custom.testapi.services.impl;

import org.springframework.stereotype.Service;
import ru.apertum.qsystem.custom.testapi.common.INetProperty;
import ru.apertum.qsystem.custom.testapi.common.NetCommander;
import ru.apertum.qsystem.custom.testapi.common.ServerNetProperty;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;
import ru.apertum.qsystem.custom.testapi.services.WelcomeService;

@Service
public class WelcomeServiceImpl implements WelcomeService {
    @Override
    public QCustomer standInService(long serviceId, String password, int priority, String inputData) throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        return NetCommander.standInService(netProperty, serviceId, password, priority, inputData);
    }
}
