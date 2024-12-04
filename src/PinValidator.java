public class PinValidator {
    private String correctPin;

    public PinValidator(String correctPin) {
        this.correctPin = correctPin;
    }

    public boolean validate(String pinCode) {
        return pinCode.equals(correctPin);
    }
}