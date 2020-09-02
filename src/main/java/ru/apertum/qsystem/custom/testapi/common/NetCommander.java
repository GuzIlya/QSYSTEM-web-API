package ru.apertum.qsystem.custom.testapi.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.log4j.Log4j2;
import ru.apertum.qsystem.custom.testapi.common.cmd.*;
import ru.apertum.qsystem.custom.testapi.common.models.QCustomer;
import ru.apertum.qsystem.custom.testapi.common.models.QService;
import ru.apertum.qsystem.custom.testapi.common.models.QUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Log4j2
public class NetCommander {
    private static final JsonRPC20 JSON_RPC = new JsonRPC20();

    public static synchronized String send(INetProperty netProperty, String commandName, CmdParams params) throws Exception {
        JSON_RPC.setMethod(commandName);
        JSON_RPC.setParams(params);
        return sendRpc(netProperty, JSON_RPC);
    }

    private static synchronized String sendRpc(INetProperty netProperty, JsonRPC20 jsonRpc) throws Exception {
        final String message;
        Gson gson = GsonPool.getInstance().borrowGson();
        try {
            message = gson.toJson(jsonRpc);
        } finally {
            GsonPool.getInstance().returnGson(gson);
        }
        final String data;


        log.trace("Task \"" + jsonRpc.getMethod() + "\" on " + netProperty.getAddress().getHostAddress() + ":" + netProperty.getPort() + "#\n" + message);
        try (final Socket socket = new Socket()) {
            log.trace("Socket was created.");
            socket.connect(new InetSocketAddress(netProperty.getAddress(), netProperty.getPort()), 15000);

            final PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.print(URLEncoder.encode(message, "utf-8"));
            log.trace("Sending...");
            writer.flush();
            log.trace("Reading...");
            final StringBuilder sb = new StringBuilder();
            final Scanner in = new Scanner(socket.getInputStream());
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
            data = URLDecoder.decode(sb.toString(), "utf-8");
            sb.setLength(0);
            writer.close();
            in.close();
        } catch (IOException ex) {
            throw new Exception(ex);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        log.trace("Response:\n" + data);


        gson = GsonPool.getInstance().borrowGson();
        try {
            final JsonRPC20Error rpc = gson.fromJson(data, JsonRPC20Error.class);
            if (rpc == null) {
                throw new Exception("error_on_server_no_get_response");
            }
            if (rpc.getError() != null) {
                throw new Exception("tack_failed" + " " + rpc.getError().getCode() + ":" + rpc.getError().getMessage());
            }
        } catch (JsonSyntaxException ex) {
            throw new Exception("bad_response" + "\n" + ex.toString());
        } finally {
            GsonPool.getInstance().returnGson(gson);
        }
        return data;
    }

    /**
     * Удаление вызванного юзером кастомера.
     *
     * @param netProperty сеть. параметры соединения с сервером
     * @param userId      оператор
     * @param customerId  переключиться на этого при параллельном приеме, NULL если переключаться не надо
     */
    public static void killNextCustomer(INetProperty netProperty, long userId, Long customerId) throws Exception {
        log.info("Удаление вызванного юзером кастомера.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.userId = userId;
        params.customerId = customerId;
        send(netProperty, Uses.TASK_KILL_NEXT_CUSTOMER, params);
    }

    /**
     * Начать работу с вызванным кастомером.
     *
     * @param netProperty сеть. параметры соединения с сервером.
     * @param userId      оператор
     */
    public static void getStartCustomer(INetProperty netProperty, long userId) throws Exception {
        log.info("Начать работу с вызванным кастомером.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.userId = userId;
        send(netProperty, Uses.TASK_START_CUSTOMER, params);
    }

    /**
     * Получение описания состояния пункта регистрации.
     *
     * @param netProperty        сеть.        параметры соединения с пунктом регистрации
     * @param message            что-то вроде названия команды для пункта регистрации
     * @param dropTicketsCounter сбросить счетчик выданных талонов или нет
     * @return некий ответ от пункта регистрации, вроде прям как строка для вывода
     */
    public static String getWelcomeState(INetProperty netProperty, String message, boolean dropTicketsCounter) throws Exception {
        log.info("Получение описания состояния пункта регистрации.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.dropTicketsCounter = dropTicketsCounter;
        final String res = send(netProperty, message, params);
        return AJsonRPC20.demarshal(res, RpcGetSrt.class).getResult();
    }

    /**
     * Получение описания всех юзеров для выбора себя.
     *
     * @param netProperty сеть. параметры соединения с сервером
     * @return XML-ответ все юзеры системы
     */
    public static List<QUser> getUsers(INetProperty netProperty) throws Exception {
        log.info("Получение описания всех юзеров для выбора себя.");
        // загрузим ответ
        String res = null;
        try {
            res = send(netProperty, Uses.TASK_GET_USERS, null);
        } catch (Exception e) {// вывод исключений
            Uses.closeSplash();
            throw new Exception("command_error2", e);
        }
        final RpcGetUsersList rpc = AJsonRPC20.demarshal(res, RpcGetUsersList.class);
        return rpc.getResult();
    }

    /**
     * Закончить работу с вызванным кастомером.
     *
     * @param netProperty сеть. параметры соединения с сервером
     * @param userId      оператор
     * @param customerId  переключиться на этого при параллельном приеме, NULL если переключаться не надо
     * @param resultId    результат обработки кастомера юзером.
     * @param comments    это если закончили работать с редиректенным и его нужно вернуть
     * @return получим ровно того же кастомера с которым работали.
     */
    public static QCustomer getFinishCustomer(INetProperty netProperty, long userId, Long customerId, Long resultId, String comments) throws Exception {
        log.info("Закончить работу с вызванным кастомером.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.userId = userId;
        params.customerId = customerId;
        params.resultId = resultId;
        params.textData = comments;
        final String res = send(netProperty, Uses.TASK_FINISH_CUSTOMER, params);
        final RpcStandInService rpc = AJsonRPC20.demarshal(res, RpcStandInService.class);
        return rpc.getResult().getCustomer();
    }

    /**
     * Постановка в очередь.
     *
     * @param netProperty сеть. netProperty параметры соединения с сервером.
     * @param serviceId   услуга, в которую пытаемся встать.
     * @param password    пароль того кто пытается выполнить задание.
     * @param priority    приоритет.
     * @param inputData   введенный текст посетителем.
     * @return Созданный кастомер.
     */
    public static QCustomer standInService(INetProperty netProperty, long serviceId, String password, int priority, String inputData) throws Exception {
        log.info("Встать в очередь.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.serviceId = serviceId;
        params.parolcheg = password;
        params.priority = priority;
        params.textData = inputData;
        params.language = Locale.getDefault().getLanguage(); // передадим выбранный язык интерфейса
        final String res = send(netProperty, Uses.TASK_STAND_IN, params);
        final RpcStandInService rpc = AJsonRPC20.demarshal(res, RpcStandInService.class);
        return rpc.getResult().getCustomer();
    }

    /**
     * Получение слeдующего юзера из одной конкретной очереди, обрабатываемой юзером.
     *
     * @param netProperty сеть. параметры соединения с сервером.
     * @param userId      оператор
     * @param serviceId   услуга
     * @return ответ-кастомер следующий по очереди.
     */
    public static QCustomer inviteNextCustomer(INetProperty netProperty, long userId, long serviceId) throws Exception {
        log.info("Получение слeдующего юзера из одной конкретной очереди, обрабатываемой юзером.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.userId = userId;
        params.serviceId = serviceId;
        final String res = send(netProperty, Uses.TASK_INVITE_NEXT_CUSTOMER, params);
        final RpcInviteCustomer rpc = AJsonRPC20.demarshal(res, RpcInviteCustomer.class);
        return rpc.getResult();
    }

    /**
     * Получить всю очередь к услуге и т.д.
     *
     * @param netProperty сеть. параметры соединения с сервером.
     * @param serviceId   id услуги о которой получаем информацию
     * @return количество предшествующих.
     * @throws Exception упало при отсыле.
     */
    public static RpcGetServiceState.ServiceState getServiceConsistency(INetProperty netProperty, long serviceId) throws Exception {
        log.info("Встать в очередь.");
        // загрузим ответ
        final CmdParams params = new CmdParams();
        params.serviceId = serviceId;
        String res = send(netProperty, Uses.TASK_GET_SERVICE_CONSISANCY, params);
        return AJsonRPC20.demarshal(res, RpcGetServiceState.class).getResult();
    }
}
