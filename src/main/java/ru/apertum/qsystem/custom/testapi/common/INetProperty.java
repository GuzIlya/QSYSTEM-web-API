package ru.apertum.qsystem.custom.testapi.common;

import java.net.InetAddress;

/**
 * Получение необходимых данных для организации работы с сетью.
 *
 */
public interface INetProperty {

    /**
     * Порт для приемы сообщений от клиентских модулей по протоколу TCP.
     *
     * @return номер порта
     */
    public Integer getPort();

    /**
     * Адрес машины, сетевой адрес.
     *
     * @return сетевой адрес
     */
    public InetAddress getAddress();
}