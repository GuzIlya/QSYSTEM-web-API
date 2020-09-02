package ru.apertum.qsystem.custom.testapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.apertum.qsystem.custom.testapi.common.cmd.RpcGetServiceState;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceStateDto {
    private int code;

    private int lenghtLine;

    private String message;

    private List<QCustomerDto> qCustomerDtos;

    public static ServiceStateDto from(RpcGetServiceState.ServiceState serviceState) {
        ServiceStateDto serviceStateDto = ServiceStateDto.builder()
                .code(serviceState.getCode())
                .lenghtLine(serviceState.getLenghtLine())
                .message(serviceState.getMessage())
                .build();

        if (serviceState.getClients() != null)
            serviceStateDto.setQCustomerDtos(QCustomerDto.from(serviceState.getClients()));

        return serviceStateDto;
    }

    public static List<ServiceStateDto> from(List<RpcGetServiceState.ServiceState> serviceStates) {
        return serviceStates.stream().map(ServiceStateDto::from).collect(Collectors.toList());
    }
}
