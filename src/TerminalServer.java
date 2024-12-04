public class TerminalServer {
    private int balance;

    public TerminalServer(int initialBalance) {
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) throws InsufficientFundsException {
        if (balance < amount) {
            throw new InsufficientFundsException("Недостаточно средств на счету.");
        }
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }
}