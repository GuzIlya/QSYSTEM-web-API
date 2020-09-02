package ru.apertum.qsystem.custom.testapi.services;

import ru.apertum.qsystem.custom.testapi.common.INetProperty;
import ru.apertum.qsystem.custom.testapi.common.cmd.RpcGetServiceState;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;
import ru.apertum.qsystem.custom.testapi.common.models.QUser;

import java.util.List;

public interface ClientService {
    void killNextCustomer(long userId, Long customerId) throws Exception;
    void getStartCustomer(long userId) throws Exception;
    QCustomer inviteNextCustomer(long userId, long serviceId) throws Exception;
    String getWelcomeState(String message, boolean dropTicketsCounter) throws Exception;
    List<QUser> getUsers() throws Exception;
    QCustomer getFinishCustomer(long userId, Long customerId, Long resultId, String comments) throws Exception;
    RpcGetServiceState.ServiceState getServiceConsistency(long serviceId) throws Exception;
}
