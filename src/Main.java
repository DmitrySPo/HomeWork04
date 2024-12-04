public class Main {
    public static void main(String[] args) {
        TerminalServer server = new TerminalServer(5000); // Начальный баланс 5000
        PinValidator validator = new PinValidator("1234"); // Корректный PIN-код
        Terminal terminal = new TerminalImpl(server, validator);

        while (true) {
            try {
                terminal.enterPinCode(); // Пользователь вводит пин-код

                terminal.checkBalance();       // Проверка баланса
                terminal.withdraw(300);        // Снятие 300
                terminal.deposit(700);         // Пополнение на 700

                break; // Выход из цикла после успешной аутентификации
            } catch (Exception e) {
                System.err.println(e.getMessage()); // Вывод ошибки
                if (e instanceof AccountBlockedException) {
                    try {
                        Thread.sleep(10000); // Ждём 10 секунд перед следующей попыткой
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}