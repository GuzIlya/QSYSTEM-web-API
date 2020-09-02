package ru.apertum.qsystem.custom.testapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.apertum.qsystem.custom.testapi.common.CustomerState;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;
import ru.apertum.qsystem.custom.testapi.common.models.QUser;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QCustomerDto {
    private Long id;
    private Integer number;
    private Integer stateIn;
    private CustomerState state;
    private Integer priority;
    private String prefix;
    private Date standTime;
    private Date startTime;
    private Date finishTime;
    private String inputData;
    private QUserDto qUserDto;


    public static QCustomerDto from(QCustomer qCustomer) {
        QCustomerDto qCustomerDto = QCustomerDto.builder()
                .id(qCustomer.getId())
                .number(qCustomer.getNumber())
                .stateIn(qCustomer.getStateIn())
                .state(qCustomer.getState())
                .prefix(qCustomer.getPrefix())
                .standTime(qCustomer.getStandTime())
                .standTime(qCustomer.getStartTime())
                .finishTime(qCustomer.getFinishTime())
                .inputData(qCustomer.getInputData())
                .build();

        if (qCustomer.getUser() == null)
            qCustomerDto.qUserDto = null;
        else
            qCustomerDto.setQUserDto(QUserDto.from(qCustomer.getUser()));

        return qCustomerDto;
    }

    public static List<QCustomerDto> from(List<QCustomer> qCustomers) {
        return qCustomers.stream().map(QCustomerDto::from).collect(Collectors.toList());
    }

    public static List<QCustomerDto> from(LinkedBlockingDeque<QCustomer> qCustomers) {
        return qCustomers.stream().map(QCustomerDto::from).collect(Collectors.toList());
    }
}
