package environment;

public class EnvNotFoundException extends Exception {
    public EnvNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
