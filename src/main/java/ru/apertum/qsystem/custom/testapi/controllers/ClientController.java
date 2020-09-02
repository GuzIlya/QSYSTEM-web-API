package ru.apertum.qsystem.custom.testapi.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.apertum.qsystem.custom.testapi.common.dto.QCustomerDto;
import ru.apertum.qsystem.custom.testapi.common.dto.QUserDto;
import ru.apertum.qsystem.custom.testapi.common.dto.ServiceStateDto;
import ru.apertum.qsystem.custom.testapi.services.ClientService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/client/")
@Api(value = "Client", description = "Запросы от сотрудника")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "Начать работу с вызванным кастомером")
    @PostMapping("/getStartCustomer")
    public ResponseEntity<Object> getStartCustomer(@RequestParam("userId") long userId) throws Exception{
        clientService.getStartCustomer(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Закончить работу с вызванным кастомером")
    @PostMapping("/getFinishCustomer")
    public ResponseEntity<QCustomerDto> getFinishCustomer(@RequestParam("userId") long userId,
                                                          @RequestParam("resultId") Long resultId,
                                                          @RequestParam("comments") String comments) throws Exception{
        return ResponseEntity.ok(QCustomerDto.from(clientService.getFinishCustomer(userId, null, resultId, comments)));
    }

    @ApiOperation(value = "Получение описания всех юзеров")
    @GetMapping("/getUsers")
    public ResponseEntity<List<QUserDto>> getUsers() throws Exception{
        return ResponseEntity.ok(QUserDto.from(clientService.getUsers()));
    }

    @ApiOperation(value = "Вызвать следующего кастомера из конкретной очереди")
    @PostMapping("/inviteNextCustomer")
    public ResponseEntity<QCustomerDto> inviteNextCustomer(@RequestParam("userId") long userId,
                                                           @RequestParam("serviceId") Long serviceId) throws Exception{
        return ResponseEntity.ok(QCustomerDto.from(clientService.inviteNextCustomer(userId, serviceId)));
    }

    @ApiOperation(value = "Получить всю очередь к услуге и т.д.")
    @PostMapping("/getServiceConsistency")
    public ResponseEntity<ServiceStateDto> getServiceConsistency(@RequestParam("serviceId") long serviceId) throws Exception{
        return ResponseEntity.ok(ServiceStateDto.from(clientService.getServiceConsistency(serviceId)));
    }


    //@PostMapping("/getWelcomeState")
    public ResponseEntity<String> getWelcomeState(@RequestParam("message") String message,
                                                  @RequestParam("dropTicketsCounter") boolean dropTicketsCounter) throws Exception{
        return ResponseEntity.ok(clientService.getWelcomeState(message, dropTicketsCounter));
    }

    //@PostMapping("/killNextCustomer")
    public ResponseEntity<Object> killNextCustomer(@RequestParam("userId") long userId,
                                                   @RequestParam("customerId") Long customerId) throws Exception{
        clientService.killNextCustomer(userId, customerId);
        return ResponseEntity.ok().build();
    }
}
