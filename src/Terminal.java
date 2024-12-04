public interface Terminal {
    void checkBalance() throws Exception; // Проверить состояние счета
    void withdraw(int amount) throws Exception;// Снять денежные средства со счета
    void deposit(int amount) throws Exception; // Внести денежные средства на счет
    void enterPinCode() throws Exception; // Ввод пинкода
}