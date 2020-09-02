package ru.apertum.qsystem.custom.testapi.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.apertum.qsystem.custom.testapi.common.dto.QCustomerDto;
import ru.apertum.qsystem.custom.testapi.services.WelcomeService;

@RestController
@RequestMapping(value = "/api/welcome/")
@Api(value = "Welcome", description = "Запросы от пункта регистрации")
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;

    @ApiOperation(value = "Постановка в очередь")
    @PostMapping("/standInService")
    public ResponseEntity<QCustomerDto> standInService(@RequestParam("serviceId") long serviceId,
                                                         @RequestParam("password") String password,
                                                         @RequestParam("priority") int priority,
                                                         @RequestParam("inputData") String inputData) throws Exception{
        return ResponseEntity.ok(QCustomerDto.from(welcomeService.standInService(serviceId, password, priority, inputData)));
    }
}
