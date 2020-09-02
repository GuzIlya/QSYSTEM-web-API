package ru.apertum.qsystem.custom.testapi.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;

@Data
public class ServerNetProperty implements INetProperty {
    @Value("${inet.property.port}")
    private Integer port;
    @Value("${inet.property.address}")
    private InetAddress address;

    public ServerNetProperty() throws Exception{
        this.port = 3128;
        try{
            this.address = InetAddress.getLocalHost();
        } catch  (Exception ex) {
            throw new Exception(ex);
        }
    }
}
