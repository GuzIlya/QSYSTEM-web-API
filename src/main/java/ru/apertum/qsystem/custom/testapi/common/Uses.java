package ru.apertum.qsystem.custom.testapi.common;

public class Uses {
    public static final String TASK_KILL_NEXT_CUSTOMER = "Удалить следующего клиента";
    public static final String TASK_START_CUSTOMER = "Начать работу с клиентом";
    public static final String TASK_FINISH_CUSTOMER = "Закончить работу с клиентом";
    public static final String WELCOME_LOCK = "#WELCOME_LOCK#";
    public static final String WELCOME_UNLOCK = "#WELCOME_UNLOCK#";
    public static final String WELCOME_OFF = "#WELCOME_OFF#";
    public static final String WELCOME_REINIT = "#WELCOME_REINIT#";
    public static final String TASK_GET_USERS = "Получить перечень пользователей";
    public static final String TASK_GET_SERVICES = "Получить перечень услуг";
    public static final String TASK_STAND_IN = "Поставить в очередь";
    public static final String TASK_INVITE_NEXT_CUSTOMER = "Получить следующего клиента";
    public static final String TASK_GET_SERVICE_CONSISANCY = "Получить очередь услуги";

    /**
     * Скрытие сплэш-заставки.
     */
    private static boolean sh = false;
    public static void closeSplash() {
        sh = false;
    }

}
