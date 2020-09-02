package ru.apertum.qsystem.custom.testapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.apertum.qsystem.custom.testapi.common.models.QUser;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QUserDto {
    private Long id;
    private Integer enable;
    private Boolean adminAccess;
    private Boolean reportAccess;
    private Boolean parallelAccess;
    private String parolcheg;
    private String point;
    private String name;
    private Integer adressRS;

    public static QUserDto from(QUser qUser) {
        return QUserDto.builder()
                .id(qUser.getId())
                .enable(qUser.getEnable())
                .adminAccess(qUser.getAdminAccess())
                .reportAccess(qUser.getReportAccess())
                .parallelAccess(qUser.getParallelAccess())
                .parolcheg(qUser.getParolcheg())
                .point(qUser.getPoint())
                .name(qUser.getName())
                .adressRS(qUser.getAdressRS())
                .build();
    }

    public static List<QUserDto> from(List<QUser> orders) {
        return orders.stream().map(QUserDto::from).collect(Collectors.toList());
    }

}
