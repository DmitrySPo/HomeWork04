import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TerminalImpl implements Terminal {

    private final Scanner scanner = new Scanner(System.in);
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private boolean loggedIn = false;
    private int remainingAttempts = 3;
    private LocalDateTime blockedUntil = null;

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }

    @Override
    public void checkBalance() throws Exception {
        if (!loggedIn) {
            throw new NotLoggedInException("Пожалуйста, войдите в систему.");
        }
        System.out.println("Ваш баланс: " + server.getBalance());
    }

    @Override
    public void withdraw(int amount) throws Exception {
        if (!loggedIn) {
            throw new NotLoggedInException("Пожалуйста, войдите в систему.");
        }
        if (amount % 100 != 0) {
            throw new InvalidAmountException("Сумма должна быть кратной 100.");
        }
        server.withdraw(amount);
        System.out.println("Вы сняли " + amount + ". Новый баланс: " + server.getBalance());
    }

    @Override
    public void deposit(int amount) throws Exception {
        if (!loggedIn) {
            throw new NotLoggedInException("Пожалуйста, войдите в систему.");
        }
        if (amount % 100 != 0) {
            throw new InvalidAmountException("Сумма должна быть кратной 100.");
        }
        server.deposit(amount);
        System.out.println("Вы пополнили счет на " + amount + ". Новый баланс: " + server.getBalance());
    }

    @Override
    public void enterPinCode() throws Exception {
        while (true) {
            if (blockedUntil != null && LocalDateTime.now().isBefore(blockedUntil)) {
                long secondsLeft = ChronoUnit.SECONDS.between(LocalDateTime.now(), blockedUntil);
                throw new AccountBlockedException("Аккаунт заблокирован. Повторите попытку через " + secondsLeft + " секунд.");
            }

            System.out.print("Введите PIN-код: ");
            String pinCode = scanner.nextLine();

            if (pinValidator.validate(pinCode)) {
                loggedIn = true;
                remainingAttempts = 3;
                System.out.println("Вход выполнен успешно.");
                break;
            } else {
                remainingAttempts--;
                if (remainingAttempts == 0) {
                    blockedUntil = LocalDateTime.now().plusSeconds(10);
                    throw new AccountBlockedException("Неверный PIN-код. Аккаунт заблокирован на 10 секунд.");
                } else {
                    throw new WrongPinAttemptException("Неверный PIN-код. Осталось попыток: " + remainingAttempts);
                }
            }
        }
    }
}