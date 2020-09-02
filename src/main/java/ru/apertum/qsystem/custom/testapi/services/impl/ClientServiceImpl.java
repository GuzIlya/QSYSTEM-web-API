package ru.apertum.qsystem.custom.testapi.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.apertum.qsystem.custom.testapi.common.*;
import ru.apertum.qsystem.custom.testapi.common.cmd.RpcGetServiceState;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;
import ru.apertum.qsystem.custom.testapi.common.models.QUser;
import ru.apertum.qsystem.custom.testapi.services.ClientService;

import java.util.LinkedList;
import java.util.List;

@Log4j2
@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public void killNextCustomer(long userId, Long customerId) throws Exception{
        INetProperty netProperty = new ServerNetProperty();
        NetCommander.killNextCustomer(netProperty, userId, customerId);
    }

    @Override
    public void getStartCustomer(long userId) throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        NetCommander.getStartCustomer(netProperty, userId);
    }

    @Override
    public QCustomer inviteNextCustomer(long userId, long serviceId) throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        return NetCommander.inviteNextCustomer(netProperty, userId, serviceId);
    }

    @Override
    public String getWelcomeState(String message, boolean dropTicketsCounter) throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        return NetCommander.getWelcomeState(netProperty, message, dropTicketsCounter);
    }

    @Override
    public List<QUser> getUsers() throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        return NetCommander.getUsers(netProperty);
    }

    @Override
    public QCustomer getFinishCustomer(long userId, Long customerId, Long resultId, String comments) throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        return NetCommander.getFinishCustomer(netProperty, userId, customerId, resultId, comments);
    }

    @Override
    public RpcGetServiceState.ServiceState getServiceConsistency(long serviceId) throws Exception {
        INetProperty netProperty = new ServerNetProperty();
        return NetCommander.getServiceConsistency(netProperty, serviceId);
    }
}
